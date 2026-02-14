package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeItem;
import net.crsimple.usecurity.networking.OpenScreenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BriefcaseItem extends PasscodeItem {
    public BriefcaseItem(Settings settings) {
        super(settings);
    }

    @Override
    public void openPasscodeScreen(ServerPlayerEntity player, ItemStack stack) {
        ServerPlayNetworking.send(player,new OpenScreenS2CPacket(player, OpenScreenS2CPacket.ScreenType.CHECK_PASSCODE_BRIEFCASE));
    }

    @Override
    public void openSetPasscodeScreen(ServerPlayerEntity player, ItemStack stack) {
        ServerPlayNetworking.send(player,new OpenScreenS2CPacket(player, OpenScreenS2CPacket.ScreenType.CHECK_PASSCODE_BRIEFCASE));
    }

    @Override
    public void onSuccess(ServerPlayerEntity player, ItemStack stack) {
        ModMain.LOGGER.info("success");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(hasPasscode(stack)) {
            tooltip.add(Text.literal("Debug Code: " + getPasscode(stack).toString()));
        } else {
            tooltip.add(Text.literal("Debug Code: empty"));
        }
    }
}
