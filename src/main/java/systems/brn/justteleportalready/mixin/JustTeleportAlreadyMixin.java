package systems.brn.justteleportalready.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static systems.brn.justteleportalready.Justteleportalready.Entity_Portal_Cooldown;

@Mixin(net.minecraft.entity.Entity.class)
public class JustTeleportAlreadyMixin {
    @Shadow private World world;

    @Inject(method = "getDefaultPortalCooldown",
            at = @At(value = "RETURN"),
            cancellable = true)
    private void getDefaultPortalCooldown(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(world.getGameRules().getInt(Entity_Portal_Cooldown));
    }

    @Shadow
    private int portalCooldown;
    @Inject(method = "tickPortalCooldown", at = @At(value = "RETURN"))
    private void tickPortalCooldown(CallbackInfo ci) {
        int maxCooldown = world.getGameRules().getInt(Entity_Portal_Cooldown);
        if (portalCooldown > maxCooldown) {
            portalCooldown = maxCooldown;
        }
    }
}
