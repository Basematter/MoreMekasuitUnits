package org.shuangfa114.moremekasuitunits.mixin.thirst;

import mekanism.common.capabilities.fluid.item.RateLimitMultiTankFluidHandler;
import mekanism.common.content.gear.IModuleContainerItem;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.item.gear.ItemSpecialArmor;
import mekanism.common.item.interfaces.IJetpackItem;
import mekanism.common.item.interfaces.IModeItem;
import mekanism.common.lib.attribute.IAttributeRefresher;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.init.thirst.ThirstModulesInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ItemMekaSuitArmor.class,remap = false)
public abstract class MixinItemMekaSuitArmor extends ItemSpecialArmor implements IModuleContainerItem, IModeItem, IJetpackItem, IAttributeRefresher, CreativeTabDeferredRegister.ICustomCreativeTabContents {
    @Shadow @Final private List<RateLimitMultiTankFluidHandler.FluidTankSpec> fluidTankSpecs;

    protected MixinItemMekaSuitArmor(ArmorMaterial material, Type armorType, Properties properties) {
        super(material, armorType, properties);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void addCleanWaterFluidTank(ArmorItem.Type armorType, Item.Properties properties, CallbackInfo ci) {
        if (MoreMekasuitUnits.isThirstLoaded&&armorType == ArmorItem.Type.HELMET) {
            CompoundTag purity = new CompoundTag();
            purity.putInt("Purity",3);
            this.fluidTankSpecs.add(RateLimitMultiTankFluidHandler.FluidTankSpec.createFillOnly(ModConfig.base.cleanWaterTransferRate, ModConfig.base.cleanWaterMaxStorage,
                    (fluidStack) -> fluidStack.isFluidEqual(new FluidStack(Fluids.WATER, 1, purity)),
                    (itemStack -> this.hasModule(itemStack, ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT))));
        }
    }
}
