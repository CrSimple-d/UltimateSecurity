package net.crsimple.usecurity.common.blocks.block;

import net.crsimple.usecurity.api.keycard.KeycardBlock;
import net.crsimple.usecurity.api.keycard.KeycardBlockEntity;
import net.crsimple.usecurity.common.blocks.entity.KeycardReaderBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class KeycardReaderBlock extends KeycardBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public KeycardReaderBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING,Direction.NORTH).with(POWERED,false));
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING,ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,POWERED);
    }

    @Override
    public @Nullable KeycardBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new KeycardReaderBlockEntity(pos,state);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos,state.with(POWERED,false));
    }
}
