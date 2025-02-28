package org.shuangfa114.moremekasuitunits.mixin.tacz;

import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.index.CommonGunIndex;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.shuangfa114.moremekasuitunits.init.ModConfig;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = ModernKineticGunItem.class, remap = false)
public abstract class MixinModernKineticGunItem {
    @Shadow
    protected abstract LuaFunction checkFunction(LuaValue luaValue);

    @Shadow
    protected abstract ReloadState defaultTickReload(ModernKineticGunScriptAPI api);

    @Inject(method = "tickReload", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/item/ModernKineticGunScriptAPI;getGunIndex()Lcom/tacz/guns/resource/index/CommonGunIndex;", shift = At.Shift.AFTER), cancellable = true)
    private void test(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter, CallbackInfoReturnable<ReloadState> cir, @Local ModernKineticGunScriptAPI api) {
        CommonGunIndex gunIndex = api.getGunIndex();
        cir.setReturnValue(gunIndex == null ? new ReloadState() : Optional.ofNullable(gunIndex.getScript()).map((script) -> this.checkFunction(script.get("tick_reload"))).map((func) -> {
            ReloadState reloadState = new ReloadState();
            Varargs varargs = func.invoke(CoerceJavaToLua.coerce(api));
            int typeOrdinary = varargs.arg(1).checkint();
            long countDown = varargs.arg(2).checklong();
            ReloadState.StateType reloadType = ReloadState.StateType.values()[typeOrdinary];
            if (reloadType.isReloadFinishing()) {
                IModule<ModuleQuickReloadingUnit> module = UnitUtil.getUnit(shooter, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT, EquipmentSlot.CHEST);
                if (module != null) {
                    FloatingLong floatingLong = UnitUtil.convertToFE(ModConfig.base.energyUsageQuickReloading.get().multiply(1 / module.getCustomInstance().getReloadingTime()));
                    if (UnitUtil.isValid(module, shooter, floatingLong)) {
                        module.useEnergy(shooter, floatingLong);
                    }
                }
            }
            reloadState.setStateType(reloadType);
            reloadState.setCountDown(countDown);
            return reloadState;
        }).orElseGet(() -> this.defaultTickReload(api)));
    }

    @Inject(method = "defaultReloadFinishing", at = @At("HEAD"))
    public void test1(ModernKineticGunScriptAPI api, boolean isTactical, CallbackInfo ci) {
        LivingEntity shooter = api.getShooter();
        IModule<ModuleQuickReloadingUnit> module = UnitUtil.getUnit(shooter, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT, EquipmentSlot.CHEST);
        if (module != null) {
            FloatingLong floatingLong = UnitUtil.convertToFE(ModConfig.base.energyUsageQuickReloading.get().multiply(1 / module.getCustomInstance().getReloadingTime()));
            if (UnitUtil.isValid(module, shooter, floatingLong)) {
                module.useEnergy(shooter, floatingLong);
            }
        }
    }
}

