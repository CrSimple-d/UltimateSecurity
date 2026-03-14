package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public sealed interface ModTags permits ModTags.ItemTags,ModTags.BlockTags {

    static void init() {
        ItemTags.init();
        BlockTags.init();
    }

    final class ItemTags implements ModTags {
        public static TagKey<Item> WIRE_CUTTERS = reg("wire_cutters");
        public static TagKey<Item> IGNORABLE = reg("ignore");

        private static TagKey<Item> reg(String id) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ModMain.ID,id));
        }

        public static void init() {
        }
    }

    final class BlockTags implements ModTags {
        public static TagKey<Block> REINFORCED = reg("reinforced");

        private static TagKey<Block> reg(String id) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ModMain.ID,id));
        }

        public static void init() {
        }
    }
}
