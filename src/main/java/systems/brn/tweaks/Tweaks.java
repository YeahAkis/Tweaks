package systems.brn.tweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class Tweaks implements ModInitializer {
    public static final GameRules.Key<GameRules.IntRule> Entity_Portal_Cooldown =
                GameRuleRegistry.register("entityPortalCooldown", GameRules.Category.MOBS, GameRuleFactory.createIntRule(300));
    public static final GameRules.Key<GameRules.IntRule> Enderpearl_Damage =
            GameRuleRegistry.register("enderPearlDamage", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(5));
    @Override
    public void onInitialize() {
    }
}
