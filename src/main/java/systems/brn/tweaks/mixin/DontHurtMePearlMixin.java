package systems.brn.tweaks.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static systems.brn.tweaks.Tweaks.ENDER_PEARL_DAMAGE;

@Mixin(net.minecraft.entity.projectile.thrown.EnderPearlEntity.class)
public class DontHurtMePearlMixin {

    @Redirect(method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;damage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean redirectDamage(ServerPlayerEntity entity, ServerWorld world, DamageSource source, float amount) {
        entity.damage(world, source, world.getGameRules().getInt(ENDER_PEARL_DAMAGE));
        return false;
    }
}
