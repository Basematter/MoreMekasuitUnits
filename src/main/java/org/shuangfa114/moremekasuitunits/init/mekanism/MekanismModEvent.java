package org.shuangfa114.moremekasuitunits.init.mekanism;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class MekanismModEvent {
    @SubscribeEvent
    public static void onInterModeEnqueue(InterModEnqueueEvent event) {
        String modid = "mekanism";
        InterModComms.sendTo(modid, "add_meka_tool_modules", MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        InterModComms.sendTo(modid,"add_meka_suit_bodyarmor_modules",MekanismModulesInit.MODULE_ELYTRA_ACCELERATION_UNIT);
    }
}
