package org.shuangfa114.moremekasuitunits.module.gear.tacz;

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

public class ModuleRecoilOffsetUnit implements ICustomModule<ModuleRecoilOffsetUnit>, WithOffMode {
    private IModuleConfigItem<Recoil> recoil;

    public ModuleRecoilOffsetUnit() {
    }

    @Override
    public void init(IModule<ModuleRecoilOffsetUnit> module, ModuleConfigItemCreator configItemCreator) {
        this.recoil = configItemCreator.createConfigItem("recoil", ModLang.MODULE_RECOIL_OFFSET, new ModuleEnumData(Recoil.LOW, module.getInstalledCount() + 1));
    }

    public float getRecoil() {
        return this.recoil.get().getRecoil();
    }

    public Component getTextComponent() {
        return this.recoil.get().getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleRecoilOffsetUnit> module, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_RECOIL_OFFSET_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return this.recoil.get().getOffMode().equals(this.recoil.get());
    }

    @NothingNullByDefault
    public enum Recoil implements IModeEnum {
        OFF(1F),
        LOW(0.8F),
        MEDIUM(0.6F),
        HIGH(0.4F),
        EXTREME(0.2F);

        private final float recoil;
        private final Component label;

        Recoil(float recoil) {
            this.recoil = recoil;
            this.label = TextComponentUtil.getString((int) (recoil * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        public float getRecoil() {
            return this.recoil;
        }

        @Override
        public IModeEnum getOffMode() {
            return OFF;
        }
    }
}
