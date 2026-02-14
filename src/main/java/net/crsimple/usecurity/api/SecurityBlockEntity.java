package net.crsimple.usecurity.api;

import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class SecurityBlockEntity extends BlockEntity implements OwnerProvider {
    public static final String SECURITY_KEY = "security";
    protected UUID owner;
    protected String ownerName;

    public SecurityBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public String ownerName() {
        return ownerName;
    }

    @Override
    public UUID ownerId() {
        return owner;
    }

    @Override
    public void changeOwner(UUID id,String name) {
        owner = id;
        ownerName = name;
        markDirty();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (!nbt.contains(SECURITY_KEY)) return;
        NbtCompound sec = nbt.getCompound(SECURITY_KEY);
        if (sec.contains(OWNER_UUID_KEY)) {
            this.owner = sec.getUuid(OWNER_UUID_KEY);
        }
        if (sec.contains(OWNER_NAME_KEY)) {
            this.ownerName = sec.getString(OWNER_NAME_KEY);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put(SECURITY_KEY,this.saveOwner(new NbtCompound()));
    }
}
