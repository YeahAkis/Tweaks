package systems.brn.tweaks.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static systems.brn.tweaks.Tweaks.ENTITY_PORTAL_COOLDOWN;

@Mixin(net.minecraft.entity.Entity.class)
public class JustTeleportAlreadyMixin {
    @Shadow
    private World world;

    @Inject(method = "getDefaultPortalCooldown",
            at = @At(value = "RETURN"),
            cancellable = true)
    private void getDefaultPortalCooldown(CallbackInfoReturnable<Integer> cir) {
        if (world instanceof ServerWorld sWorld) {
            cir.setReturnValue(sWorld.getGameRules().getInt(ENTITY_PORTAL_COOLDOWN));
        }
    }

    @Shadow
    private int portalCooldown;

    @Inject(method = "tickPortalCooldown", at = @At(value = "RETURN"))
    private void tickPortalCooldown(CallbackInfo ci) {
        if (world instanceof ServerWorld sWorld) {
            int maxCooldown = sWorld.getGameRules().getInt(ENTITY_PORTAL_COOLDOWN);
            if (portalCooldown > maxCooldown) {
                portalCooldown = maxCooldown;
            }
        }
    }
}
