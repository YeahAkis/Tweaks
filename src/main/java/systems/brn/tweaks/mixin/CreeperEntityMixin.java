package systems.brn.tweaks.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import systems.brn.tweaks.Tweaks;
import systems.brn.tweaks.mixin.accessors.CreeperEntityAccessor;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onConstructor(EntityType<? extends CreeperEntity> entityType, World world, CallbackInfo ci) {
        CreeperEntity creeper = (CreeperEntity)(Object)this;
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            GameRules.IntRule rule = serverWorld.getGameRules().get(Tweaks.CREEPER_FUSE_TIME);
            ((CreeperEntityAccessor) creeper).setFuseTime(rule.get());
        }
    }
}