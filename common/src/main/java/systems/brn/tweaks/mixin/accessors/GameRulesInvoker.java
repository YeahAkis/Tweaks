package systems.brn.tweaks.mixin.accessors;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.class)
public interface GameRulesInvoker {
    @Invoker("registerInteger")
    static GameRule<Integer> tweaks$registerInteger(String name, GameRuleCategory category, int def, int min, int max) {
        throw new AssertionError();
    }
}
