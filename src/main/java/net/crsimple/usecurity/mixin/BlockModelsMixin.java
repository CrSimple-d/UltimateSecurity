package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.ReinforcedManager;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModels.class)
public abstract class BlockModelsMixin {
    @Shadow public abstract BakedModel getModel(BlockState state);

    @SuppressWarnings("unchecked")
    @Inject(method = "getModel",at = @At("HEAD"), cancellable = true)
    public void getResource(BlockState state, CallbackInfoReturnable<BakedModel> cir) {
        Identifier id = Registries.BLOCK.getId(state.getBlock());
        if (!SecurityManager.isSecurity(id) && state.getBlock() instanceof Reinforced reinforced) {
            BlockState newState = ReinforcedManager.fromReinforced(reinforced).getDefaultState();
            for (Property property : state.getProperties()) {
                newState.with(property,state.get(property));
            }
            cir.setReturnValue(getModel(newState));
        }
    }
}
