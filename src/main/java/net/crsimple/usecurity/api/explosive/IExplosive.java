package net.crsimple.usecurity.api.explosive;

import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IExplosive {
    void explode(World world, BlockState state, BlockPos pos);

    default boolean shouldExplode(OwnerProvider owner, Entity entity, World world, BlockState state, BlockPos pos) {
        return !SecurityManager.hasAccess(owner,entity);
    }

    default boolean isDefusable() {
        return this instanceof Defusable;
    }
}
