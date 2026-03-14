package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.api.passcode.PasscodeItem;
import net.crsimple.usecurity.networking.OpenScreenS2CPacket;
import net.crsimple.usecurity.util.ScreenHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class BriefcaseItem extends PasscodeItem {
    public BriefcaseItem(Settings settings) {
        super(settings);
    }

    @Override
    public void openPasscodeScreen(ServerPlayerEntity player, ItemStack stack) {
        ScreenHelper.openScreen(player, OpenScreenS2CPacket.ScreenType.CHECK_PASSCODE_BRIEFCASE);
    }

    @Override
    public void openSetPasscodeScreen(ServerPlayerEntity player, ItemStack stack) {
        ScreenHelper.openScreen(player, OpenScreenS2CPacket.ScreenType.SET_PASSCODE_BRIEFCASE);
    }

    @Override
    public void onSuccess(ServerPlayerEntity player, ItemStack stack) {
        player.getWorld().playSound(null,player.getBlockPos(),SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER);
    }
}
