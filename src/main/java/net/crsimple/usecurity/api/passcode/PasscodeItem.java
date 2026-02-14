package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.passcode.util.Passcode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class PasscodeItem extends Item {
    public PasscodeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user instanceof ServerPlayerEntity spe && hand == Hand.MAIN_HAND) {
            ItemStack stack = user.getMainHandStack();
            if (!hasPasscode(stack)) {
                openSetPasscodeScreen(spe,stack);
            } else {
                openPasscodeScreen(spe,stack);
            }
        }
        return super.use(world, user, hand);
    }

    public abstract void openPasscodeScreen(ServerPlayerEntity player, ItemStack stack);
    public abstract void openSetPasscodeScreen(ServerPlayerEntity player,ItemStack stack);

    public abstract void onSuccess(ServerPlayerEntity player, ItemStack stack);
    public void onIncorrect(ServerPlayerEntity player, ItemStack stack) {
    }

    public boolean checkPasscode(ItemStack stack, Passcode other) {
        return hasPasscode(stack) && getPasscode(stack).equals(other);
    }
    public boolean hasPasscode(ItemStack stack) {
        return getPasscode(stack) != null && getPasscode(stack).hasSignature();
    }
    public Passcode getPasscode(ItemStack stack) {
        return Passcode.fromNbt(stack.getOrCreateNbt().getCompound(PasscodeProtected.PASSCODE_KEY));
    }
    public void setPasscode(ItemStack stack,Passcode passcode) {
        stack.getOrCreateNbt().put(PasscodeProtected.PASSCODE_KEY,passcode.saveToNbt(new NbtCompound()));
    }
}
