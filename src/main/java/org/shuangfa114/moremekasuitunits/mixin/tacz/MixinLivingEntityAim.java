package org.shuangfa114.moremekasuitunits.mixin.tacz;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.entity.shooter.LivingEntityAim;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczUnitConfig;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleQuickAimingUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.shuangfa114.moremekasuitunits.util.tacz.IShooterDataHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityAim.class, remap = false)
public abstract class MixinLivingEntityAim {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Shadow
    @Final
    private ShooterDataHolder data;

    @ModifyArg(method = "tickAimingProgress", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 1)
    public float aimTimeWithMekasuit(float aimTime) {
        IModule<ModuleQuickAimingUnit> module = UnitUtil.getUnit(this.shooter, TaczModulesInit.MODULE_QUICK_AIMING_UNIT, EquipmentSlot.CHEST);
        if (module != null) {
            FloatingLong floatingLong = UnitUtil.convertToFE(TaczUnitConfig.base.energyUsageQuickAiming.get().multiply(1 / module.getCustomInstance().getAimTime()));
            if(UnitUtil.isValid(module,this.shooter,floatingLong)){
                aimTime *= module.getCustomInstance().getAimTime();
            }
        }
        return aimTime;
    }

    @Definition(id = "aimingProgress", field = "Lcom/tacz/guns/entity/shooter/ShooterDataHolder;aimingProgress:F")
    @Expression("?.aimingProgress = ?.aimingProgress+?")
    @Inject(method = "tickAimingProgress", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    public void onAiming(CallbackInfo ci) {
        IShooterDataHolder iShooterDataHolder = ((IShooterDataHolder) this.data);
        if (!iShooterDataHolder.getLastAim()) {
            IModule<ModuleQuickAimingUnit> module = UnitUtil.getUnit(this.shooter, TaczModulesInit.MODULE_QUICK_AIMING_UNIT, EquipmentSlot.CHEST);
            if (module != null) {
                FloatingLong floatingLong = UnitUtil.convertToFE(TaczUnitConfig.base.energyUsageQuickAiming.get().multiply(1 / module.getCustomInstance().getAimTime()));
                if(UnitUtil.isValid(module,this.shooter,floatingLong)){
                    module.useEnergy(this.shooter, floatingLong);
                }
            }
            iShooterDataHolder.setLastAim(true);
        }
    }

    @Definition(id = "aimingProgress", field = "Lcom/tacz/guns/entity/shooter/ShooterDataHolder;aimingProgress:F")
    @Expression("?.aimingProgress = ?.aimingProgress-?")
    @Inject(method = "tickAimingProgress", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    public void onNotAiming(CallbackInfo ci) {
        IShooterDataHolder iShooterDataHolder = ((IShooterDataHolder) this.data);
        if (iShooterDataHolder.getLastAim()) {
            IModule<ModuleQuickAimingUnit> module = UnitUtil.getUnit(this.shooter, TaczModulesInit.MODULE_QUICK_AIMING_UNIT, EquipmentSlot.CHEST);
            if (module != null) {
                FloatingLong floatingLong = UnitUtil.convertToFE(TaczUnitConfig.base.energyUsageQuickAiming.get().multiply(1 / module.getCustomInstance().getAimTime()));
                if(UnitUtil.isValid(module,this.shooter,floatingLong)){
                    module.useEnergy(this.shooter, floatingLong);
                }
            }
            iShooterDataHolder.setLastAim(false);
        }
    }

    @ModifyExpressionValue(method = "lambda$tickSprint$1", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunData;getSprintTime()F"))
    public float sprintTime(float original) {
        IModule<?> unit = UnitUtil.getUnit(this.shooter, TaczModulesInit.MODULE_QUICK_SPRINTSHOOT_UNIT, EquipmentSlot.CHEST);
        if (unit != null && unit.isEnabled()) {
            original *= 0.5F;
        }
        return original;
    }
}
