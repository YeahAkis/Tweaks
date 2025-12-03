package systems.brn.tweaks.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import systems.brn.tweaks.Tweaks;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @ModifyConstant(
            method = "disableShield(Lnet/minecraft/item/ItemStack;)V",
            constant = @Constant(intValue = 100)
    )
    private int modifyShieldCooldownConstant(int original) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getWorld() != null && player.getWorld() instanceof ServerWorld sWorld) {
            GameRules.IntRule rule = sWorld.getGameRules().get(Tweaks.SHIELD_AXE_COOLDOWN);
            return rule.get();
        }
        return original;
    }

    @ModifyConstant(
            method = "attack",
            constant = @Constant(floatValue = 1.5F)
    )
    private float modifyCriticalMultiplier(float original) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getWorld() != null && player.getWorld() instanceof ServerWorld serverWorld) {
            GameRules.IntRule rule = serverWorld.getGameRules().get(Tweaks.CRITICAL_MULTIPLIER);
            return rule.get() / 100.0F;
        }
        return original;
    }
}