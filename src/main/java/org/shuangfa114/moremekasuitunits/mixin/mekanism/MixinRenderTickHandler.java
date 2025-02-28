package org.shuangfa114.moremekasuitunits.mixin.mekanism;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import mekanism.api.gear.IModule;
import mekanism.client.render.RenderTickHandler;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.module.gear.mekanism.ModuleFlameThrowerUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = RenderTickHandler.class)
public abstract class MixinRenderTickHandler {
    @ModifyExpressionValue(method = "tickEnd",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
    private Item fakeThrower(Item original, @Local(ordinal = 1) Player player){
        IModule<ModuleFlameThrowerUnit> module = UnitUtil.getMekaToolUnit(player, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        if(UnitUtil.isValidWithNull(module,player, ModConfig.base.energyUsageFlameThrower.get())){
            return MekanismItems.FLAMETHROWER.asItem();
        }
        return original;
    }
    @ModifyExpressionValue(method = "tickEnd",at = @At(value = "INVOKE", target = "Lmekanism/common/util/ChemicalUtil;hasGas(Lnet/minecraft/world/item/ItemStack;)Z"),remap = false)
    private boolean isArmorHasGas(boolean original, @Local(ordinal = 1) Player player){
        IModule<ModuleFlameThrowerUnit> module = UnitUtil.getMekaToolUnit(player, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        if(UnitUtil.isValidWithNull(module,player, ModConfig.base.energyUsageFlameThrower.get())){
            ItemStack itemStack = UnitUtil.getEquipment(player, EquipmentSlot.CHEST);
            return ChemicalUtil.hasChemical(itemStack, MekanismGases.HYDROGEN.getChemical());
        }
        return original;
    }
}
