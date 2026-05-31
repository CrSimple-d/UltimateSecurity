package net.crsimple.usecurity.client;

import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.retinal.RetinalProtected;
import net.crsimple.usecurity.networking.CheckRetinalC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;

public class ModClientTick {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ModClientTick::loop);
    }

    private static void loop(MinecraftClient c) {
        if(c.world != null) {
            if(c.crosshairTarget instanceof BlockHitResult bhr && SecurityManager.isIncognito(c.player)) {
               if (c.world.getBlockEntity(bhr.getBlockPos()) instanceof RetinalProtected) {
                   ClientPlayNetworking.send(new CheckRetinalC2SPacket(bhr.getBlockPos()));
               }
            }
        }
    }
}
