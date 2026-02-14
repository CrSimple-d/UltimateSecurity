package net.crsimple.usecurity.common.blocks.block;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.explosive.AbstractMine;
import net.crsimple.usecurity.common.blocks.entity.MineBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Mine extends AbstractMine implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 3, 11);

    public Mine(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED,false));
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(WATERLOGGED,ctx.getWorld().getFluidState(ctx.getBlockPos()).isIn(FluidTags.WATER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public @Nullable SecurityBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MineBlockEntity(pos,state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
