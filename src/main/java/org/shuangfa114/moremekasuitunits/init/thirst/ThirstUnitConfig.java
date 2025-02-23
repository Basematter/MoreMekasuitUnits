package org.shuangfa114.moremekasuitunits.init.thirst;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import mekanism.common.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class ThirstUnitConfig {
    public static final Config base = new Config();
    public static class Config extends BaseMekanismConfig {
        private final ForgeConfigSpec configSpec;
        public final CachedIntValue drinkingMBPerDrinking;
        public final CachedIntValue thirstPerDrinking;
        public final CachedIntValue quenchedPerDrinking;
        public final CachedFloatingLongValue energyUsageAutomaticDrinking;
        public final CachedIntValue cleanWaterTransferRate;
        public final CachedIntValue cleanWaterMaxStorage;
        Config(){
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
            builder.comment("Automatic Drinking Unit Settings").push("automatic_drinking_unit");
            drinkingMBPerDrinking=CachedIntValue.wrap(this,builder
                    .comment("Mb of pure water needed per drinking")
                    .defineInRange("mbPerDrinking", 75,1,Integer.MAX_VALUE));
            energyUsageAutomaticDrinking=CachedFloatingLongValue.define(this,builder,
                    "Energy usage (FE) of MekaSuit when automatic drinking",
                    "energyUsageAutomaticDrinking",
                    FloatingLong.createConst(2500L));
            thirstPerDrinking=CachedIntValue.wrap(this,builder
                    .comment("Thirst provided by each automatic drinking")
                    .defineInRange("thirstPerAutomaticDrinking",1,1,Integer.MAX_VALUE));
            quenchedPerDrinking=CachedIntValue.wrap(this,builder
                    .comment("Quenched provided by each automatic drinking")
                    .defineInRange("quenchedPerAutomaticDrinking",2,1,Integer.MAX_VALUE));
            cleanWaterTransferRate=CachedIntValue.wrap(this,builder
                    .comment("Rate at which Clean Water can be transferred into the unit")
                    .defineInRange("cleanWaterTransferRate",500,1,Integer.MAX_VALUE));
            cleanWaterMaxStorage=CachedIntValue.wrap(this,builder
                    .comment("Maximum amount of Clean Water storable by the unit")
                    .defineInRange("cleanWaterMaxStorage",50000,1,Integer.MAX_VALUE));
            configSpec=builder.build();
        }
        @Override
        public String getFileName() {
            return MoreMekasuitUnits.MODID+"_thirst_unit";
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
