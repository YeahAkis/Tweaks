package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import systems.brn.tweaks.Rules;

@Mixin(EyeOfEnder.class)
public abstract class EyeOfEnderMixin {
    @Shadow
    private boolean surviveAfterDeath;

    @Inject(method = "signalTo", at = @At("TAIL"))
    private void tweaks$popChance(Vec3 pos, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        if (self.level() instanceof ServerLevel level) {
            int chance = level.getGameRules().getInt(Rules.ENDER_EYE_POP_CHANCE);
            this.surviveAfterDeath = self.getRandom().nextInt(100) >= chance;
        }
    }
}
