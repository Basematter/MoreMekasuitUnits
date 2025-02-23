package org.shuangfa114.moremekasuitunits.mixin.mekanism;

import mekanism.common.entity.EntityFlame;
import mekanism.common.item.gear.ItemFlamethrower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityFlame.class)
public interface EntityFlameAccessor {
    @Accessor(value = "mode",remap = false)
    void setMode(ItemFlamethrower.FlamethrowerMode flamethrowerMode);
}
