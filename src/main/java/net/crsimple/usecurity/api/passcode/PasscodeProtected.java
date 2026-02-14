package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.api.passcode.util.Passcode;
import net.crsimple.usecurity.networking.OpenScreenS2CPacket;
import net.crsimple.usecurity.util.PlayerUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PasscodeProtected extends OwnerProvider {
    String PASSCODE_KEY = ModMain.createKey("passcode");

    default void openPasscodeScreen(BlockPos pos, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity spe) {
            ServerPlayNetworking.send(spe,new OpenScreenS2CPacket(pos,OpenScreenS2CPacket.ScreenType.CHECK_PASSCODE));
        }
    }
    default void openSetPasscodeScreen(BlockPos pos, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity spe) {
            ServerPlayNetworking.send(spe,new OpenScreenS2CPacket(pos,OpenScreenS2CPacket.ScreenType.SET_PASSCODE));
        }
    }

    default ActionResult handleClick(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || hand == Hand.OFF_HAND) return ActionResult.PASS;
        if (!hasPasscode()) {
            if (SecurityManager.hasAccess(this,player)) {
                openSetPasscodeScreen(hit.getBlockPos(), player);
            } else {
                PlayerUtil.sendMessage(player, (BlockEntity)this, Text.translatable("message.usecurity.passcode.not_set").formatted(Formatting.RED));
            }
        } else {
            openPasscodeScreen(hit.getBlockPos(),player);
        }
        return ActionResult.PASS;
    }

    default boolean checkPasscode(Passcode other) {
        return other != null && hasPasscode() && getPasscode().equals(other);
    }

    Passcode getPasscode();
    void setPasscode(Passcode passcode);

    void onSuccess(World world, PlayerEntity player);
    default void onIncorrect(World world, PlayerEntity player) {
    }

    default void saveCode(NbtCompound nbt) {
        if (hasPasscode()) {
            nbt.put(PASSCODE_KEY,getPasscode().saveToNbt(new NbtCompound()));
        }
    }
    default Passcode deserializeCode(NbtCompound nbt) {
        return Passcode.fromNbt(nbt.getCompound(PASSCODE_KEY));
    }

    default boolean hasPasscode() {
        return getPasscode() != null && getPasscode().hasSignature();
    }
}
