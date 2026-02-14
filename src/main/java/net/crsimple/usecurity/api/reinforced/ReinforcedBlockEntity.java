package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.common.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ReinforcedBlockEntity extends SecurityBlockEntity {
    public ReinforcedBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REINFORCED, pos, state);
    }
}
