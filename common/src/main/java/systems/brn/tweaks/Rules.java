package systems.brn.tweaks;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GameRules;
import systems.brn.tweaks.mixin.accessors.GameRulesInvoker;
import systems.brn.tweaks.mixin.accessors.IntegerValueInvoker;

public final class Rules {
    public static GameRules.Key<GameRules.IntegerValue> ENTITY_PORTAL_COOLDOWN;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_PEARL_DAMAGE;
    public static GameRules.Key<GameRules.IntegerValue> SHIELD_AXE_COOLDOWN;
    public static GameRules.Key<GameRules.IntegerValue> CRITICAL_MULTIPLIER;
    public static GameRules.Key<GameRules.IntegerValue> CREEPER_FUSE_TIME;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_PEARL_POWER;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_PEARL_DIVERGENCE;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_EYE_POP_CHANCE;
    public static GameRules.Key<GameRules.IntegerValue> LEASH_BREAK_DISTANCE;
    public static GameRules.Key<GameRules.IntegerValue> LEASH_ELASTIC_DISTANCE;

    private static boolean registered;

    private Rules() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        registered = true;
        ENTITY_PORTAL_COOLDOWN = integer("entityPortalCooldown", GameRules.Category.MOBS, 300, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DAMAGE = integer("enderPearlDamage", GameRules.Category.PLAYER, 5, 0, Integer.MAX_VALUE);
        SHIELD_AXE_COOLDOWN = integer("shieldAxeCooldown", GameRules.Category.PLAYER, 100, 0, Integer.MAX_VALUE);
        CRITICAL_MULTIPLIER = integer("criticalHitMultiplier", GameRules.Category.PLAYER, 150, 0, Integer.MAX_VALUE);
        CREEPER_FUSE_TIME = integer("creeperDefaultFuseTime", GameRules.Category.MOBS, 30, 0, Integer.MAX_VALUE);
        ENDER_PEARL_POWER = integer("enderPearlPower", GameRules.Category.PLAYER, 150, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DIVERGENCE = integer("enderPearlDivergence", GameRules.Category.PLAYER, 100, 0, Integer.MAX_VALUE);
        ENDER_EYE_POP_CHANCE = integer("enderEyePopChance", GameRules.Category.PLAYER, 20, 0, 100);
        LEASH_BREAK_DISTANCE = integer("leashBreakDistance", GameRules.Category.MOBS, 10, 0, Integer.MAX_VALUE);
        LEASH_ELASTIC_DISTANCE = integer("leashElasticDistance", GameRules.Category.MOBS, 6, 0, Integer.MAX_VALUE);
    }

    private static GameRules.Key<GameRules.IntegerValue> integer(String name, GameRules.Category category, int def, int min, int max) {
        return GameRulesInvoker.tweaks$register(name, category, IntegerValueInvoker.tweaks$create(def, min, max, FeatureFlagSet.of(), (server, value) -> {
        }));
    }
}
