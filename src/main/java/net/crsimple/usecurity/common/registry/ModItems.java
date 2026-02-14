package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.common.items.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class ModItems {
    public static final Item WIRE_CUTTERS = reg(Item::new, new Item.Settings().maxDamage(64),"wire_cutters", ModGroups.TECH_KEY,ModGroups.EXPLOSIVE_KEY);
    public static final Item ADMIN_TOOL = reg(AdminTool::new, new Item.Settings().rarity(Rarity.EPIC).maxCount(1),"admin_tool", ModGroups.TECH_KEY, ItemGroups.OPERATOR);
    public static final Item HACKER_TOOL = reg(HackerTool::new, new Item.Settings().rarity(Rarity.EPIC).maxDamage(128),"codebreaker", ModGroups.TECH_KEY, ItemGroups.OPERATOR);
    public static final Item BRIEFCASE = reg(BriefcaseItem::new, new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1),"briefcase", ModGroups.TECH_KEY);
    public static final Item FAKE_LAVA_BUCKET = reg(FakeLavaBucket::new, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1),"fake_lava_bucket", ModGroups.TECH_KEY);
    public static final Item FAKE_WATER_BUCKET = reg(FakeWaterBucket::new, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1),"fake_water_bucket", ModGroups.TECH_KEY);

    @SafeVarargs
    public static Item reg(Function<Item.Settings,Item> factory, Item.Settings sett, String id, RegistryKey<ItemGroup>... groups) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, ModMain.id(id));
        Item item = factory.apply(sett);
        for (var group : groups) {
            ItemGroupEvents.modifyEntriesEvent(group).register(e -> e.add(item.getDefaultStack()));
        }
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void init() {
    }
}
