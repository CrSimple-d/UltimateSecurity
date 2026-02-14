package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static TagKey<Item> WIRE_CUTTERS = reg("wire_cutters");
    public static TagKey<Item> IGNORABLE = reg("ignore");

    private static TagKey<Item> reg(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ModMain.ID,id));
    }

    public static void init() {

    }
}
