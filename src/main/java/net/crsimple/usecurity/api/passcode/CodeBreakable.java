package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.Clickable;
import net.crsimple.usecurity.common.items.HackerTool;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface CodeBreakable extends Clickable {
    default boolean isCodeBreakable() {
        return true;
    }
    default boolean shouldBreakCode(PlayerEntity p, ItemStack stack) {
        return isCodeBreakable();
    }

    @Override
    default ActionResult handleClick(World world, PlayerEntity p, Hand hand, BlockHitResult hit) {
        if (!world.isClient && isCodeBreakable()) {
            if (hand == Hand.MAIN_HAND && p.getStackInHand(hand).getItem() instanceof HackerTool hackerTool && shouldBreakCode(p,p.getStackInHand(hand))) {
                if(hackerTool.tryToHack(p.getStackInHand(hand),p,this)) {
                    this.onSuccess(world,p,hit.getBlockPos());
                } else {
                    this.onHackingError(world,p,p.getStackInHand(hand),hit.getBlockPos());
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    default void onHackingError(World world, PlayerEntity player, ItemStack stack, BlockPos pos) {
        this.onError(world,player,pos);
    }
}
