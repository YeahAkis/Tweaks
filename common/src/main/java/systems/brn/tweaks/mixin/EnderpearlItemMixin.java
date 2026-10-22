package systems.brn.tweaks.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import systems.brn.tweaks.Rules;

@Mixin(EnderpearlItem.class)
public abstract class EnderpearlItemMixin {
    @Redirect(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ThrownEnderpearl;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V")
    )
    private void tweaks$velocity(ThrownEnderpearl pearl, Entity shooter, float xRot, float yRot, float z, float power, float divergence) {
        Level level = shooter.level();
        float newPower = level.getGameRules().getInt(Rules.ENDER_PEARL_POWER) / 100f;
        float newDivergence = level.getGameRules().getInt(Rules.ENDER_PEARL_DIVERGENCE) / 100f;
        pearl.shootFromRotation(shooter, xRot, yRot, z, newPower, newDivergence);
    }
}
