package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.networking.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModNetworking {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(CheckPasscodeC2SPacket.TYPE,CheckPasscodeC2SPacket::handlePacket);
        ServerPlayNetworking.registerGlobalReceiver(CheckItemPasscodeC2SPacket.TYPE, CheckItemPasscodeC2SPacket::handlePacket);
        ServerPlayNetworking.registerGlobalReceiver(SetPasscodeC2SPacket.TYPE,SetPasscodeC2SPacket::handlePacket);
        ServerPlayNetworking.registerGlobalReceiver(SetItemPasscodeC2SPacket.TYPE,SetItemPasscodeC2SPacket::handlePacket);
        ServerPlayNetworking.registerGlobalReceiver(UpdateKeycardC2SPacket.TYPE, UpdateKeycardC2SPacket::handlePacket);
        ServerPlayNetworking.registerGlobalReceiver(UpdateKeycardReaderC2SPacket.TYPE, UpdateKeycardReaderC2SPacket::handlePacket);

        ClientPlayNetworking.registerGlobalReceiver(OpenScreenS2CPacket.TYPE,OpenScreenS2CPacket::handlePacket);
    }
}
