package org.shuangfa114.moremekasuitunits.util;

import mekanism.api.gear.ModuleData;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

import java.util.HashSet;
import java.util.Set;

public interface ICustomModuleWithConflicting {
    default void addConflicting(ModuleData<?> moduleData, Set<ModuleData<?>> moduleDataSet) {
        MoreMekasuitUnits.conflictingModules.putIfAbsent(moduleData, new HashSet<>());
        for (ModuleData<?> data : moduleDataSet){
            MoreMekasuitUnits.conflictingModules.get(moduleData).add(data);
            MoreMekasuitUnits.conflictingModules.putIfAbsent(data, new HashSet<>());
            MoreMekasuitUnits.conflictingModules.get(data).add(moduleData);
        }
    }
}
