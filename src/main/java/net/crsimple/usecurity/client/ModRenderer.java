package net.crsimple.usecurity.client;

import net.crsimple.usecurity.common.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModRenderer {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REINFORCED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REINFORCED_DOOR_BLOCK, RenderLayer.getCutout());
    }
}
