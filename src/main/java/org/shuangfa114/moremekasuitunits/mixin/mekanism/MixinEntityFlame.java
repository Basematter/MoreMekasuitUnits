package org.shuangfa114.moremekasuitunits.mixin.mekanism;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import mekanism.api.gear.IModule;
import mekanism.common.entity.EntityFlame;
import mekanism.common.registries.MekanismItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.shuangfa114.moremekasuitunits.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitunits.module.gear.mekanism.ModuleFlameThrowerUnit;
import org.shuangfa114.moremekasuitunits.util.UnitUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityFlame.class)
public abstract class MixinEntityFlame {
    @ModifyExpressionValue(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
    private static Item test(Item original, @Local(argsOnly = true) Player player){
        return MekanismItems.FLAMETHROWER.asItem();
    }
    @Inject(method = "create",at = @At(value = "INVOKE", target = "Lmekanism/common/entity/EntityFlame;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V"))
    private static void getMekaToolFlameMod(Player player, CallbackInfoReturnable<EntityFlame> cir,@Local EntityFlame entityFlame){
        IModule<ModuleFlameThrowerUnit> module = UnitUtil.getMekaToolUnit(player, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        if(module!=null){
            ((EntityFlameAccessor) entityFlame).setMode(module.getCustomInstance().getFlameMode());
        }
    }
}
