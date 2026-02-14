package net.crsimple.usecurity.common.fluid;

import net.crsimple.usecurity.common.registry.ModBlocks;
import net.crsimple.usecurity.common.registry.ModFluids;
import net.crsimple.usecurity.common.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class FakeLava extends LavaFluid {
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_FAKE_LAVA;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.STILL_FAKE_LAVA;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.FAKE_LAVA_BUCKET;
    }

    @Override
    public BlockState toBlockState(FluidState state) {
        return ModBlocks.FAKE_LAVA.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }

    public static class Still extends FakeLava {

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
    public static class Flowing extends FakeLava {

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
