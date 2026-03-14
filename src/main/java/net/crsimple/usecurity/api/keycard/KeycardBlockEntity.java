package net.crsimple.usecurity.api.keycard;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.ImplementedContainer;
import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.common.screen.container.KeycardReaderMenu;
import net.crsimple.usecurity.util.Utils;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.crsimple.usecurity.common.items.KeycardItem.LEVEL_KEY;

import net.crsimple.usecurity.api.SignatureImpl;

public abstract class KeycardBlockEntity extends SecurityBlockEntity implements KeycardProtected, ExtendedScreenHandlerFactory, ImplementedContainer {
    public static final String DATA_KEY = ModMain.createKey("keycard_block_data");
    protected DefaultedList<ItemStack> inv = DefaultedList.ofSize(26,ItemStack.EMPTY);
    protected int minLevel;
    protected LevelMode mode;
    protected SignatureImpl signature;

    public KeycardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void openKeycardScreen(BlockPos pos, PlayerEntity player) {
        player.openHandledScreen(this);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new KeycardReaderMenu(syncId,playerInventory,world,getPos());
    }

    @Override
    public Text getDisplayName() {
        return Utils.getName(this);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf buf) {
        buf.writeBlockPos(getPos());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.signature = deserializeSignature(nbt);
        this.minLevel = nbt.getInt(LEVEL_KEY);
        this.mode = LevelMode.values()[nbt.getInt(MODE_KEY)];
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put(DATA_KEY,saveAll(new NbtCompound()));
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inv;
    }

    @Override
    public @NotNull SignatureImpl getSignature() {
        return signature==null?SignatureImpl.EMPTY:signature;
    }

    @Override
    public void setSignature(SignatureImpl signature) {
        this.signature = signature;
    }

    @Override
    public int getMinLevel() {
        return minLevel;
    }

    @Override
    public void setMinLevel(int level) {
        this.minLevel = level;
    }

    @Override
    public @NotNull LevelMode getLevelMode() {
        return mode==null?LevelMode.GREATER_THAN:mode;
    }

    @Override
    public void setLevelMode(LevelMode mode) {
        this.mode = mode;
    }
}
