package systems.brn.tweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class Tweaks implements ModInitializer {
    public static final GameRules.Key<GameRules.IntRule> ENTITY_PORTAL_COOLDOWN =
            GameRuleRegistry.register("entityPortalCooldown", GameRules.Category.MOBS, GameRuleFactory.createIntRule(300, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> ENDER_PEARL_DAMAGE =
            GameRuleRegistry.register("enderPearlDamage", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(5, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> SHIELD_AXE_COOLDOWN =
            GameRuleRegistry.register("shieldAxeCooldown", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(100, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> CRITICAL_MULTIPLIER =
            GameRuleRegistry.register("criticalHitMultiplier", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(150, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> CREEPER_FUSE_TIME =
            GameRuleRegistry.register("creeperDefaultFuseTime", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(30, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> ENDER_PEARL_POWER =
            GameRuleRegistry.register("enderPearlPower", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(150, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> ENDER_PEARL_DIVERGENCE =
            GameRuleRegistry.register("enderPearlDivergence", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(100, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> ENDER_EYE_POP_CHANCE =
            GameRuleRegistry.register("enderEyePopChance", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(20, 0, 100));

    public static final GameRules.Key<GameRules.IntRule> LEASH_BREAK_DISTANCE =
            GameRuleRegistry.register("leashBreakDistance", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(10, 0, Integer.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> LEASH_ELASTIC_DISTANCE =
            GameRuleRegistry.register("leashElasticDistance", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(6, 0, Integer.MAX_VALUE));

    @Override
    public void onInitialize() {
    }
}
