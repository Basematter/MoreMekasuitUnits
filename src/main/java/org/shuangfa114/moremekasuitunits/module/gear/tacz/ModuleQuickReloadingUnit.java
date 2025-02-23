package org.shuangfa114.moremekasuitunits.module.gear.tacz;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.IModuleConfigItem;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.gear.config.ModuleEnumData;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTextComponent;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;
import org.shuangfa114.moremekasuitunits.init.ModLang;

import java.util.function.Consumer;

public class ModuleQuickReloadingUnit implements ICustomModule<ModuleQuickReloadingUnit> {
    private IModuleConfigItem<ReloadingTime> reloadingTime;
    public ModuleQuickReloadingUnit() {
    }


    @Override
    public void init(IModule<ModuleQuickReloadingUnit> module, ModuleConfigItemCreator configItemCreator) {
        this.reloadingTime = configItemCreator.createConfigItem("reloading_time", ModLang.MODULE_QUICK_RELOADING, new ModuleEnumData(ReloadingTime.LOW, module.getInstalledCount()+1));
    }

    public float getReloadingTime(){
        return this.reloadingTime.get().getReloadingTime();
    }

    public Component getTextComponent(){return this.reloadingTime.get().getTextComponent();}

    @Override
    public void addHUDStrings(IModule<ModuleQuickReloadingUnit> module, Player player, Consumer<Component> hudStringAdder) {
        if(module.isEnabled()){
            hudStringAdder.accept(ModLang.MODULE_QUICK_RELOADING_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO,getTextComponent().getString()));
        }
    }

    @NothingNullByDefault
    public enum ReloadingTime implements IHasTextComponent {
        OFF(1F),
        LOW(0.8F),
        MEDIUM(0.6F),
        HIGH(0.4F),
        EXTREME(0.2F);

        private final float ReloadingTime;
        private final Component label;

        ReloadingTime(float ReloadingTime) {
            this.ReloadingTime=ReloadingTime;
            this.label = TextComponentUtil.getString((int)(ReloadingTime*100)+"%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        public float getReloadingTime() {
            return this.ReloadingTime;
        }
    }
}
