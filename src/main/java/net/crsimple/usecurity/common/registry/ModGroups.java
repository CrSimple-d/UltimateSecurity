package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModGroups {
    public static final RegistryKey<ItemGroup> TECH_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ModMain.ID,"tech"));
    public static final RegistryKey<ItemGroup> EXPLOSIVE_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ModMain.ID,"explosive"));
    public static final RegistryKey<ItemGroup> DECO_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ModMain.ID,"deco"));

    public static final ItemGroup TECH = reg(FabricItemGroup.builder()
            .icon(() -> ModBlocks.KEYPAD.asItem().getDefaultStack())
            .displayName(Text.translatable("itemGroup.usecurity.tech"))
            .build(), TECH_KEY);
    public static final ItemGroup EXPLOSIVE = reg(FabricItemGroup.builder()
            .icon(() -> ModBlocks.MINE.asItem().getDefaultStack())
            .displayName(Text.translatable("itemGroup.usecurity.explosive"))
            .build(), EXPLOSIVE_KEY);
    public static final ItemGroup DECO = reg(FabricItemGroup.builder()
            .icon(() -> ModBlocks.FAKE_WATER.asItem().getDefaultStack())
            .displayName(Text.translatable("itemGroup.usecurity.deco"))
            .build(), DECO_KEY);


    private static ItemGroup reg(ItemGroup group,RegistryKey<ItemGroup> key) {
        return Registry.register(Registries.ITEM_GROUP,key,group);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(EXPLOSIVE_KEY).register(e -> e.add(Items.FLINT_AND_STEEL.getDefaultStack()));
    }
}
