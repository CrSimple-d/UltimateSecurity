package net.crsimple.usecurity.client;

import net.crsimple.usecurity.ServerCore;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public abstract class ClientCore {
    public static FluidRenderHandler createFakeWaterRenderHandler(Sprite[] waterSprites, Sprite waterOverlay) {
        return new FluidRenderHandler() {
            Sprite[] waterSpritesFull = new Sprite[]{waterSprites[0], waterSprites[1], waterOverlay};
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return waterSpritesFull;
            }

            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return view != null && pos != null ? BiomeColors.getWaterColor(view, pos) : 4159204;
            }
        };
    }

    public static FluidRenderHandler createFakeLavaRenderHandler(Sprite[] lavaSprites) {
        return (view, pos, state) -> lavaSprites;
    }

    public static boolean isInFluid(LivingEntity entity, Class<? extends Fluid> clazz) {
        return BlockPos.stream(entity.getBoundingBox()).anyMatch(p -> clazz.isInstance(entity.getWorld().getFluidState(p).getFluid()));
    }

    public static boolean shouldBreak(ClientWorld world, BlockPos pos, ClientPlayerEntity player) {
        return ServerCore.shouldBreak(world,pos,player);
    }
}
