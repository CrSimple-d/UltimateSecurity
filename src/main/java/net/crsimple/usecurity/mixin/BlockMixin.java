package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.api.ReinforcedManager;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.minecraft.block.Block;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "getTranslationKey",at = @At("HEAD"), cancellable = true)
    private void getTranslationKey(CallbackInfoReturnable<String> cir) {
        if (!SecurityManager.isSecurity((Block)(Object)this) && this instanceof Reinforced reinforced) {
            Block block = ReinforcedManager.fromReinforced(reinforced);
            cir.setReturnValue(block.getTranslationKey());
        }
    }
}
