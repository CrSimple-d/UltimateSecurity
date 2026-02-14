package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.reinforced.ReinforcedBlock;
import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class PasscodeBlock extends ReinforcedBlock {
    public PasscodeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || !(world.getBlockEntity(pos) instanceof PasscodeProtected passcodeProtected)) return ActionResult.PASS;
        return passcodeProtected.handleClick(world, player, hand, hit);
    }

    @Override
    public boolean shouldInteract(SecurityBlockEntity be) {
        return true;
    }

    @Override
    public abstract @Nullable BlockEntityWithCode createBlockEntity(BlockPos pos, BlockState state);
}
