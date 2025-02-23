package org.shuangfa114.moremekasuitunits.init.mekanism;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import mekanism.common.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class MekanismUnitConfig {
    public static final Config base = new Config();
    public static class Config extends BaseMekanismConfig {
        private final ForgeConfigSpec configSpec;
        public final CachedFloatingLongValue energyUsageFlameThrower;
        public final CachedFloatingLongValue energyUsageElytraAccelerationEachTick;
        Config(){
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
            builder.comment("Energy Settings").push("energy");
            energyUsageFlameThrower=CachedFloatingLongValue.define(this,builder,
                    "Energy usage (FE) of MekaSuit when throwing flame",
                    "energyUsageFlameThrower",
                    FloatingLong.createConst(250L));
            energyUsageElytraAccelerationEachTick=CachedFloatingLongValue.define(this,builder,
                    "Energy usage (FE) of MekaSuit per tick per tier when elytra acceleration",
                    "energyUsageElytraAccelerationEachTick",
                    FloatingLong.createConst(2500L));
            configSpec=builder.build();
        }
        @Override
        public String getFileName() {
            return MoreMekasuitUnits.MODID+"_mekanism_unit";
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
