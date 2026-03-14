package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.api.ReinforcedManager;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BakedModelManager.class)
public abstract class BakedModelManagerMixin {
    @Shadow public abstract BakedModel getModel(ModelIdentifier id);

    @Inject(method = "getModel", at = @At("HEAD"),cancellable = true)
    private void getModel(ModelIdentifier id, CallbackInfoReturnable<BakedModel> cir) {
        if (!SecurityManager.isSecurity(id) && Registries.BLOCK.get(Identifier.of(id.getNamespace(),id.getPath())) instanceof Reinforced reinforced) {
            id = new ModelIdentifier(Registries.BLOCK.getId(ReinforcedManager.fromReinforced(reinforced)), id.getVariant());
            //ModMain.LOGGER.info("loaded reinforced resource: {}", id);
            cir.setReturnValue(getModel(id));
        }
    }
}
