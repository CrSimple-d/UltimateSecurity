package net.crsimple.usecurity;

import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.crsimple.usecurity.api.SecurityManager.*;

public abstract class ServerCore {
    public static boolean shouldBreak(World world, BlockPos pos, PlayerEntity player) {
        return convertToOwnable(world, pos).map(value -> {
            if (((BlockEntity)value).getCachedState().getBlock() instanceof Reinforced reinforced) {
                return reinforced.shouldBreak(((SecurityBlockEntity) value)) || hasAccess(value, player);
            }
            return true;
        }).orElse(true);
    }
    public static boolean shouldInteract(World world, BlockPos pos, PlayerEntity player) {
        return convertToOwnable(world, pos).map(value -> {
            if (((BlockEntity)value).getCachedState().getBlock() instanceof Reinforced reinforced) {
                return reinforced.shouldInteract(((SecurityBlockEntity) value)) || hasAccess(value, player);
            }
            return true;
        }).orElse(true);
    }
}
