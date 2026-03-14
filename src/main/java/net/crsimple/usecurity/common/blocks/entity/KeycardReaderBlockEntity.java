package net.crsimple.usecurity.common.blocks.entity;

import net.crsimple.usecurity.api.keycard.KeycardBlockEntity;
import net.crsimple.usecurity.common.blocks.block.KeycardReaderBlock;
import net.crsimple.usecurity.common.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeycardReaderBlockEntity extends KeycardBlockEntity {
    public KeycardReaderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KEYCARD_READER, pos, state);
    }

    @Override
    public void onSuccess(World world, PlayerEntity player, BlockPos pos) {
        world.setBlockState(pos,getCachedState().with(KeycardReaderBlock.POWERED,true));
        world.scheduleBlockTick(pos,getCachedState().getBlock(),40);
    }
}
