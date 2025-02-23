package org.shuangfa114.moremekasuitunits.mixin.thirst;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mekanism.client.render.item.MekaSuitBarDecorator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.OptionalInt;

@Mixin(value = MekaSuitBarDecorator.class,remap = false)
public abstract class MixinMekaSuitBarDecorator {
    @WrapOperation(method = "render",at = @At(value = "INVOKE", target = "Lmekanism/common/util/FluidUtils;getRGBDurabilityForDisplay(Lnet/minecraft/world/item/ItemStack;)Ljava/util/OptionalInt;"))
    public OptionalInt getRGBDurabilityForDisplay(ItemStack itemStack, Operation<OptionalInt> original, @Local FluidStack stack){
        if (!stack.isEmpty()) {
            if (stack.getFluid().isSame(Fluids.LAVA)) {
                return OptionalInt.of(-2397415);
            }
            if (FMLEnvironment.dist.isClient()) {
                return OptionalInt.of(IClientFluidTypeExtensions.of(stack.getFluid()).getTintColor(stack));
            }
        }
        return OptionalInt.empty();
    }
}
