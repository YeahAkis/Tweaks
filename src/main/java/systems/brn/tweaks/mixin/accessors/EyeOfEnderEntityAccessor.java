package systems.brn.tweaks.mixin.accessors;

import net.minecraft.entity.EyeOfEnderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EyeOfEnderEntity.class)
public interface EyeOfEnderEntityAccessor {
    @Accessor("dropsItem")
    void setDropsItem(boolean value);
}
