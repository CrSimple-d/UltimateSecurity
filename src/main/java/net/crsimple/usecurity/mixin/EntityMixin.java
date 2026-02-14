package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.client.ClientCore;
import net.crsimple.usecurity.common.fluid.FakeLava;
import net.crsimple.usecurity.common.fluid.FakeWater;
import net.crsimple.usecurity.common.registry.ModDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private World world;

    @Inject(method = "setOnFireFromLava",at = @At("HEAD"),cancellable = true)
    private void setOnFireFromLava(CallbackInfo ci) {
        if((Object)this instanceof LivingEntity living) {
            if (ClientCore.isInFluid(living,FakeLava.class)) {
                living.extinguish();
                if(!living.hasStatusEffect(StatusEffects.REGENERATION)) {
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,18,2,false,false));
                }
                if(!living.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,18,0,false,false));
                }
                ci.cancel();
            }
        }
    }

    @Inject(method = "updateWaterState",at = @At("HEAD"))
    private void updateWaterState(CallbackInfoReturnable<Boolean> cir) {
        if((Object)this instanceof LivingEntity living) {
            if (ClientCore.isInFluid(living,FakeWater.class)) {
                living.damage(ModDamageTypes.of(world,ModDamageTypes.FAKE_WATER),4f);
            }
        }
    }
}
