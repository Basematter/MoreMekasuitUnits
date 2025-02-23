package org.shuangfa114.moremekasuitunits.mixin.tacz;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ModernKineticGunScriptAPI.class,remap = false)
public abstract class MixinModernKineticGunScriptAPI {
    @Shadow
    private LivingEntity shooter;

    @ModifyReturnValue(method = "getReloadTime", at = @At("RETURN"))
    public long reloadTime(long original) {
        IModule<ModuleQuickReloadingUnit> module = UnitUtil.getUnit(this.shooter, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT, EquipmentSlot.CHEST);
        if (UnitUtil.isValidWithNull(module, this.shooter, FloatingLong.ZERO)) {
            original = (long) (1/ module.getCustomInstance().getReloadingTime() * original);
        }
        return original;
    }
}
