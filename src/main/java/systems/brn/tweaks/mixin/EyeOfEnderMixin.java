package systems.brn.tweaks.mixin;

import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import systems.brn.tweaks.Tweaks;
import systems.brn.tweaks.mixin.accessors.EyeOfEnderEntityAccessor;

@Mixin(EyeOfEnderEntity.class)
public class EyeOfEnderMixin {

    @Inject(
            method = "initTargetPos",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/EyeOfEnderEntity;dropsItem:Z",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void applyCustomBreakChance(CallbackInfo ci) {
        EyeOfEnderEntity self = (EyeOfEnderEntity)(Object)this;
        World world = self.getWorld();

        if (!(world instanceof ServerWorld serverWorld))
            return;

        int popChance = serverWorld.getGameRules().getInt(Tweaks.ENDER_EYE_POP_CHANCE);
        int roll = self.getRandom().nextInt(100);

        boolean dropsItem = roll >= popChance;

        ((EyeOfEnderEntityAccessor) self).setDropsItem(dropsItem);
    }
}