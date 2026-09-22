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
        ModelPredicateProviderRegistry.register(ModItems.KEYCARD,Identifier.tryParse(KeycardItem.LEVEL_KEY),
                (stack,world,entity,seed) -> ((float)stack.getOrCreateNbt().getInt(KeycardItem.LEVEL_KEY))/10);

        ModelPredicateProviderRegistry.register(ModItems.KEYCARD,Identifier.tryParse(KeycardItem.USES_KEY),
                (stack,world,entity,seed) -> ModItems.KEYCARD.usesLeft(stack) == -1 ? 1 : 0);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REINFORCED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REINFORCED_DOOR_BLOCK, RenderLayer.getCutout());

        for(Block b : ReinforcedManager.getReinforcedBlocks()) {
            if (b instanceof ReinforcedWithRender render) {
                BlockRenderLayerMap.INSTANCE.putBlock(b, render.getRenderLayer());
            }
        }
    }
}
