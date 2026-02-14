package net.crsimple.usecurity.common.fluid;

import net.crsimple.usecurity.common.registry.ModBlocks;
import net.crsimple.usecurity.common.registry.ModFluids;
import net.crsimple.usecurity.common.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class FakeWater extends WaterFluid {
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_FAKE_WATER;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.STILL_FAKE_WATER;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.FAKE_WATER_BUCKET;
    }

    @Override
    public BlockState toBlockState(FluidState state) {
        return ModBlocks.FAKE_WATER.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }

    public static class Still extends FakeWater {

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
    public static class Flowing extends FakeWater {

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }
}
