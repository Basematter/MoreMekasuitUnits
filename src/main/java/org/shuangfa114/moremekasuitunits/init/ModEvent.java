package org.shuangfa114.moremekasuitunits.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.init.thirst.ThirstModulesInit;

import java.util.function.Supplier;

public class ModEvent {
    @SubscribeEvent
    public static void onInterModeEnqueue(InterModEnqueueEvent event) {
        sendTo(EquipmentSlot.MAINHAND, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        sendTo(EquipmentSlot.CHEST, MekanismModulesInit.MODULE_ELYTRA_ACCELERATION_UNIT);
        sendTo(EquipmentSlot.MAINHAND, MekanismModulesInit.MODULE_LOOTING_MODIFICATION_UNIT);
        if (MoreMekasuitUnits.isTaczLoaded) {
            sendTo(EquipmentSlot.CHEST, TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
            sendTo(EquipmentSlot.CHEST, TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT);
            sendTo(EquipmentSlot.CHEST, TaczModulesInit.MODULE_QUICK_SPRINTSHOOT_UNIT);
            sendTo(EquipmentSlot.CHEST, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT);
        }
        if (MoreMekasuitUnits.isThirstLoaded) {
            sendTo(EquipmentSlot.HEAD, ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT);
        }
    }

    private static void sendTo(EquipmentSlot slot, Supplier<?> thing) {
        String method = "null";
        switch (slot) {
            case HEAD -> method = "add_meka_suit_helmet_modules";
            case CHEST -> method = "add_meka_suit_bodyarmor_modules";
            case LEGS -> method = "add_meka_suit_pants_modules";
            case FEET -> method = "add_meka_suit_boots_modules";
            case MAINHAND, OFFHAND -> method = "add_meka_tool_modules";
        }
        InterModComms.sendTo("mekanism", method, thing);
    }
}
