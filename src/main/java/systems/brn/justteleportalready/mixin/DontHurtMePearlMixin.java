package systems.brn.justteleportalready.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static systems.brn.justteleportalready.Justteleportalready.Enderpearl_Damage;

@Mixin(net.minecraft.entity.projectile.thrown.EnderPearlEntity.class)
public class DontHurtMePearlMixin {

    @Redirect(method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean redirectDamage(Entity entity, DamageSource source, float amount) {
        World world = entity.getWorld();
        entity.damage(source, world.getGameRules().getInt(Enderpearl_Damage));
        return false;
    }
}
