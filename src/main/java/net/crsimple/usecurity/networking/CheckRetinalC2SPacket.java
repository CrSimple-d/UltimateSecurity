package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.Passcode;
import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.crsimple.usecurity.api.retinal.RetinalProtected;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CheckRetinalC2SPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("check_retinal");
    public static final PacketType<CheckRetinalC2SPacket> TYPE = PacketType.create(PACKET_ID, CheckRetinalC2SPacket::new);
    public final BlockPos pos;

    public CheckRetinalC2SPacket(BlockPos pos) {
        this.pos = pos;
    }
    public CheckRetinalC2SPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ServerPlayerEntity player, PacketSender packetSender) {
        if(player.getWorld().getBlockEntity(pos) instanceof RetinalProtected retinalProtected) {
            retinalProtected.handleLook(player);
        }
    }
}
