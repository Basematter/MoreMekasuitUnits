package org.shuangfa114.moremekasuitunits.init.mekanism;

import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class MekanismItemInit {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MoreMekasuitUnits.MODID);
    public static final ItemRegistryObject<ItemModule> FLAME_THROWER_UNIT;
    public static final ItemRegistryObject<ItemModule> ELYTRA_ACCELERATION_UNIT;
    public static final ItemRegistryObject<ItemModule> LOOTING_MODIFICATION_UNIT;
    static {
        FLAME_THROWER_UNIT=ITEMS.registerModule(MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        ELYTRA_ACCELERATION_UNIT=ITEMS.registerModule(MekanismModulesInit.MODULE_ELYTRA_ACCELERATION_UNIT);
        LOOTING_MODIFICATION_UNIT=ITEMS.registerModule(MekanismModulesInit.MODULE_LOOTING_MODIFICATION_UNIT);
    }
}
