package net.crsimple.usecurity.common.blocks.entity;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.common.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class MineBlockEntity extends SecurityBlockEntity {
    public MineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MINE, pos, state);
    }
}
