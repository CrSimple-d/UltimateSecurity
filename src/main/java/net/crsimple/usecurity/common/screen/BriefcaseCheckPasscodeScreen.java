package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.networking.CheckItemPasscodeC2SPacket;
import net.crsimple.usecurity.networking.SetItemPasscodeC2SPacket;
import net.crsimple.usecurity.util.Utils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class BriefcaseCheckPasscodeScreen extends BriefcasePasscodeScreen {
    public BriefcaseCheckPasscodeScreen(int slot, Text title) {
        super(slot, title);
    }

    @Override
    protected void confirmCode(byte[] code) {
        ClientPlayNetworking.send(new CheckItemPasscodeC2SPacket(slot, Utils.arrayToString(code)));
        close();
    }
}
