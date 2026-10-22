package systems.brn.tweaks.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import systems.brn.tweaks.Rules;

@Mixin(PathfinderMob.class)
public abstract class PathfinderMobMixin {
    @ModifyConstant(method = "tickLeash", constant = @Constant(floatValue = 10.0F))
    private float tweaks$breakDistance(float original) {
        return ((Entity) (Object) this).level().getGameRules().getInt(Rules.LEASH_BREAK_DISTANCE);
    }

    @ModifyConstant(method = "tickLeash", constant = @Constant(floatValue = 6.0F))
    private float tweaks$elasticDistance(float original) {
        return ((Entity) (Object) this).level().getGameRules().getInt(Rules.LEASH_ELASTIC_DISTANCE);
    }
}
