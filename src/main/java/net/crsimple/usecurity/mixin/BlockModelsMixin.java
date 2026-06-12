package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.api.wrapper.SecurityBlockWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModels.class)
public abstract class BlockModelsMixin {
    @Shadow public abstract BakedModel getModel(BlockState state);

    @Inject(method = "getModel",at = @At("HEAD"), cancellable = true)
    public void getResource(BlockState state, CallbackInfoReturnable<BakedModel> cir) {
        if (state.getBlock() instanceof SecurityBlockWrapper wrapper) {
            cir.setReturnValue(getModel(wrapper.getWrappedBlock().getStateWithProperties(state)));
        }
    }
}
