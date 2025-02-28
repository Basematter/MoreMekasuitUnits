package org.shuangfa114.moremekasuitunits.util;

import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.shuangfa114.moremekasuitunits.module.gear.WithOffMode;

public class UnitUtil {
    public static <T extends ICustomModule<T>>@Nullable IModule<T> getUnit(LivingEntity livingEntity, IModuleDataProvider<T> unit,EquipmentSlot equipmentSlot) {
        if (livingEntity instanceof Player player) {
            ItemStack itemStack = getEquipment(player,equipmentSlot);
            return IModuleHelper.INSTANCE.load(itemStack,unit);
        }
        return null;
    }
    public static <T extends ICustomModule<T>> @Nullable IModule<T> getMekaToolUnit(LivingEntity livingEntity, IModuleDataProvider<T> unit){
        if (livingEntity instanceof Player player) {
            ItemStack itemStack = player.getInventory().getSelected();
            return IModuleHelper.INSTANCE.load(itemStack,unit);
        }
        return null;
    }
    public static ItemStack getEquipment(Player player, EquipmentSlot equipmentSlot){
        return player.getItemBySlot(equipmentSlot);
    }
    public static boolean isValidWithNull(IModule<?> module, LivingEntity livingEntity, FloatingLong floatingLong){
        return module!=null&&isValid(module,livingEntity,floatingLong);
    }
    public static boolean isValid(@NotNull IModule<?> module, LivingEntity livingEntity, FloatingLong floatingLong){
        boolean t = true;
        if(module.getCustomInstance() instanceof WithOffMode withOffMode){
            t=!withOffMode.isOffMode();
        }
        return module.isEnabled()&&t&&module.canUseEnergy(livingEntity, convertToFE(floatingLong));
    }
    public static FloatingLong convertToFE(FloatingLong floatingLong){
        return UnitDisplayUtils.EnergyUnit.FORGE_ENERGY.convertFrom(floatingLong);
    }
}
