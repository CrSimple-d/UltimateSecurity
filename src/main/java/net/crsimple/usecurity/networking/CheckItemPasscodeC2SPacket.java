package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeItem;
import net.crsimple.usecurity.api.Passcode;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CheckItemPasscodeC2SPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("check_item_passcode");
    public static final PacketType<CheckItemPasscodeC2SPacket> TYPE = PacketType.create(PACKET_ID, CheckItemPasscodeC2SPacket::new);
    public final ItemStack stack;
    public final String code;
    public int slot;

    public CheckItemPasscodeC2SPacket(ItemStack stack, String code) {
        this.stack = stack;
        this.code = code;
        this.slot = -1;
    }
    public CheckItemPasscodeC2SPacket(int slot, String code) {
        this.slot = slot;
        this.stack = null;
        this.code = code;
    }
    public CheckItemPasscodeC2SPacket(PacketByteBuf buf) {
        if (buf.readInt() == -1) {
            this.stack = buf.readItemStack();
        } else {
            this.stack = null;
        }
        this.code = buf.readString();
    }

    @Override
    public void write(PacketByteBuf buf) {
        if (slot == -1) {
            buf.writeItemStack(stack);
        } else {
            buf.writeInt(slot);
        }
        buf.writeString(code);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ServerPlayerEntity player, PacketSender packetSender) {
        ItemStack stack = this.stack == null ? player.getInventory().getStack(slot) : this.stack;
        if(!code.isEmpty() && stack.getItem() instanceof PasscodeItem passcodeItem) {
            if (passcodeItem.checkPasscode(stack,code.getBytes())) {
                passcodeItem.onSuccess(player,stack);
            } else {
                passcodeItem.onIncorrect(player,stack);
            }
        }
    }
}
