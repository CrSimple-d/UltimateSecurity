package net.crsimple.usecurity.api.reflection;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ReflectionApi {
    private static ReflectionTable.Builder builder = new ReflectionTable.Builder();
    private static ReflectionHelper helper;

    public static void init() {
        register(b -> b.register(Block.class,"dropExperience",void.class, ServerWorld.class, BlockPos.class, int.class));
        register(b -> b.register(Block.class,"spawnBreakParticles",void.class, World.class, PlayerEntity.class, BlockPos.class, BlockState.class));
        register(b -> b.register(Block.class,"getShapesForStates", ImmutableMap.class, Function.class));
        register(b -> b.register(Block.class,"dropExperienceWhenMined",void.class,ServerWorld.class, BlockPos.class, ItemStack.class, IntProvider.class));
        register(b -> b.register(Block.class,"asBlock",Block.class));
        helper = ReflectionHelper.of(builder.build());
        builder = null;
    }

    public static void register(UnaryOperator<ReflectionTable.Builder> builder) {
        if (isInitialized()) {
            throw new RuntimeException("helper is already initialized");
        }
        builder.apply(ReflectionApi.builder);
    }

    public static ReflectionHelper getHelper() {
        if (!isInitialized()) {
            throw new RuntimeException("helper is not already initialized");
        }
        return helper;
    }

    public static boolean isInitialized() {
        return helper != null;
    }
}
