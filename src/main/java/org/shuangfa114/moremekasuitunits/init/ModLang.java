package org.shuangfa114.moremekasuitunits.init;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public enum ModLang implements ILangEntry {
    MODULE_QUICK_AIMING("quick_aiming"),
    MODULE_QUICK_AIMING_HUD("quick_aiming_hud"),
    MODULE_RECOIL_OFFSET("recoil_offset"),
    MODULE_RECOIL_OFFSET_HUD("quick_aiming_hud"),
    MODULE_QUICK_RELOADING("quick_reloading"),
    MODULE_QUICK_RELOADING_HUD("quick_reloading_hud"),
    MODULE_FLAME_THROWER("flame_thrower"),
    MODULE_ELYTRA_ACCELERATION("elytra_acceleration"),
    MODULE_ELYTRA_ACCELERATION_HUD("elytra_acceleration_hud"),
    MODULE_LOOTING_MODIFICATION("looting_modification"),
    MODULE_LOOTING_MODIFICATION_HUD("looting_modification_hud");
    private final String key;

    ModLang(String path){
        this.key = Util.makeDescriptionId("module", new ResourceLocation(MoreMekasuitUnits.MODID,path));
    }
    @Override
    public @NotNull String getTranslationKey() {
        return this.key;
    }
}
