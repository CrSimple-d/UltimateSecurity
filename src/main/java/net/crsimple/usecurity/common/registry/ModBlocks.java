package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.reinforced.ReinforcedBlock;
import net.crsimple.usecurity.api.reinforced.ReinforcedDoorBlock;
import net.crsimple.usecurity.api.reinforced.ReinforcedPaneBlock;
import net.crsimple.usecurity.common.blocks.block.KeypadBlock;
import net.crsimple.usecurity.common.blocks.block.Mine;
import net.crsimple.usecurity.common.blocks.block.ElectrifiedFenceGateBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public class ModBlocks {
    public static final Block MINE = reg(Mine::new,AbstractBlock.Settings.create().strength(3.5f).mapColor(MapColor.BLACK).sounds(BlockSoundGroup.METAL),"mine", ModGroups.EXPLOSIVE_KEY);
    public static final Block FAKE_LAVA = reg((s) -> new FluidBlock(ModFluids.STILL_FAKE_LAVA,s), FabricBlockSettings.copy(Blocks.LAVA),"fake_lava", ModGroups.DECO_KEY);
    public static final Block FAKE_WATER = reg((s) -> new FluidBlock(ModFluids.STILL_FAKE_WATER,s), FabricBlockSettings.copy(Blocks.WATER),"fake_water", ModGroups.DECO_KEY);
    public static final Block DEBUG_REINFORCED_BLOCK = reg(ReinforcedBlock::new,AbstractBlock.Settings.create().strength(3.5f),"debug", ItemGroups.OPERATOR);
    public static final Block KEYPAD = reg(KeypadBlock::new,FabricBlockSettings.copy(Blocks.IRON_BLOCK),"keypad", ModGroups.TECH_KEY);
    public static final Block REINFORCED_DOOR_BLOCK = reg((sett) -> new ReinforcedDoorBlock(sett,BlockSetType.IRON),FabricBlockSettings.copy(Blocks.IRON_DOOR),"reinforced_iron_door", ModGroups.TECH_KEY,ModGroups.DECO_KEY);
    public static final Block REINFORCED_FENCE_GATE = reg(ElectrifiedFenceGateBlock::new,FabricBlockSettings.copy(Blocks.IRON_BARS),"electrified_fence_gate", ModGroups.TECH_KEY,ModGroups.DECO_KEY);
    public static final Block REINFORCED_IRON_BARS = reg(ReinforcedPaneBlock::new,FabricBlockSettings.copy(Blocks.IRON_BARS),"reinforced_iron_bars", ModGroups.DECO_KEY);
    public static final Block REINFORCED_IRON_BLOCK = reg(ReinforcedBlock::new,FabricBlockSettings.copy(Blocks.IRON_BLOCK),"reinforced_iron_block", ModGroups.DECO_KEY);

    @SafeVarargs
    private static Block reg(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, String id, RegistryKey<ItemGroup>... groups) {
        return reg(factory, sett, id,true,groups);
    }
    private static Block reg(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, String id) {
        return reg(factory, sett, id,true);
    }
    @SafeVarargs
    private static Block reg(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, String id, boolean shouldRegisterItem, RegistryKey<ItemGroup>... groups) {
        RegistryKey<Block> key = RegistryKey.of(Registries.BLOCK.getKey(), ModMain.id(id));
        Block block = factory.apply(sett);
        if (shouldRegisterItem) {
            ModItems.reg(s -> new BlockItem(block, s), new Item.Settings(), id, groups);
        }
        return Registry.register(Registries.BLOCK,key, block);
    }

    public static void init() {
    }
}
