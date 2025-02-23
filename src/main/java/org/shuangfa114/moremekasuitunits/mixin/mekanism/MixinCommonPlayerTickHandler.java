package org.shuangfa114.moremekasuitunits.mixin.mekanism;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import mekanism.common.CommonPlayerTickHandler;
import mekanism.common.item.gear.ItemFlamethrower;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismUnitConfig;
import org.shuangfa114.moremekasuitunits.module.gear.mekanism.ModuleFlameThrowerUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CommonPlayerTickHandler.class)
public abstract class MixinCommonPlayerTickHandler {
    @ModifyExpressionValue(method = "isFlamethrowerOn",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/mine craft/world/item/Item;"))
    private static Item fakeThrower(Item original, @Local(argsOnly = true) Player player){
        IModule<ModuleFlameThrowerUnit> module = UnitUtil.getMekaToolUnit(player, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        if(UnitUtil.isValidWithNull(module,player, MekanismUnitConfig.base.energyUsageFlameThrower.get())){
            return MekanismItems.FLAMETHROWER.asItem();
        }
        return original;
    }

    @ModifyExpressionValue(method = "tickEnd", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;",ordinal = 0))
    public Item useBodyArmorGas(Item original, @Local(argsOnly = true) Player player){
        return MekanismItems.FLAMETHROWER.asItem();
    }
    @WrapOperation(method = "tickEnd",at = @At(value = "INVOKE", target = "Lmekanism/common/item/gear/ItemFlamethrower;useGas(Lnet/minecraft/world/item/ItemStack;J)Lmekanism/api/chemical/gas/GasStack;"),remap = false)
    public GasStack useArmorGas(ItemFlamethrower instance, ItemStack itemStack, long l, Operation<GasStack> original,@Local(argsOnly = true) Player player){
        IModule<ModuleFlameThrowerUnit> module = UnitUtil.getMekaToolUnit(player, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        FloatingLong floatingLong = MekanismUnitConfig.base.energyUsageFlameThrower.get();
        if(UnitUtil.isValidWithNull(module,player, floatingLong)) {
            ItemStack armor = UnitUtil.getEquipment(player, EquipmentSlot.CHEST);
            if (armor.getItem() instanceof ItemMekaSuitArmor mekaSuitArmor) {
                module.useEnergy(player,floatingLong);
                return mekaSuitArmor.useGas(armor, MekanismGases.HYDROGEN.getChemical(), l * 2);
            }
        }
        return instance.useGas(itemStack,l);
    }
}
