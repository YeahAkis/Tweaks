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

    @Override
    public void onInitialize() {
    }
}
