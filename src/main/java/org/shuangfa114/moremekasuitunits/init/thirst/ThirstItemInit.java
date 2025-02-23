package org.shuangfa114.moremekasuitunits.init.thirst;

import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class ThirstItemInit {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MoreMekasuitUnits.MODID);
    public static final ItemRegistryObject<ItemModule> AUTOMATIC_DRINKING_UNIT;
    static {
        AUTOMATIC_DRINKING_UNIT=ITEMS.registerModule(ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT);
    }
}
