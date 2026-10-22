package systems.brn.tweaks.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import systems.brn.tweaks.Rules;

@Mixin(Leashable.class)
public interface LeashableMixin {
    @ModifyConstant(method = "tickLeash", constant = @Constant(doubleValue = 10.0))
    private static double tweaks$breakDistance(double original, Entity entity) {
        return entity.level().getGameRules().getInt(Rules.LEASH_BREAK_DISTANCE);
    }

    @ModifyConstant(method = "tickLeash", constant = @Constant(doubleValue = 6.0))
    private static double tweaks$elasticDistance(double original, Entity entity) {
        return entity.level().getGameRules().getInt(Rules.LEASH_ELASTIC_DISTANCE);
    }
}
