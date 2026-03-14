package net.crsimple.usecurity.api.keycard;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.SignatureProtected;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.api.passcode.CodeBreakable;
import net.crsimple.usecurity.common.registry.ModItems;
import net.crsimple.usecurity.util.PlayerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.crsimple.usecurity.common.items.KeycardItem.LEVEL_KEY;

import net.crsimple.usecurity.api.SignatureImpl;

public interface KeycardProtected extends OwnerProvider,SignatureProtected<SignatureImpl>, CodeBreakable {
    KeycardValidator VALIDATOR = KeycardValidator.ALL;
    String MODE_KEY = ModMain.createKey("mode");

    void openKeycardScreen(BlockPos pos, PlayerEntity player);

    default ActionResult handleClick(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (CodeBreakable.super.handleClick(world, player, hand, hit) == ActionResult.SUCCESS) {
            return ActionResult.PASS;
        }
        if (world.isClient || hand == Hand.OFF_HAND) return ActionResult.PASS;
        if (!hasSignature()) {
            if (SecurityManager.hasAccess(this,player)) {
                openKeycardScreen(hit.getBlockPos(), player);
            }
            return ActionResult.PASS;
        }
        ItemStack stack = player.getStackInHand(hand);
        if (ModItems.KEYCARD.isKeycardValid(stack)) {
            if (checkKeycard(player, stack)) {
                onSuccess(world, player, hit.getBlockPos());
            } else {
                onError(world, player, hit.getBlockPos());
            }
            ModItems.KEYCARD.onUse(stack);
        }
        return ActionResult.PASS;
    }

    private boolean checkKeycard(PlayerEntity player, ItemStack stack) {
        ValidationResult result = VALIDATOR.validate(this, player, stack);
        if (result != ValidationResult.SUCCESS) {
            PlayerUtil.sendMessage(player,stack.getItem(),result.getMessage());
        }
        return result.asBool();
    }

    default NbtCompound saveAll(NbtCompound nbt) {
        saveSignature(nbt);
        nbt.putInt(LEVEL_KEY,getMinLevel());
        nbt.putInt(MODE_KEY,getLevelMode().ordinal());
        return nbt;
    }

    @Override
    default void onError(World world, PlayerEntity player, BlockPos pos) {
        player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.NEUTRAL,1f,1f);
    }

    @Override SignatureImpl getSignature();
    @Override void setSignature(SignatureImpl signature);

    int getMinLevel();
    void setMinLevel(int level);
    LevelMode getLevelMode();
    void setLevelMode(LevelMode mode);
}
