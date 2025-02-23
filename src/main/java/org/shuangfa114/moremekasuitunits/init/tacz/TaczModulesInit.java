package org.shuangfa114.moremekasuitunits.init.tacz;

import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.world.item.Rarity;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleQuickAimingUnit;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleRecoilOffsetUnit;

public class TaczModulesInit {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MoreMekasuitUnits.MODID);
    public static final ModuleRegistryObject<ModuleQuickAimingUnit> MODULE_QUICK_AIMING_UNIT;
    public static final ModuleRegistryObject<ModuleRecoilOffsetUnit> MODULE_RECOIL_OFFSET_UNIT;
    public static final ModuleRegistryObject<ModuleQuickReloadingUnit> MODULE_QUICK_RELOADING_UNIT;
    public static final ModuleRegistryObject<?> MODULE_QUICK_SPRINTSHOOT_UNIT;
    public TaczModulesInit(){
    }
    static {
        MODULE_QUICK_AIMING_UNIT = MODULES.register("quick_aiming_unit", ModuleQuickAimingUnit::new, ()->{
            return TaczItemInit.QUICK_AIMING_UNIT.asItem();
        },(m)-> m.maxStackSize(4).rarity(Rarity.RARE).rendersHUD());
        MODULE_RECOIL_OFFSET_UNIT = MODULES.register("recoil_offset_unit",ModuleRecoilOffsetUnit::new,()->{
            return TaczItemInit.RECOIL_OFFSET_UNIT.asItem();
        },(m)-> m.maxStackSize(4).rarity(Rarity.RARE).rendersHUD());
        MODULE_QUICK_SPRINTSHOOT_UNIT = MODULES.registerMarker("quick_sprintshoot_unit",()->{
            return TaczItemInit.QUICK_SPRINTSHOOT_UNIT.asItem();
        },(m)-> m.rarity(Rarity.RARE));
        MODULE_QUICK_RELOADING_UNIT = MODULES.register("quick_reloading_unit",ModuleQuickReloadingUnit::new,()->{
            return TaczItemInit.QUICK_RELOADING_UNIT.asItem();
        },(m)-> m.maxStackSize(4).rarity(Rarity.RARE).rendersHUD());
    }
}
