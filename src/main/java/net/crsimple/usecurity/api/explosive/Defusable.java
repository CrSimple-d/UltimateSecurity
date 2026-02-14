package net.crsimple.usecurity.api.explosive;

import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Defusable extends IExplosive {
    void defuse(World world, BlockState state, BlockPos pos);
    void activate(World world, BlockState state, BlockPos pos);
    boolean isActivate(World world, BlockState state, BlockPos pos);

    @Override
    default boolean shouldExplode(OwnerProvider owner, Entity entity, World world, BlockState state, BlockPos pos) {
        return IExplosive.super.shouldExplode(owner, entity, world, state, pos) && isActivate(world, state, pos);
    }

    @Override
    default boolean isDefusable() {
        return true;
    }
}
