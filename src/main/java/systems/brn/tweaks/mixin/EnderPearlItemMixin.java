package systems.brn.tweaks.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import systems.brn.tweaks.Tweaks;

@Mixin(EnderPearlItem.class)
public class EnderPearlItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void injectCustomVelocity(World world, PlayerEntity user, Hand hand,
                                      CallbackInfoReturnable<ActionResult> cir) {

        if (!(world instanceof ServerWorld serverWorld)) return;

        ItemStack stack = user.getStackInHand(hand);

        float power = serverWorld.getGameRules().getInt(Tweaks.ENDER_PEARL_POWER) / 100f;
        float divergence = serverWorld.getGameRules().getInt(Tweaks.ENDER_PEARL_DIVERGENCE) / 100f;

        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                net.minecraft.sound.SoundEvents.ENTITY_ENDER_PEARL_THROW,
                net.minecraft.sound.SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        net.minecraft.entity.projectile.ProjectileEntity.spawnWithVelocity(
                EnderPearlEntity::new,
                serverWorld,
                stack,
                user,
                0f,
                power,
                divergence
        );

        user.incrementStat(net.minecraft.stat.Stats.USED.getOrCreateStat((EnderPearlItem)(Object)this));
        stack.decrementUnlessCreative(1, user);

        cir.setReturnValue(ActionResult.SUCCESS);
    }
}
