package systems.brn.tweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import systems.brn.tweaks.Rules;

@Mixin(Leashable.class)
public interface LeashableMixin {
    @Redirect(method = "tickLeash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;leashSnapDistance()D"))
    private static double tweaks$breakDistance(Leashable leashable) {
        double original = leashable.leashSnapDistance();
        if (original == 12.0 && ((Entity) leashable).level() instanceof ServerLevel level) {
            return level.getGameRules().getInt(Rules.LEASH_BREAK_DISTANCE);
        }
        return original;
    }

    @Redirect(method = "tickLeash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;leashElasticDistance()D"))
    private static double tweaks$elasticDistance(Leashable leashable) {
        double original = leashable.leashElasticDistance();
        if (original == 6.0 && ((Entity) leashable).level() instanceof ServerLevel level) {
            return level.getGameRules().getInt(Rules.LEASH_ELASTIC_DISTANCE);
        }
        return original;
    }
}
