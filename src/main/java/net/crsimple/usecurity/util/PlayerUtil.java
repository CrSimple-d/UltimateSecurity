package net.crsimple.usecurity.util;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import org.jetbrains.annotations.Nullable;

public class PlayerUtil {
    public static void decrementUnlessCreative(PlayerEntity p, ItemStack stack, int i) {
        if (!p.isCreative()) {
            stack.decrement(i);
        }
    }
    public static void decrementUnlessCreative(PlayerEntity p, ItemStack stack) {
        decrementUnlessCreative(p,stack,1);
    }
    public static void damageUnlessCreative(PlayerEntity p, ItemStack stack, int i) {
        if (!p.isCreative() && stack.isDamageable() && p instanceof ServerPlayerEntity sp) {
            stack.damage(i,sp,$p -> $p.sendToolBreakStatus(ItemStack.areEqual($p.getMainHandStack(),stack)?Hand.MAIN_HAND:Hand.OFF_HAND));
        }
    }
    public static void damageUnlessCreative(PlayerEntity p, ItemStack stack) {
        damageUnlessCreative(p,stack,1);
    }
    public static @Nullable ItemStack getStackFromAnyHand(PlayerEntity p, Item type) {
        Hand hand = getStackHand(p,type);
        return hand != null?p.getStackInHand(hand):null;
    }
    public static @Nullable Hand getStackHand(PlayerEntity p, Item type) {
        if (p.getMainHandStack().isOf(type)) {
            return Hand.MAIN_HAND;
        }
        return p.getOffHandStack().isOf(type)?Hand.OFF_HAND:null;
    }

    public static void sendMessage(PlayerEntity p, Text source, Text msg) {
        p.sendMessage(createMessage(source,msg),false);
    }
    public static void sendMessage(PlayerEntity p, BlockEntity source, Text msg) {
        sendMessage(p,source.getCachedState().getBlock(),msg);
    }
    public static void sendMessage(PlayerEntity p, Block source, Text msg) {
        sendMessage(p,Text.translatable(source.getTranslationKey()), msg);
    }
    public static void sendMessage(PlayerEntity p, Item source, Text msg) {
        sendMessage(p,Text.translatable(source.getTranslationKey()), msg);
    }

    public static Text createMessage(Text source, Text msg) {
        return Text.literal("[").formatted(Formatting.GRAY)
                .append(source)
                .append(Text.literal("] ").formatted(Formatting.GRAY))
                .append(msg);
    }
}
