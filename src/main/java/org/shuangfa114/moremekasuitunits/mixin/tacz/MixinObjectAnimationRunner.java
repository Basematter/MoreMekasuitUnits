package org.shuangfa114.moremekasuitunits.mixin.tacz;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.client.animation.ObjectAnimationRunner;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.shuangfa114.moremekasuitunits.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitunits.module.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nonnull;

@Mixin(value = ObjectAnimationRunner.class,remap = false)
public abstract class MixinObjectAnimationRunner {
    @Shadow @Final @Nonnull private ObjectAnimation animation;

    @Definition(id = "lastUpdateNs", field = "Lcom/tacz/guns/api/client/animation/ObjectAnimationRunner;lastUpdateNs:J")
    @Expression("? - this.lastUpdateNs")
    @ModifyExpressionValue(method = "update", at = @At("MIXINEXTRAS:EXPRESSION"))
    public long reloadingTime(long original){
        if(this.animation.name.contains("reload")){
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            IModule<ModuleQuickReloadingUnit> module = UnitUtil.getUnit(localPlayer, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT, EquipmentSlot.CHEST);
            if (UnitUtil.isValidWithNull(module, localPlayer, FloatingLong.ZERO)) {
                original = (long) (original * (1 / module.getCustomInstance().getReloadingTime()));
            }
        }
        return original;
    }
}
