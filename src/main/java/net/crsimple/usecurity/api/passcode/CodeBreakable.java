package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.common.items.HackerTool;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public interface CodeBreakable extends PasscodeProtected {
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
                    this.onSuccess(world,p);
                } else {
                    this.onHackingError(p,p.getStackInHand(hand));
                }
                return ActionResult.PASS;
            }
        }
        return PasscodeProtected.super.handleClick(world, p, hand, hit);
    }

    default void onHackingError(PlayerEntity p, ItemStack stack) {
    }
}
