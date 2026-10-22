package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import systems.brn.tweaks.Rules;

@Mixin(ThrownEnderpearl.class)
public abstract class EnderPearlDamageMixin {
    @ModifyArg(
            method = "onHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            index = 2
    )
    private float tweaks$damage(float original) {
        if (((Entity) (Object) this).level() instanceof ServerLevel level) {
            return level.getGameRules().get(Rules.ENDER_PEARL_DAMAGE);
        }
        return original;
    }
}
