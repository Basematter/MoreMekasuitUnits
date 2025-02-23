package org.shuangfa114.moremekasuitunits.mixin.tacz;


import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.client.event.CameraSetupEvent;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczUnitConfig;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleRecoilOffsetUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = CameraSetupEvent.class,remap = false)
public abstract class MixinCameraSetupEvent {
    @ModifyArg(method = "initialCameraRecoil", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunRecoil;genPitchSplineFunction(F)Lorg/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction;"))
    private static float pitch(float modifier, @Local LocalPlayer shooter) {
        IModule<ModuleRecoilOffsetUnit> module = UnitUtil.getUnit(shooter, TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT, EquipmentSlot.CHEST);
        if (module != null) {
            FloatingLong floatingLong = TaczUnitConfig.base.energyUsageRecoilOffset.get().multiply(1/module.getCustomInstance().getRecoil());
            if(UnitUtil.isValid(module,shooter,floatingLong)){
                modifier *= module.getCustomInstance().getRecoil();
                module.useEnergy(shooter, floatingLong);
            }
        }
        return modifier;
    }

    @ModifyArg(method = "initialCameraRecoil", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunRecoil;genYawSplineFunction(F)Lorg/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction;"))
    private static float yaw(float modifier, @Local LocalPlayer shooter) {
        IModule<ModuleRecoilOffsetUnit> module = UnitUtil.getUnit(shooter, TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT, EquipmentSlot.CHEST);
        if (module != null && module.isEnabled()) {
            modifier *= module.getCustomInstance().getRecoil();
        }
        return modifier;
    }
}
