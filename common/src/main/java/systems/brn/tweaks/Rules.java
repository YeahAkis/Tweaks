package systems.brn.tweaks;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import systems.brn.tweaks.mixin.accessors.GameRulesInvoker;

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

    private static boolean registered;

    private Rules() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        registered = true;
        ENTITY_PORTAL_COOLDOWN = integer("entity_portal_cooldown", GameRuleCategory.MOBS, 300, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DAMAGE = integer("ender_pearl_damage", GameRuleCategory.PLAYER, 5, 0, Integer.MAX_VALUE);
        SHIELD_AXE_COOLDOWN = integer("shield_axe_cooldown", GameRuleCategory.PLAYER, 100, 0, Integer.MAX_VALUE);
        CRITICAL_MULTIPLIER = integer("critical_hit_multiplier", GameRuleCategory.PLAYER, 150, 0, Integer.MAX_VALUE);
        CREEPER_FUSE_TIME = integer("creeper_default_fuse_time", GameRuleCategory.MOBS, 30, 0, Integer.MAX_VALUE);
        ENDER_PEARL_POWER = integer("ender_pearl_power", GameRuleCategory.PLAYER, 150, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DIVERGENCE = integer("ender_pearl_divergence", GameRuleCategory.PLAYER, 100, 0, Integer.MAX_VALUE);
        ENDER_EYE_POP_CHANCE = integer("ender_eye_pop_chance", GameRuleCategory.PLAYER, 20, 0, 100);
        LEASH_BREAK_DISTANCE = integer("leash_break_distance", GameRuleCategory.MOBS, 12, 0, Integer.MAX_VALUE);
        LEASH_ELASTIC_DISTANCE = integer("leash_elastic_distance", GameRuleCategory.MOBS, 6, 0, Integer.MAX_VALUE);
    }

    private static GameRule<Integer> integer(String name, GameRuleCategory category, int def, int min, int max) {
        return GameRulesInvoker.tweaks$registerInteger(name, category, def, min, max);
    }
}
