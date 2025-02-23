package org.shuangfa114.moremekasuitunits.init.tacz;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class TaczModEvent {
    @SubscribeEvent
    public static void onInterModeEnqueue(InterModEnqueueEvent event) {
        String modid = "mekanism";
        InterModComms.sendTo(modid, "add_meka_suit_bodyarmor_modules", TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
        InterModComms.sendTo(modid, "add_meka_suit_bodyarmor_modules", TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT);
        InterModComms.sendTo(modid, "add_meka_suit_bodyarmor_modules", TaczModulesInit.MODULE_QUICK_SPRINTSHOOT_UNIT);
        InterModComms.sendTo(modid, "add_meka_suit_bodyarmor_modules", TaczModulesInit.MODULE_QUICK_RELOADING_UNIT);
    }
}
