package org.shuangfa114.moremekasuitunits.init;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import mekanism.common.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;

public class ModConfig {
    public static final Config base = new Config();

    public static class Config extends BaseMekanismConfig {
        //mekanism
        public final CachedFloatingLongValue energyUsageFlameThrower;
        public final CachedFloatingLongValue energyUsageElytraAccelerationEachTick;
        public final CachedFloatingLongValue energyUsageLootingModification;
        //tacz
        public final CachedFloatingLongValue energyUsageQuickAiming;
        public final CachedFloatingLongValue energyUsageQuickReloading;
        public final CachedFloatingLongValue energyUsageRecoilOffset;
        //thirst
        public final CachedIntValue drinkingMBPerDrinking;
        public final CachedIntValue thirstPerDrinking;
        public final CachedIntValue quenchedPerDrinking;
        public final CachedFloatingLongValue energyUsageAutomaticDrinking;
        public final CachedIntValue cleanWaterTransferRate;
        public final CachedIntValue cleanWaterMaxStorage;
        private final ForgeConfigSpec configSpec;

        Config() {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
            //mekanism
            builder.comment("Mekanism Settings").push("mekanism");
            energyUsageFlameThrower = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit when throwing flame",
                    "energyUsageFlameThrower",
                    FloatingLong.createConst(250L));
            energyUsageElytraAccelerationEachTick = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit per tick per tier when elytra acceleration",
                    "energyUsageElytraAccelerationEachTick",
                    FloatingLong.createConst(2500L));
            energyUsageLootingModification = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit per tier each looting modification",
                    "energyUsageLootingModification",
                    FloatingLong.createConst(2500L));
            builder.pop();
            //tacz
            builder.comment("Tacz Settings").push("tacz");
            energyUsageQuickAiming = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit per tier when quick aiming",
                    "energyUsageQuickAiming",
                    FloatingLong.createConst(1000L));
            energyUsageQuickReloading = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit per tier when quick reloading",
                    "energyUsageQuickReloading",
                    FloatingLong.createConst(1000L));
            energyUsageRecoilOffset = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit per tier when shooting recoil offsetting",
                    "energyUsageRecoilOffset",
                    FloatingLong.createConst(1000L));
            builder.pop();
            //thirst
            builder.comment("Thirst Settings").push("thirst");
            drinkingMBPerDrinking = CachedIntValue.wrap(this, builder
                    .comment("Mb of pure water needed per drinking")
                    .defineInRange("mbPerDrinking", 75, 1, Integer.MAX_VALUE));
            energyUsageAutomaticDrinking = CachedFloatingLongValue.define(this, builder,
                    "Energy usage (FE) of MekaSuit when automatic drinking",
                    "energyUsageAutomaticDrinking",
                    FloatingLong.createConst(2500L));
            thirstPerDrinking = CachedIntValue.wrap(this, builder
                    .comment("Thirst provided by each automatic drinking")
                    .defineInRange("thirstPerAutomaticDrinking", 1, 1, Integer.MAX_VALUE));
            quenchedPerDrinking = CachedIntValue.wrap(this, builder
                    .comment("Quenched provided by each automatic drinking")
                    .defineInRange("quenchedPerAutomaticDrinking", 2, 1, Integer.MAX_VALUE));
            cleanWaterTransferRate = CachedIntValue.wrap(this, builder
                    .comment("Rate at which Clean Water can be transferred into the unit")
                    .defineInRange("cleanWaterTransferRate", 500, 1, Integer.MAX_VALUE));
            cleanWaterMaxStorage = CachedIntValue.wrap(this, builder
                    .comment("Maximum amount of Clean Water storable by the unit")
                    .defineInRange("cleanWaterMaxStorage", 50000, 1, Integer.MAX_VALUE));

            configSpec = builder.build();
        }

        @Override
        public String getFileName() {
            return MoreMekasuitUnits.MODID;
        }

        @Override
        public ForgeConfigSpec getConfigSpec() {
            return configSpec;
        }

        @Override
        public net.minecraftforge.fml.config.ModConfig.Type getConfigType() {
            return net.minecraftforge.fml.config.ModConfig.Type.SERVER;
        }
    }
}
