package systems.brn.tweaks.paper;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class TweakListener implements Listener {
    private static final int VANILLA_PORTAL_COOLDOWN = 300;

    private final Set<UUID> watched = ConcurrentHashMap.newKeySet();
    private final Plugin plugin;

    public TweakListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EnderSignal eye) {
            int chance = Rules.get(entity.getWorld(), Rules.ENDER_EYE_POP_CHANCE);
            eye.setDropItem(ThreadLocalRandom.current().nextInt(100) >= chance);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Creeper creeper) {
            creeper.setMaxFuseTicks(Rules.get(creeper.getWorld(), Rules.CREEPER_FUSE_TIME));
        }
    }

    @EventHandler
    public void onAdd(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        int rule = Rules.get(entity.getWorld(), Rules.ENTITY_PORTAL_COOLDOWN);
        int current = entity.getPortalCooldown();
        if (current > rule || current == VANILLA_PORTAL_COOLDOWN && rule != VANILLA_PORTAL_COOLDOWN) {
            entity.setPortalCooldown(rule);
        }
        if (entity instanceof Mob mob && mob.isLeashed()) {
            watchLeash(mob);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof EnderPearl)) {
            return;
        }
        int damage = Rules.get(event.getEntity().getWorld(), Rules.ENDER_PEARL_DAMAGE);
        if (damage == 0) {
            event.setCancelled(true);
        } else {
            event.setDamage(damage);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCritical(EntityDamageByEntityEvent event) {
        if (!event.isCritical()) {
            return;
        }
        int percent = Rules.get(event.getEntity().getWorld(), Rules.CRITICAL_MULTIPLIER);
        if (percent != 150) {
            event.setDamage(event.getDamage() / 1.5 * (percent / 100.0));
        }
    }

    @EventHandler
    public void onShieldCooldown(PlayerItemCooldownEvent event) {
        if (event.getType() != Material.SHIELD || event.getCooldown() != 100) {
            return;
        }
        event.setCooldown(Rules.get(event.getPlayer().getWorld(), Rules.SHIELD_AXE_COOLDOWN));
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl pearl) || !(pearl.getShooter() instanceof LivingEntity shooter)) {
            return;
        }
        int power = Rules.get(pearl.getWorld(), Rules.ENDER_PEARL_POWER);
        int divergence = Rules.get(pearl.getWorld(), Rules.ENDER_PEARL_DIVERGENCE);
        if (power == 150 && divergence == 100) {
            return;
        }
        var handle = ((org.bukkit.craftbukkit.entity.CraftEntity) pearl).getHandle();
        var owner = ((org.bukkit.craftbukkit.entity.CraftEntity) shooter).getHandle();
        ((net.minecraft.world.entity.projectile.Projectile) handle)
                .shootFromRotation(owner, owner.getXRot(), owner.getYRot(), 0.0F, power / 100.0F, divergence / 100.0F);
    }

    @EventHandler
    public void onUnleash(EntityUnleashEvent event) {
        if (event.getReason() != EntityUnleashEvent.UnleashReason.DISTANCE || !(event.getEntity() instanceof Mob mob)) {
            return;
        }
        Entity holder = mob.getLeashHolder();
        if (holder == null || holder.getWorld() != mob.getWorld()) {
            return;
        }
        int limit = Rules.get(mob.getWorld(), Rules.LEASH_BREAK_DISTANCE);
        if (mob.getLocation().distanceSquared(holder.getLocation()) <= (double) limit * limit) {
            event.setDropLeash(false);
            LeashPackets.mark(mob, holder);
            mob.getScheduler().run(plugin, task -> {
                if (holder.isValid() && mob.isValid()) {
                    mob.setLeashHolder(holder);
                }
            }, null);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        LeashPackets.inject(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onLeash(PlayerLeashEntityEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            mob.getScheduler().run(plugin, task -> watchLeash(mob), null);
        }
    }

    private void watchLeash(Mob mob) {
        if (!watched.add(mob.getUniqueId())) {
            return;
        }
        mob.getScheduler().runAtFixedRate(plugin, task -> {
            if (!mob.isLeashed()) {
                watched.remove(mob.getUniqueId());
                task.cancel();
                return;
            }
            Entity holder = mob.getLeashHolder();
            if (holder.getWorld() != mob.getWorld()) {
                return;
            }
            int breakDistance = Rules.get(mob.getWorld(), Rules.LEASH_BREAK_DISTANCE);
            double distanceSquared = mob.getLocation().distanceSquared(holder.getLocation());
            if (distanceSquared > (double) breakDistance * breakDistance) {
                mob.setLeashHolder(null);
                mob.getWorld().dropItemNaturally(mob.getLocation(), new org.bukkit.inventory.ItemStack(Material.LEAD));
                watched.remove(mob.getUniqueId());
                task.cancel();
                return;
            }
            int elastic = Rules.get(mob.getWorld(), Rules.LEASH_ELASTIC_DISTANCE);
            if (elastic < 6 && distanceSquared > (double) elastic * elastic && distanceSquared <= 36) {
                double distance = Math.sqrt(distanceSquared);
                var pull = holder.getLocation().toVector().subtract(mob.getLocation().toVector()).multiply(1.0 / distance);
                mob.setVelocity(mob.getVelocity().add(pull.multiply(0.4 * (distance - elastic) / 6.0)));
            }
        }, () -> watched.remove(mob.getUniqueId()), 1L, 1L);
    }
}
