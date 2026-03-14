package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.networking.SetItemPasscodeC2SPacket;
import net.crsimple.usecurity.util.Utils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class BriefcaseSetPasscodeScreen extends BriefcasePasscodeScreen {
    public BriefcaseSetPasscodeScreen(int slot, Text title) {
        super(slot, title);
    }

    @Override
    protected void confirmCode(byte[] code) {
        ClientPlayNetworking.send(new SetItemPasscodeC2SPacket(slot, Utils.arrayToString(code)));
        close();
    }
}
