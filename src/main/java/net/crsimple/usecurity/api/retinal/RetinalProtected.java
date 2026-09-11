package net.crsimple.usecurity.api.retinal;

import net.crsimple.usecurity.api.Protected;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.api.passcode.Hackable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public interface RetinalProtected extends Protected, OwnerProvider, Hackable {
    @Override
    default ActionResult handleClick(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        tryToHack(world, player, hand, hit);
        return ActionResult.PASS;
    }

    default void handleLook(PlayerEntity e) {
        if(SecurityManager.hasAccess(this,e)) {
            onSuccess(e.getWorld(),e,((BlockEntity)this).getPos());
        } else {
            onError(e.getWorld(),e,((BlockEntity)this).getPos());
        }
    }
}
