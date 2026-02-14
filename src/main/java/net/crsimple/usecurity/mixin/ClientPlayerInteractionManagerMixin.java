package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.client.ClientCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void cancelBlockBreaking();

    @Shadow private GameMode gameMode;

    @Inject(method = "updateBlockBreakingProgress",at = @At("HEAD"),cancellable = true)
    private void update(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!gameMode.isCreative() && !ClientCore.shouldBreak(client.world,pos,client.player)) {
            cancelBlockBreaking();
            cir.setReturnValue(false);
        }
    }
}
