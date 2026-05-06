package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.SignatureProtected;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.networking.OpenScreenS2CPacket;
import net.crsimple.usecurity.util.PlayerUtil;
import net.crsimple.usecurity.util.ScreenHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PasscodeProtected extends OwnerProvider, SignatureProtected<Passcode>, CodeBreakable {
    default void openPasscodeScreen(BlockPos pos, PlayerEntity player) {
        ScreenHelper.openScreen(player,pos, OpenScreenS2CPacket.ScreenType.CHECK_PASSCODE);
    }
    default void openSetPasscodeScreen(BlockPos pos, PlayerEntity player) {
        ScreenHelper.openScreen(player,pos, OpenScreenS2CPacket.ScreenType.SET_PASSCODE);
    }

    @Override
    default ActionResult handleClick(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (tryToHack(world, player, hand, hit)) {
            return ActionResult.PASS;
        }
        if (world.isClient || hand == Hand.OFF_HAND) return ActionResult.PASS;
        if (!hasSignature()) {
            if (SecurityManager.hasAccess(this,player)) {
                openSetPasscodeScreen(hit.getBlockPos(), player);
            } else {
                PlayerUtil.sendMessage(player, (BlockEntity)this, Text.translatable("message.usecurity.passcode.not_set").formatted(Formatting.RED));
            }
        } else {
            openPasscodeScreen(hit.getBlockPos(),player);
        }
        return ActionResult.SUCCESS;
    }

    @Override Passcode getSignature();
    @Override void setSignature(Passcode signature);

    @Override
    default void onError(World world, PlayerEntity player, BlockPos pos) {
        player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.NEUTRAL,1f,1f);
    }

    @Override
    default Passcode deserializeSignature(NbtCompound nbt) {
        return Passcode.fromNbt(nbt.getCompound(SIGNATURE_KEY));
    }
}
