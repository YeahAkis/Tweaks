package systems.brn.tweaks.paper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LeashPackets extends ChannelDuplexHandler {
    private static final String NAME = "tweaks_leash";
    private static final long WINDOW_NANOS = 150_000_000L;
    private static final double SOUND_RANGE_SQUARED = 16.0;
    private static final Map<Integer, Stretch> STRETCHED = new ConcurrentHashMap<>();

    private record Stretch(long nanos, double mobX, double mobY, double mobZ, double holderX, double holderY, double holderZ) {
        boolean fresh(long now) {
            return now - nanos < WINDOW_NANOS;
        }

        boolean near(double x, double y, double z) {
            return distanceSquared(x, y, z, mobX, mobY, mobZ) <= SOUND_RANGE_SQUARED
                    || distanceSquared(x, y, z, holderX, holderY, holderZ) <= SOUND_RANGE_SQUARED;
        }

        private static double distanceSquared(double ax, double ay, double az, double bx, double by, double bz) {
            double dx = ax - bx;
            double dy = ay - by;
            double dz = az - bz;
            return dx * dx + dy * dy + dz * dz;
        }
    }

    private LeashPackets() {
    }

    public static void mark(Mob mob, Entity holder) {
        long now = System.nanoTime();
        STRETCHED.values().removeIf(stretch -> !stretch.fresh(now));
        Location at = mob.getLocation();
        Location holderAt = holder.getLocation();
        STRETCHED.put(mob.getEntityId(), new Stretch(now, at.getX(), at.getY(), at.getZ(), holderAt.getX(), holderAt.getY(), holderAt.getZ()));
    }

    public static void inject(Player player) {
        Channel channel = channel(player);
        if (channel != null && channel.pipeline().get(NAME) == null) {
            channel.pipeline().addLast(NAME, new LeashPackets());
        }
    }

    public static void eject(Player player) {
        Channel channel = channel(player);
        if (channel != null && channel.pipeline().get(NAME) != null) {
            channel.pipeline().remove(NAME);
        }
    }

    private static Channel channel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.connection.channel;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (shouldDrop(msg)) {
            promise.setSuccess();
            return;
        }
        super.write(ctx, msg, promise);
    }

    private static boolean shouldDrop(Object msg) {
        if (STRETCHED.isEmpty()) {
            return false;
        }
        long now = System.nanoTime();
        if (msg instanceof ClientboundSetEntityLinkPacket link) {
            Stretch stretch = STRETCHED.get(link.getSourceId());
            return link.getDestId() == 0 && stretch != null && stretch.fresh(now);
        }
        if (msg instanceof ClientboundSoundPacket sound) {
            String name = sound.getSound().getRegisteredName();
            if (!name.contains(".lead.") && !name.contains("leash_knot")) {
                return false;
            }
            for (Stretch stretch : STRETCHED.values()) {
                if (stretch.fresh(now) && stretch.near(sound.getX(), sound.getY(), sound.getZ())) {
                    return true;
                }
            }
        }
        return false;
    }
}
