package org.shuangfa114.moremekasuitunits.module.gear.thirst;

import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StorageUtils;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.shuangfa114.moremekasuitunits.MoreMekasuitUnits;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;

import java.util.Optional;
import java.util.function.Consumer;

public class ModuleAutomaticDrinkingUnit implements ICustomModule<ModuleAutomaticDrinkingUnit> {
    public static ResourceLocation icon = new ResourceLocation(MoreMekasuitUnits.MODID, MekanismUtils.ResourceType.GUI_HUD.getPrefix()+"automatic_drinking_unit.png");
    public ModuleAutomaticDrinkingUnit() {
    }

    @Override
    public void tickServer(IModule<ModuleAutomaticDrinkingUnit> module, Player player) {
        FloatingLong usage = UnitUtil.convertToFE(ModConfig.base.energyUsageAutomaticDrinking.get());
        if (MekanismUtils.isPlayingMode(player)) {
            player.getCapability(ModCapabilities.PLAYER_THIRST).ifPresent((cap) -> {
                ItemStack container = module.getContainer();
                ItemMekaSuitArmor item = (ItemMekaSuitArmor) container.getItem();
                CompoundTag purity = new CompoundTag();
                purity.putInt("Purity", 3);
                int mbPerDrinking = ModConfig.base.drinkingMBPerDrinking.get();
                int needed = Math.min(20 - cap.getThirst(), item.getContainedFluid(container, new FluidStack(Fluids.WATER, 1, purity)).getAmount() / mbPerDrinking);
                int toDrink = Math.min(module.getContainerEnergy().divideToInt(usage), needed);
                if (toDrink >= 1) {
                    module.useEnergy(player, UnitDisplayUtils.EnergyUnit.FORGE_ENERGY.convertFrom(usage));
                    FluidUtil.getFluidHandler(container).ifPresent((handler) -> {
                        handler.drain(new FluidStack(Fluids.WATER, mbPerDrinking, purity), IFluidHandler.FluidAction.EXECUTE);
                    });
                    cap.drink(player, ModConfig.base.thirstPerDrinking.get(), ModConfig.base.quenchedPerDrinking.get());
                }
            });
        }
    }

    @Override
    public void addHUDElements(IModule<ModuleAutomaticDrinkingUnit> module, Player player, Consumer<IHUDElement> hudElementAdder) {
        if (module.isEnabled()) {
            ItemStack container = module.getContainer();
            Optional<IFluidHandlerItem> capability = FluidUtil.getFluidHandler(container).resolve();
            CompoundTag purity = new CompoundTag();
            purity.putInt("Purity", 3);
            int max = ModConfig.base.cleanWaterMaxStorage.getAsInt();
            if (capability.isPresent()) {
                IFluidHandlerItem handler = capability.get();
                handler.drain(new FluidStack(Fluids.WATER, max, purity), IFluidHandler.FluidAction.SIMULATE);
            }
            FluidStack stored = ((ItemMekaSuitArmor) container.getItem()).getContainedFluid(container, new FluidStack(Fluids.WATER, 1, purity));
            double ratio = StorageUtils.getRatio(stored.getAmount(), max);
            hudElementAdder.accept(IModuleHelper.INSTANCE.hudElementPercent(icon, ratio));
        }
    }
}
