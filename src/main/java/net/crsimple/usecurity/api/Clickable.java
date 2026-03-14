package net.crsimple.usecurity.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Clickable {
    ActionResult handleClick(World world, PlayerEntity player, Hand hand, BlockHitResult hit);
    void onSuccess(World world, PlayerEntity player, BlockPos pos);
    void onError(World world, PlayerEntity player, BlockPos pos);
}
