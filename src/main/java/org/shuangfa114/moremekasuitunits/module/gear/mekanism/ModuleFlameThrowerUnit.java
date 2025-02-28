package org.shuangfa114.moremekasuitunits.module.gear.mekanism;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.IModuleConfigItem;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.gear.config.ModuleEnumData;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTextComponent;
import mekanism.common.item.gear.ItemFlamethrower;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.shuangfa114.moremekasuitunits.init.ModLang;

import java.util.function.Consumer;

public class ModuleFlameThrowerUnit implements ICustomModule<ModuleFlameThrowerUnit> {
    private IModuleConfigItem<FlameMode> flameMode;

    public ModuleFlameThrowerUnit() {
    }

    @Override
    public void init(IModule<ModuleFlameThrowerUnit> module, ModuleConfigItemCreator configItemCreator) {
        this.flameMode = configItemCreator.createConfigItem("flame_mode", ModLang.MODULE_FLAME_THROWER, new ModuleEnumData(FlameMode.COMBAT, 3));
    }

    public ItemFlamethrower.FlamethrowerMode getFlameMode() {
        return this.flameMode.get().getFlameMode();
    }

    public Component getTextComponent(){return this.flameMode.get().getTextComponent();}

    @Override
    public void addHUDStrings(IModule<ModuleFlameThrowerUnit> module, Player player, Consumer<Component> hudStringAdder) {
        if(module.isEnabled()){
            hudStringAdder.accept(ModLang.MODULE_FLAME_THROWER.translateColored(EnumColor.DARK_GRAY).append(": ").append(getTextComponent()));
        }
    }

    @NothingNullByDefault
    public enum FlameMode implements IHasTextComponent {
        COMBAT(ItemFlamethrower.FlamethrowerMode.COMBAT),
        HEAT(ItemFlamethrower.FlamethrowerMode.HEAT),
        INFERNO(ItemFlamethrower.FlamethrowerMode.INFERNO);

        private final ItemFlamethrower.FlamethrowerMode flameMode;
        private final Component label;

        FlameMode(ItemFlamethrower.FlamethrowerMode mode) {
            this.flameMode = mode;
            this.label = mode.getTextComponent();
        }

        public Component getTextComponent() {
            return this.label;
        }

        public ItemFlamethrower.FlamethrowerMode getFlameMode() {
            return this.flameMode;
        }
    }
}
