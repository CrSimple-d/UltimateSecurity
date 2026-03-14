package net.crsimple.usecurity.client;

import net.crsimple.usecurity.api.ReinforcedManager;
import net.crsimple.usecurity.api.reinforced.ReinforcedWithRender;
import net.crsimple.usecurity.common.items.KeycardItem;
import net.crsimple.usecurity.common.registry.ModBlocks;
import net.crsimple.usecurity.common.registry.ModItems;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class ModRenderer {
    public static void init() {
        String[] split = KeycardItem.LEVEL_KEY.split(":");
        ModelPredicateProviderRegistry.register(ModItems.KEYCARD,Identifier.of(split[0],split[1]),
                (stack,world,entity,seed) -> stack.getOrCreateNbt().getInt(KeycardItem.LEVEL_KEY));
        split = KeycardItem.USES_KEY.split(":");
        ModelPredicateProviderRegistry.register(ModItems.KEYCARD,Identifier.of(split[0],split[1]),
                (stack,world,entity,seed) -> ModItems.KEYCARD.usesLeft(stack) == -1 ? -1 : 0);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REINFORCED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REINFORCED_DOOR_BLOCK, RenderLayer.getCutout());
        for(Block b : ReinforcedManager.getReinforcedBlocks()) {
            if (b instanceof ReinforcedWithRender render) {
                BlockRenderLayerMap.INSTANCE.putBlock(b, render.getRenderLayer());
            }
        }
    }
}
