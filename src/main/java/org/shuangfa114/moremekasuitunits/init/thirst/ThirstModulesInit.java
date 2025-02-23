package org.shuangfa114.moremekasuitunits.init.thirst;

import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.world.item.Rarity;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;
import org.shuangfa114.moremekasuitunits.module.gear.thirst.ModuleAutomaticDrinkingUnit;

public class ThirstModulesInit {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MoreMekasuitUnits.MODID);
    public static final ModuleRegistryObject<ModuleAutomaticDrinkingUnit> MODULE_AUTOMATIC_DRINKING_UNIT;

    static {
        MODULE_AUTOMATIC_DRINKING_UNIT = MODULES.register("automatic_drinking_unit",ModuleAutomaticDrinkingUnit::new,()->{
            return ThirstItemInit.AUTOMATIC_DRINKING_UNIT.asItem();
        },(m)-> m.rarity(Rarity.RARE).rendersHUD());
    }
}
