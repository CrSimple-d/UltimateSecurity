package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.common.screen.container.KeycardReaderMenu;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class UpdateKeycardC2SPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("update_keycard");
    public static final PacketType<UpdateKeycardC2SPacket> TYPE = PacketType.create(PACKET_ID, UpdateKeycardC2SPacket::new);
    public final ItemStack stack;
    public final BlockPos pos;

    public UpdateKeycardC2SPacket(ItemStack stack, BlockPos pos) {
        this.stack = stack;
        this.pos = pos;
    }
    public UpdateKeycardC2SPacket(PacketByteBuf buf) {
        this.stack = buf.readItemStack();
        this.pos = buf.readBlockPos();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeItemStack(stack);
        buf.writeBlockPos(pos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ServerPlayerEntity p, PacketSender packetSender) {
        if (p.currentScreenHandler instanceof KeycardReaderMenu menu) {
            menu.link(stack);
        }
    }
}
