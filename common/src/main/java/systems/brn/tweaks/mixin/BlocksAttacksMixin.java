package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import systems.brn.tweaks.Rules;

@Mixin(BlocksAttacks.class)
public abstract class BlocksAttacksMixin {
    @ModifyVariable(method = "disable", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float tweaks$disableSeconds(float seconds, ServerLevel level, LivingEntity entity, float original, ItemStack stack) {
        return seconds * level.getGameRules().getInt(Rules.SHIELD_AXE_COOLDOWN) / 100.0F;
    }
}
