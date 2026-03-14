package net.crsimple.usecurity.util;

import net.crsimple.usecurity.networking.OpenScreenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ScreenHelper {
    public static void openScreen(ServerPlayerEntity player, OpenScreenS2CPacket packet) {
        ServerPlayNetworking.send(player,packet);
    }
    public static void openScreen(PlayerEntity player, BlockPos pos, OpenScreenS2CPacket.ScreenType screenType) {
        if (player instanceof ServerPlayerEntity spe) {
            openScreen(spe,new OpenScreenS2CPacket(pos,screenType));
        }
    }
    public static void openScreen(PlayerEntity player, OpenScreenS2CPacket.ScreenType screenType) {
        if (player instanceof ServerPlayerEntity spe) {
            openScreen(spe,new OpenScreenS2CPacket(screenType));
        }
    }
}
