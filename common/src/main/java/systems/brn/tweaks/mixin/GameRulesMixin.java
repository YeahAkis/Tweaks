package systems.brn.tweaks.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import systems.brn.tweaks.Rules;

@Mixin(GameRules.class)
public class GameRulesMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void tweaks$registerRules(CallbackInfo ci) {
        Rules.register();
    }
}
