package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.networking.SetItemPasscodeC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class BriefcaseCheckPasscodeScreen extends BriefcasePasscodeScreen {
    public BriefcaseCheckPasscodeScreen(int slot, Text title) {
        super(slot, title);
    }

    @Override
    protected void confirmCode(byte[] code) {
        ClientPlayNetworking.send(new SetItemPasscodeC2SPacket(slot, code));
        close();
    }
}
