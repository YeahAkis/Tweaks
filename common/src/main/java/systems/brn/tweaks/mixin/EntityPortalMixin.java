package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import systems.brn.tweaks.Rules;

@Mixin(Entity.class)
public abstract class EntityPortalMixin {
    @Shadow
    private int portalCooldown;

    @Shadow
    public abstract net.minecraft.world.level.Level level();

    @Inject(method = "getDimensionChangingDelay", at = @At("RETURN"), cancellable = true)
    private void tweaks$delay(CallbackInfoReturnable<Integer> cir) {
        if (level() instanceof ServerLevel level) {
            cir.setReturnValue(level.getGameRules().getInt(Rules.ENTITY_PORTAL_COOLDOWN));
        }
    }

    @Inject(method = "processPortalCooldown", at = @At("RETURN"))
    private void tweaks$clamp(CallbackInfo ci) {
        if (level() instanceof ServerLevel level) {
            int max = level.getGameRules().getInt(Rules.ENTITY_PORTAL_COOLDOWN);
            if (portalCooldown > max) {
                portalCooldown = max;
            }
        }
    }
}
