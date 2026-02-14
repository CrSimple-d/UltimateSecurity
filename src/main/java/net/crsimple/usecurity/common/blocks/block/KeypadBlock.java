package net.crsimple.usecurity.common.blocks.block;

import net.crsimple.usecurity.api.passcode.BlockEntityWithCode;
import net.crsimple.usecurity.api.passcode.PasscodeBlock;
import net.crsimple.usecurity.common.blocks.entity.KeypadBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class KeypadBlock extends PasscodeBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public KeypadBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING,Direction.NORTH).with(POWERED,false));
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING,ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,POWERED);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos,state.with(POWERED,false));
    }

    @Override
    public @Nullable BlockEntityWithCode createBlockEntity(BlockPos pos, BlockState state) {
        return new KeypadBlockEntity(pos,state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(POWERED)) {
            return super.onUse(state, world, pos, player, hand, hit);
        }
        return ActionResult.PASS;
    }
}
