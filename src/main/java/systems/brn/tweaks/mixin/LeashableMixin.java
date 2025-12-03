package systems.brn.tweaks.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import systems.brn.tweaks.Tweaks;

@Mixin(Leashable.class)
public interface LeashableMixin {

    @ModifyConstant(
            method = "tickLeash",
            constant = @Constant(doubleValue = 10.0)
    )
    private static double modifyLeashBreakDistance(double original, ServerWorld world, Entity entity) {
        GameRules.IntRule rule = world.getGameRules().get(Tweaks.LEASH_BREAK_DISTANCE);
        return rule.get();
    }

    @ModifyConstant(
            method = "tickLeash",
            constant = @Constant(doubleValue = 6.0)
    )
    private static double modifyLeashElasticDistance(double original, ServerWorld world, Entity entity) {
        GameRules.IntRule rule = world.getGameRules().get(Tweaks.LEASH_ELASTIC_DISTANCE);
        return rule.get();
    }
}