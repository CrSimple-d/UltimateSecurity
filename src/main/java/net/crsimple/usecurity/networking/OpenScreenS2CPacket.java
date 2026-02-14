package net.crsimple.usecurity.networking;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.crsimple.usecurity.common.registry.ModItems;
import net.crsimple.usecurity.common.screen.BriefcaseCheckPasscodeScreen;
import net.crsimple.usecurity.common.screen.BriefcaseSetPasscodeScreen;
import net.crsimple.usecurity.common.screen.CheckPasscodeScreen;
import net.crsimple.usecurity.common.screen.SetPasscodeScreen;
import net.crsimple.usecurity.util.PlayerUtil;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;

public class OpenScreenS2CPacket implements FabricPacket {
    public static final Identifier PACKET_ID = ModMain.id("open_screen");
    public static final PacketType<OpenScreenS2CPacket> TYPE = PacketType.create(PACKET_ID, OpenScreenS2CPacket::new);
    public final BlockPos pos;
    public final ScreenType type;

    public OpenScreenS2CPacket(BlockPos pos, ScreenType type) {
        this.pos = pos;
        this.type = type;
    }
    public OpenScreenS2CPacket(Entity entity,ScreenType type) {
        this.pos = new BlockPos(BlockPos.ZERO);
        this.type = type;
    }
    public OpenScreenS2CPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.type = buf.readEnumConstant(ScreenType.class);
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeEnumConstant(type);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handlePacket(ClientPlayerEntity p, PacketSender packetSender) {
        MinecraftClient client = MinecraftClient.getInstance();
        BlockEntity be = p.getWorld().getBlockEntity(pos);
        switch (type) {
            case CHECK_PASSCODE -> {
                if (be instanceof PasscodeProtected passcodeProtected) {
                    client.setScreen(new CheckPasscodeScreen(passcodeProtected,getName(be)));
                }
            }
            case SET_PASSCODE -> {
                if (be instanceof PasscodeProtected passcodeProtected) {
                    client.setScreen(new SetPasscodeScreen(passcodeProtected,getName(be)));
                }
            }
            case SET_PASSCODE_BRIEFCASE -> {
                Hand hand = PlayerUtil.getStackHand(p, ModItems.BRIEFCASE);
                if (hand != null) {
                    client.setScreen(new BriefcaseSetPasscodeScreen(hand==Hand.MAIN_HAND?0:1,Text.translatable(p.getStackInHand(hand).getItem().getTranslationKey())));
                }
            }
            case CHECK_PASSCODE_BRIEFCASE -> {
                Hand hand = PlayerUtil.getStackHand(p, ModItems.BRIEFCASE);
                if (hand != null) {
                    client.setScreen(new BriefcaseCheckPasscodeScreen(hand==Hand.MAIN_HAND?0:1,Text.translatable(p.getStackInHand(hand).getItem().getTranslationKey())));
                }
            }
        }
    }

    public static Text getName(BlockEntity be) {
        return be instanceof Nameable n ? n.getDisplayName() : Text.translatable(be.getCachedState().getBlock().getTranslationKey());
    }

    public enum ScreenType {
        CHECK_PASSCODE,
        SET_PASSCODE,
        SET_PASSCODE_BRIEFCASE,
        CHECK_PASSCODE_BRIEFCASE;
    }
}
