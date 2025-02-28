package org.shuangfa114.moremekasuitunits.module.gear.mekanism;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.IModuleConfigItem;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.gear.config.ModuleEnumData;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.Mekanism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.init.ModLang;
import org.shuangfa114.moremekasuitunits.module.gear.WithOffMode;
import org.shuangfa114.moremekasuitunits.util.IModeEnum;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;

import java.util.function.Consumer;

public class ModuleElytraAccelerationUnit implements ICustomModule<ModuleElytraAccelerationUnit>, WithOffMode {
    private IModuleConfigItem<Acceleration> acceleration;

    public ModuleElytraAccelerationUnit() {
    }

    @Override
    public void init(IModule<ModuleElytraAccelerationUnit> module, ModuleConfigItemCreator configItemCreator) {
        this.acceleration = configItemCreator.createConfigItem("acceleration", ModLang.MODULE_ELYTRA_ACCELERATION, new ModuleEnumData(Acceleration.MEDIUM, module.getInstalledCount() + 1));
    }

    @Override
    public void tickServer(IModule<ModuleElytraAccelerationUnit> module, Player player) {
        FloatingLong floatingLong = getEnergyUsage();
        if (Mekanism.keyMap.has(player.getUUID(), 0) && player.isFallFlying() && UnitUtil.isValid(module, player, floatingLong)) {
            module.useEnergy(player, floatingLong);
        }
    }

    @Override
    public void tickClient(IModule<ModuleElytraAccelerationUnit> module, Player player) {
        if (player instanceof LocalPlayer) {
            if (Minecraft.getInstance().options.keyJump.isDown()) {
                if (player.isFallFlying()) {
                    FloatingLong floatingLong = getEnergyUsage();
                    if (UnitUtil.isValid(module, player, floatingLong) && getAcceleration() != 1F) {
                        Vec3 lookAngle = player.getLookAngle();
                        player.setDeltaMovement(lookAngle.scale(getAcceleration()));
                    }
                }
            }
        }
    }

    private FloatingLong getEnergyUsage() {
        return UnitUtil.convertToFE(ModConfig.base.energyUsageElytraAccelerationEachTick.get().multiply(getAcceleration()));
    }

    public float getAcceleration() {
        return this.acceleration.get().getAcceleration();
    }

    public Component getTextComponent() {
        return this.acceleration.get().getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleElytraAccelerationUnit> module, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_ELYTRA_ACCELERATION_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, this.getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return this.acceleration.get().getOffMode().equals(this.acceleration.get());
    }


    @NothingNullByDefault
    public enum Acceleration implements IModeEnum {
        OFF(1F),
        MEDIUM(2.5F),
        HIGH(5F),
        EXTREME(7.5F);

        private final float acceleration;
        private final Component label;

        Acceleration(float acceleration) {
            this.acceleration = acceleration;
            this.label = TextComponentUtil.getString((int) (acceleration * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        public float getAcceleration() {
            return this.acceleration;
        }

        @Override
        public IModeEnum getOffMode() {
            return OFF;
        }
    }
}
