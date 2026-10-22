package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import systems.brn.tweaks.Rules;

@Mixin(EnderpearlItem.class)
public abstract class EnderpearlItemMixin {
    @Redirect(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;spawnProjectileFromRotation(Lnet/minecraft/world/entity/projectile/Projectile$ProjectileFactory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;FFF)Lnet/minecraft/world/entity/projectile/Projectile;")
    )
    private <T extends Projectile> T tweaks$velocity(Projectile.ProjectileFactory<T> factory, ServerLevel level, ItemStack stack, LivingEntity shooter, float yaw, float power, float divergence) {
        float newPower = level.getGameRules().get(Rules.ENDER_PEARL_POWER) / 100f;
        float newDivergence = level.getGameRules().get(Rules.ENDER_PEARL_DIVERGENCE) / 100f;
        return Projectile.spawnProjectileFromRotation(factory, level, stack, shooter, yaw, newPower, newDivergence);
    }
}
