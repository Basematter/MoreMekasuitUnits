package org.shuangfa114.moremekasuitunits.init.tacz;

import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class TaczItemInit {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MoreMekasuitUnits.MODID);
    public static final ItemRegistryObject<ItemModule> QUICK_AIMING_UNIT;
    public static final ItemRegistryObject<ItemModule> RECOIL_OFFSET_UNIT;
    public static final ItemRegistryObject<ItemModule> QUICK_SPRINTSHOOT_UNIT;
    public static final ItemRegistryObject<ItemModule> QUICK_RELOADING_UNIT;
    public TaczItemInit() {
    }
    static {
        QUICK_AIMING_UNIT=ITEMS.registerModule(TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
        RECOIL_OFFSET_UNIT=ITEMS.registerModule(TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT);
        QUICK_SPRINTSHOOT_UNIT=ITEMS.registerModule(TaczModulesInit.MODULE_QUICK_SPRINTSHOOT_UNIT);
        QUICK_RELOADING_UNIT=ITEMS.registerModule(TaczModulesInit.MODULE_QUICK_RELOADING_UNIT);
    }
}
