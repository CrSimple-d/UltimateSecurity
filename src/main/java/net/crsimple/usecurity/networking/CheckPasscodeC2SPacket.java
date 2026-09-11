package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.crsimple.usecurity.api.Passcode;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CheckPasscodeC2SPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("check_passcode");
    public static final PacketType<CheckPasscodeC2SPacket> TYPE = PacketType.create(PACKET_ID,CheckPasscodeC2SPacket::new);
    public final BlockPos pos;
    public final String code;

    public CheckPasscodeC2SPacket(BlockPos pos,String code) {
        this.pos = pos;
        this.code = code;
    }
    public CheckPasscodeC2SPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.code = buf.readString();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeString(code);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ServerPlayerEntity player, PacketSender packetSender) {
        BlockEntity be = player.getWorld().getBlockEntity(pos);
        if(!(be instanceof PasscodeProtected passcodeProtected)) return;
        byte[] passcode = code.getBytes();
        if(passcodeProtected.checkSignature(passcode)) {
            passcodeProtected.onSuccess(player.getWorld(),player,pos);
        } else {
            passcodeProtected.onError(player.getWorld(),player,pos);
        }
    }
}
