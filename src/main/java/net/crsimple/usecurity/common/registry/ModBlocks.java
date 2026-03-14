package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.ReinforcedManager;
import net.crsimple.usecurity.api.reinforced.*;
import net.crsimple.usecurity.common.blocks.block.KeycardReaderBlock;
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
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModBlocks {
    static final List<Block> REINFORCED = new ArrayList<>();

    public static final Block MINE = reg(Mine::new,AbstractBlock.Settings.create().strength(3.5f).mapColor(MapColor.BLACK).sounds(BlockSoundGroup.METAL),"mine", ModGroups.EXPLOSIVE_KEY);
    public static final Block FAKE_LAVA = reg((s) -> new FluidBlock(ModFluids.STILL_FAKE_LAVA,s), FabricBlockSettings.copy(Blocks.LAVA),"fake_lava", ModGroups.DECO_KEY);
    public static final Block FAKE_WATER = reg((s) -> new FluidBlock(ModFluids.STILL_FAKE_WATER,s), FabricBlockSettings.copy(Blocks.WATER),"fake_water", ModGroups.DECO_KEY);
    public static final Block DEBUG_REINFORCED_BLOCK = regReinforced(ReinforcedBlock::new,AbstractBlock.Settings.create().strength(3.5f),"debug", ItemGroups.OPERATOR);
    public static final Block KEYPAD = reg(KeypadBlock::new,FabricBlockSettings.copy(Blocks.IRON_BLOCK),"keypad", ModGroups.TECH_KEY);
    public static final Block KEYCARD_READER = reg(KeycardReaderBlock::new,FabricBlockSettings.copy(Blocks.IRON_BLOCK),"keycard_reader", ModGroups.TECH_KEY);
    public static final Block REINFORCED_DOOR_BLOCK = regReinforced((sett) -> new ReinforcedDoorBlock(sett,BlockSetType.IRON),FabricBlockSettings.copy(Blocks.IRON_DOOR),"reinforced_iron_door", ModGroups.TECH_KEY,ModGroups.DECO_KEY);
    public static final Block REINFORCED_FENCE_GATE = regReinforced(ElectrifiedFenceGateBlock::new,FabricBlockSettings.copy(Blocks.IRON_BARS),"electrified_fence_gate", ModGroups.TECH_KEY,ModGroups.DECO_KEY);
    public static final Block REINFORCED_IRON_BARS = regReinforced(ReinforcedPaneBlock::new,FabricBlockSettings.copy(Blocks.IRON_BARS),"reinforced_iron_bars", ModGroups.DECO_KEY);

    @SafeVarargs
    private static Block reg(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, String id, RegistryKey<ItemGroup>... groups) {
        return reg(factory, sett, id,true,groups);
    }
    @SafeVarargs
    private static Block regReinforced(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, String id, RegistryKey<ItemGroup>... groups) {
        Block block = reg(factory, sett, id, true, groups);
        REINFORCED.add(block);
        return block;
    }
    @SafeVarargs
    private static Block reg(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, String id, boolean shouldRegisterItem, RegistryKey<ItemGroup>... groups) {
        Block block = regRaw(factory,sett,ModMain.id(id));
        if (shouldRegisterItem) {
            ModItems.reg(s -> new BlockItem(block, s), new Item.Settings(), id, groups);
        }
        return block;
    }

    private static Block regRaw(Function<AbstractBlock.Settings,Block> factory, AbstractBlock.Settings sett, Identifier id) {
        RegistryKey<Block> key = RegistryKey.of(Registries.BLOCK.getKey(), id);
        return Registry.register(Registries.BLOCK, key, factory.apply(sett));
    }

    public static void init() {
        registerReinforcedBlocks();
    }

    private static void registerReinforcedBlocks() {
        for(Block block : Registries.BLOCK) {
            if (block.getClass() == Block.class) {
                reg(block,ReinforcedBlock::new);
            } else if (block instanceof PaneBlock) {
                reg(block,ReinforcedPaneBlock::new);
            } else if (block instanceof LeverBlock) {
                reg(block,ReinforcedLeverBlock::new);
            }
        }
        ReinforcedManager.setReinforcedBlocks(REINFORCED);
    }

    private static void reg(Block block,Function<AbstractBlock.Settings,Block> factory) {
        Block b = regRaw(factory,FabricBlockSettings.copy(block).strength(block.getHardness()*1.5f),Registries.BLOCK.getId(block).withPrefixedPath("reinforced_"));
        ModItems.regRaw(s -> new BlockItem(b, s), new Item.Settings(), Registries.BLOCK.getId(block).withPrefixedPath("reinforced_"), ModGroups.DECO_KEY);
        REINFORCED.add(b);
    }
}
