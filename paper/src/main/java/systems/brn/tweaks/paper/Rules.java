package systems.brn.tweaks.paper;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

import java.lang.reflect.Method;

public final class Rules {
    public static GameRule<Integer> ENTITY_PORTAL_COOLDOWN;
    public static GameRule<Integer> ENDER_PEARL_DAMAGE;
    public static GameRule<Integer> SHIELD_AXE_COOLDOWN;
    public static GameRule<Integer> CRITICAL_MULTIPLIER;
    public static GameRule<Integer> CREEPER_FUSE_TIME;
    public static GameRule<Integer> ENDER_PEARL_POWER;
    public static GameRule<Integer> ENDER_PEARL_DIVERGENCE;
    public static GameRule<Integer> ENDER_EYE_POP_CHANCE;
    public static GameRule<Integer> LEASH_BREAK_DISTANCE;
    public static GameRule<Integer> LEASH_ELASTIC_DISTANCE;

    private Rules() {
    }

    public static void register() throws ReflectiveOperationException {
        Method register = GameRules.class.getDeclaredMethod("registerInteger", String.class, GameRuleCategory.class, int.class, int.class, int.class);
        register.setAccessible(true);

        ENTITY_PORTAL_COOLDOWN = integer(register, "entity_portal_cooldown", GameRuleCategory.MOBS, 300, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DAMAGE = integer(register, "ender_pearl_damage", GameRuleCategory.PLAYER, 5, 0, Integer.MAX_VALUE);
        SHIELD_AXE_COOLDOWN = integer(register, "shield_axe_cooldown", GameRuleCategory.PLAYER, 100, 0, Integer.MAX_VALUE);
        CRITICAL_MULTIPLIER = integer(register, "critical_hit_multiplier", GameRuleCategory.PLAYER, 150, 0, Integer.MAX_VALUE);
        CREEPER_FUSE_TIME = integer(register, "creeper_default_fuse_time", GameRuleCategory.MOBS, 30, 0, Integer.MAX_VALUE);
        ENDER_PEARL_POWER = integer(register, "ender_pearl_power", GameRuleCategory.PLAYER, 150, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DIVERGENCE = integer(register, "ender_pearl_divergence", GameRuleCategory.PLAYER, 100, 0, Integer.MAX_VALUE);
        ENDER_EYE_POP_CHANCE = integer(register, "ender_eye_pop_chance", GameRuleCategory.PLAYER, 20, 0, 100);
        LEASH_BREAK_DISTANCE = integer(register, "leash_break_distance", GameRuleCategory.MOBS, 12, 0, Integer.MAX_VALUE);
        LEASH_ELASTIC_DISTANCE = integer(register, "leash_elastic_distance", GameRuleCategory.MOBS, 6, 0, Integer.MAX_VALUE);
    }

    @SuppressWarnings("unchecked")
    private static GameRule<Integer> integer(Method register, String name, GameRuleCategory category, int def, int min, int max) throws ReflectiveOperationException {
        return (GameRule<Integer>) register.invoke(null, name, category, def, min, max);
    }

    public static int get(World world, GameRule<Integer> key) {
        return ((CraftWorld) world).getHandle().getGameRules().get(key);
    }
}
