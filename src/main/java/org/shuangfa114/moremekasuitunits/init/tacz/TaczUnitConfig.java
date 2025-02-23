package org.shuangfa114.moremekasuitunits.init.tacz;


import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class TaczUnitConfig {
    public static final TaczUnitConfig.Config base = new Config();
    public static class Config extends BaseMekanismConfig {
        private final ForgeConfigSpec configSpec;
        public final CachedFloatingLongValue energyUsageQuickAiming;
        public final CachedFloatingLongValue energyUsageQuickReloading;
        public final CachedFloatingLongValue energyUsageRecoilOffset;
        Config(){
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
            builder.comment("Energy Settings").push("energy");
            energyUsageQuickAiming=CachedFloatingLongValue.define(this,builder,
                    "Energy usage (FE) of MekaSuit per tier when quick aiming",
                    "energyUsageQuickAiming",
                    FloatingLong.createConst(1000L));
            energyUsageQuickReloading=CachedFloatingLongValue.define(this,builder,
                    "Energy usage (FE) of MekaSuit per tier when quick reloading",
                    "energyUsageQuickReloading",
                    FloatingLong.createConst(1000L));
            energyUsageRecoilOffset=CachedFloatingLongValue.define(this,builder,
                    "Energy usage (FE) of MekaSuit per tier when shooting recoil offsetting",
                    "energyUsageRecoilOffset",
                    FloatingLong.createConst(1000L));
            configSpec=builder.build();
        }
        @Override
        public String getFileName() {
            return MoreMekasuitUnits.MODID+"_tacz_unit";
        }

        @Override
        public ForgeConfigSpec getConfigSpec() {
            return configSpec;
        }

        @Override
        public ModConfig.Type getConfigType() {
            return ModConfig.Type.SERVER;
        }
    }
}
