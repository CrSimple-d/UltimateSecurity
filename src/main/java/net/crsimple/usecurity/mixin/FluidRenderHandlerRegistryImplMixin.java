package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.client.ClientCore;
import net.crsimple.usecurity.common.registry.ModFluids;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.impl.client.rendering.fluid.FluidRenderHandlerRegistryImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRenderHandlerRegistryImpl.class)
public abstract class FluidRenderHandlerRegistryImplMixin {
    @Inject(method = "onFluidRendererReload",at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/impl/client/rendering/fluid/FluidRenderHandlerRegistryImpl;register(Lnet/minecraft/fluid/Fluid;Lnet/fabricmc/fabric/api/client/render/fluid/v1/FluidRenderHandler;)V",shift = At.Shift.AFTER))
    private void onFluidRendererReload(FluidRenderer renderer, Sprite[] waterSprites, Sprite[] lavaSprites, Sprite waterOverlay, CallbackInfo ci) {
        ((FluidRenderHandlerRegistry)this).register(ModFluids.STILL_FAKE_WATER,ModFluids.FLOWING_FAKE_WATER,ClientCore.createFakeWaterRenderHandler(waterSprites, waterOverlay));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_FAKE_WATER, ModFluids.FLOWING_FAKE_WATER);
        ((FluidRenderHandlerRegistry)this).register(ModFluids.STILL_FAKE_LAVA,ModFluids.FLOWING_FAKE_LAVA,ClientCore.createFakeLavaRenderHandler(lavaSprites));
    }
}
