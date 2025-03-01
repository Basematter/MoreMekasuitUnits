package org.shuangfa114.moremekasuitunits.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeHooks;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitunits.module.gear.mekanism.ModuleLootingModificationUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ForgeHooks.class)
public class MixinForgeHooks {
    @ModifyReturnValue(method = "getLootingLevel(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)I", at = @At("RETURN"), remap = false)
    private static int modifyLootingLevel(int original, @Local(argsOnly = true, ordinal = 1) Entity killer) {
        if (killer instanceof Player player) {
            IModule<ModuleLootingModificationUnit> module = UnitUtil.getMekaToolUnit(player, MekanismModulesInit.MODULE_LOOTING_MODIFICATION_UNIT);
            FloatingLong floatingLong = ModConfig.base.energyUsageLootingModification.get();
            if (UnitUtil.isValidWithNull(module, player, floatingLong)) {
                original = (int) (Math.min((original <= 0 ? 1 : original), 15) * module.getCustomInstance().getMultiplier());
                module.useEnergy(player,floatingLong);
            }
        }
        return original;
    }
}
