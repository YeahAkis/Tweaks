package systems.brn.tweaks.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import systems.brn.tweaks.Rules;

@Mixin(Creeper.class)
public abstract class CreeperMixin {
    @Shadow
    private int maxSwell;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void tweaks$fuseTime(EntityType<? extends Creeper> type, Level level, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel) {
            this.maxSwell = serverLevel.getGameRules().get(Rules.CREEPER_FUSE_TIME);
        }
    }
}
