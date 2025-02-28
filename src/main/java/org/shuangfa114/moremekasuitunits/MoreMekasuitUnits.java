package org.shuangfa114.moremekasuitunits;

import mekanism.api.gear.ModuleData;
import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.shuangfa114.moremekasuitunits.init.ModEvent;
import org.shuangfa114.moremekasuitunits.init.ModTabs;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismItemInit;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczItemInit;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.init.thirst.ThirstItemInit;
import org.shuangfa114.moremekasuitunits.init.thirst.ThirstModulesInit;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoreMekasuitUnits.MODID)
public class MoreMekasuitUnits {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "moremekasuitunits";

    public static Map<ModuleData<?>, Set<ModuleData<?>>> conflictingModules = new IdentityHashMap<>();

    public static boolean isTaczLoaded;
    public static boolean isThirstLoaded;

    public MoreMekasuitUnits(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModContainer modContainer = context.getContainer();
        MinecraftForge.EVENT_BUS.register(this);
        isTaczLoaded = ModList.get().isLoaded("tacz");
        isThirstLoaded = ModList.get().isLoaded("thirst");
        //Mekanism
        MekanismItemInit.ITEMS.register(modEventBus);
        MekanismModulesInit.MODULES.register(modEventBus);
        MekanismConfigHelper.registerConfig(modContainer, ModConfig.base);
        //Tacz
        if(isTaczLoaded){
            TaczItemInit.ITEMS.register(modEventBus);
            TaczModulesInit.MODULES.register(modEventBus);
        }
        //Thirst
        if(isThirstLoaded){
            ThirstItemInit.ITEMS.register(modEventBus);
            ThirstModulesInit.MODULES.register(modEventBus);
        }
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.register(ModEvent.class);
    }

}
