package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.keycard.KeycardBlockEntity;
import net.crsimple.usecurity.api.reinforced.ReinforcedBlockEntity;
import net.crsimple.usecurity.common.blocks.entity.KeycardReaderBlockEntity;
import net.crsimple.usecurity.common.blocks.entity.KeypadBlockEntity;
import net.crsimple.usecurity.common.blocks.entity.MineBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static final BlockEntityType<MineBlockEntity> MINE = register("mine", MineBlockEntity::new,ModBlocks.MINE);
    public static final BlockEntityType<KeypadBlockEntity> KEYPAD = register("keypad", KeypadBlockEntity::new,ModBlocks.KEYPAD);
    public static final BlockEntityType<KeycardReaderBlockEntity> KEYCARD_READER = register("keycard_reader", KeycardReaderBlockEntity::new,ModBlocks.KEYCARD_READER);

    public static final BlockEntityType<ReinforcedBlockEntity> REINFORCED = register("reinforced", ReinforcedBlockEntity::new,ModBlocks.REINFORCED.toArray(Block[]::new));

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... blocks) {
        if (blocks.length > 0) {
            return Registry.register(Registries.BLOCK_ENTITY_TYPE, ModMain.id(id), FabricBlockEntityTypeBuilder.<T>create(factory, blocks).build());
        } return null;
    }

    public static void init() {

    }
}
