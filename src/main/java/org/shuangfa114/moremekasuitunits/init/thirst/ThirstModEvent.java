package org.shuangfa114.moremekasuitunits.init.thirst;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class ThirstModEvent {
    @SubscribeEvent
    public static void onInterModeEnqueue(InterModEnqueueEvent event) {
        String modid = "mekanism";
        InterModComms.sendTo(modid, "add_meka_suit_helmet_modules", ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT);
    }
}
