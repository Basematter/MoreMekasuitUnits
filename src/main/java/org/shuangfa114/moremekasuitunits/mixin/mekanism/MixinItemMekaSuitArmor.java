package org.shuangfa114.moremekasuitunits.mixin.mekanism;

import mekanism.common.item.gear.ItemMekaSuitArmor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(value = ItemMekaSuitArmor.class,remap = false)
public abstract class MixinItemMekaSuitArmor {
    @ModifyArg(method = "<init>",at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/chemical/item/ChemicalTankSpec;createFillOnly(Ljava/util/function/LongSupplier;Ljava/util/function/LongSupplier;Ljava/util/function/Predicate;Ljava/util/function/Predicate;)Lmekanism/common/capabilities/chemical/item/ChemicalTankSpec;"),index = 3)
    public Predicate<@NotNull ItemStack> returnTrue(Predicate<@NotNull ItemStack> supportsStack){
        return (itemStack)->true;
    }
}
