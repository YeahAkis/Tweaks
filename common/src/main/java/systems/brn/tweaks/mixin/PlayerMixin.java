package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import systems.brn.tweaks.Rules;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyConstant(method = "disableShield", constant = @Constant(intValue = 100))
    private int tweaks$shieldCooldown(int original) {
        if (((Player) (Object) this).level() instanceof ServerLevel level) {
            return level.getGameRules().getInt(Rules.SHIELD_AXE_COOLDOWN);
        }
        return original;
    }

    @ModifyConstant(method = "attack", constant = @Constant(floatValue = 1.5F))
    private float tweaks$criticalMultiplier(float original) {
        if (((Player) (Object) this).level() instanceof ServerLevel level) {
            return level.getGameRules().getInt(Rules.CRITICAL_MULTIPLIER) / 100.0F;
        }
        return original;
    }
}
