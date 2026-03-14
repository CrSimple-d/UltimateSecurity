package net.crsimple.usecurity.common.blocks.entity;

import net.crsimple.usecurity.api.passcode.BlockEntityWithCode;
import net.crsimple.usecurity.common.blocks.block.KeypadBlock;
import net.crsimple.usecurity.common.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeypadBlockEntity extends BlockEntityWithCode {
    public KeypadBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KEYPAD, pos, state);
    }

    @Override
    public void onSuccess(World world, PlayerEntity player, BlockPos pos) {
        world.setBlockState(getPos(),getCachedState().with(KeypadBlock.POWERED,true));
        world.scheduleBlockTick(getPos(),getCachedState().getBlock(),40);
    }
}
