package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.SignatureImpl;
import net.crsimple.usecurity.api.keycard.KeycardBlockEntity;
import net.crsimple.usecurity.api.keycard.LevelMode;
import net.crsimple.usecurity.common.screen.container.KeycardReaderMenu;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class UpdateKeycardReaderC2SPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("update_keycard");
    public static final PacketType<UpdateKeycardReaderC2SPacket> TYPE = PacketType.create(PACKET_ID, UpdateKeycardReaderC2SPacket::new);
    public final BlockPos pos;
    public final SignatureImpl signature;
    public final LevelMode mode;
    public final int minLevel;

    public UpdateKeycardReaderC2SPacket(BlockPos pos, SignatureImpl signature, LevelMode mode, int minLevel) {
        this.pos = pos;
        this.signature = signature;
        this.mode = mode;
        this.minLevel = minLevel;
    }
    public UpdateKeycardReaderC2SPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.signature = SignatureImpl.fromNbt(buf.readNbt());
        this.mode = buf.readEnumConstant(LevelMode.class);
        this.minLevel = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(signature.saveToNbt(new NbtCompound()));
        buf.writeEnumConstant(mode);
        buf.writeInt(minLevel);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ServerPlayerEntity p, PacketSender packetSender) {
        BlockEntity be = p.getWorld().getBlockEntity(pos);
        if (be instanceof KeycardBlockEntity kbe) {
            kbe.setSignature(signature);
            kbe.setLevelMode(mode);
            kbe.setMinLevel(minLevel);
        }
    }
}
