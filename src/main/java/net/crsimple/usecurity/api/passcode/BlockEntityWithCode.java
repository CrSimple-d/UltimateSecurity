package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class BlockEntityWithCode extends SecurityBlockEntity implements PasscodeProtected {
    protected Passcode code;

    public BlockEntityWithCode(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Passcode getSignature() {
        return code;
    }

    @Override
    public void setSignature(Passcode passcode) {
        this.code = passcode;
    }

    @Override
    public boolean shouldBreakCode(PlayerEntity p, ItemStack stack) {
        return true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.code = deserializeSignature(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.saveSignature(nbt);
    }
}
