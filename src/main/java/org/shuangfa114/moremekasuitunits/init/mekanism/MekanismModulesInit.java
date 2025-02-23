package org.shuangfa114.moremekasuitunits.init.mekanism;

import mekanism.api.gear.ModuleData;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.world.item.Rarity;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;
import org.shuangfa114.moremekasuitunits.module.gear.mekanism.ModuleElytraAccelerationUnit;
import org.shuangfa114.moremekasuitunits.module.gear.mekanism.ModuleFlameThrowerUnit;

public class MekanismModulesInit {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MoreMekasuitUnits.MODID);
    public static final ModuleRegistryObject<ModuleFlameThrowerUnit> MODULE_FLAME_THROWER_UNIT;
    public static final ModuleRegistryObject<ModuleElytraAccelerationUnit> MODULE_ELYTRA_ACCELERATION_UNIT;

    static {
        MODULE_FLAME_THROWER_UNIT = MODULES.register("flame_thrower_unit", ModuleFlameThrowerUnit::new, () -> {
            return MekanismItemInit.FLAME_THROWER_UNIT.asItem();
        }, (m) -> m.rarity(Rarity.RARE).exclusive(ModuleData.ExclusiveFlag.INTERACT_ANY).rendersHUD());
        MODULE_ELYTRA_ACCELERATION_UNIT = MODULES.register("elytra_acceleration_unit", ModuleElytraAccelerationUnit::new, () -> {
            return MekanismItemInit.ELYTRA_ACCELERATION_UNIT.asItem();
        }, (m) -> m.rarity(Rarity.EPIC).maxStackSize(3).exclusive(ModuleData.ExclusiveFlag.OVERRIDE_JUMP).rendersHUD());
    }
}
