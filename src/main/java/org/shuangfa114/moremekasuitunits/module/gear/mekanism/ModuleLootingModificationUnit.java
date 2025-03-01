package org.shuangfa114.moremekasuitunits.module.gear.mekanism;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.IModuleConfigItem;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.gear.config.ModuleEnumData;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.shuangfa114.moremekasuitunits.init.ModLang;
import org.shuangfa114.moremekasuitunits.module.gear.WithOffMode;
import org.shuangfa114.moremekasuitunits.util.IModeEnum;

import java.util.function.Consumer;

public class ModuleLootingModificationUnit implements ICustomModule<ModuleLootingModificationUnit>, WithOffMode {
    private IModuleConfigItem<Multiplier> multiplier;

    public ModuleLootingModificationUnit() {
    }

    @Override
    public void init(IModule<ModuleLootingModificationUnit> module, ModuleConfigItemCreator configItemCreator) {
        this.multiplier = configItemCreator.createConfigItem("looting_multiplier", ModLang.MODULE_LOOTING_MODIFICATION, new ModuleEnumData(Multiplier.MEDIUM, module.getInstalledCount() + 1));
    }

    public float getMultiplier() {
        return this.multiplier.get().getMultiplier();
    }

    public Component getTextComponent() {
        return this.multiplier.get().getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleLootingModificationUnit> module, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_LOOTING_MODIFICATION_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, this.getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return this.multiplier.get().getOffMode().equals(this.multiplier.get());
    }

    @NothingNullByDefault
    public enum Multiplier implements IModeEnum {
        OFF(1F),
        MEDIUM(2.5F),
        HIGH(5F);

        private final float multiplier;
        private final Component label;

        Multiplier(float multiplier) {
            this.multiplier = multiplier;
            this.label = TextComponentUtil.getString((int) (multiplier * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        public float getMultiplier() {
            return this.multiplier;
        }

        @Override
        public IModeEnum getOffMode() {
            return OFF;
        }
    }
}
