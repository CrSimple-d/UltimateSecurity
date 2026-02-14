package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeItem;
import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.crsimple.usecurity.api.passcode.util.Passcode;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SetItemPasscodeC2SPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("set_item_passcode");
    public static final PacketType<SetItemPasscodeC2SPacket> TYPE = PacketType.create(PACKET_ID, SetItemPasscodeC2SPacket::new);
    public final ItemStack stack;
    public final byte[] code;
    public int slot;

    public SetItemPasscodeC2SPacket(ItemStack stack, byte[] code) {
        this.stack = stack;
        this.code = code;
        this.slot = -1;
    }
    public SetItemPasscodeC2SPacket(int slot, byte[] code) {
        this.slot = slot;
        this.stack = null;
        this.code = code;
    }
    public SetItemPasscodeC2SPacket(PacketByteBuf buf) {
        if (buf.readInt() == -1) {
            this.stack = buf.readItemStack();
        } else {
            this.stack = null;
        }
        this.code = buf.readByteArray();
    }

    @Override
    public void write(PacketByteBuf buf) {
        if (slot == -1) {
            buf.writeItemStack(stack);
        } else {
            buf.writeInt(slot);
        }
        buf.writeBytes(code);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ServerPlayerEntity player, PacketSender packetSender) {
        ItemStack stack = this.stack == null ? player.getInventory().getStack(slot) : this.stack;
        if(code.length > 0 && stack.getItem() instanceof PasscodeItem passcodeItem) {
            passcodeItem.setPasscode(stack,new Passcode(this.code));
        }
    }
}
