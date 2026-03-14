package net.crsimple.usecurity.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;

public class Utils {
    public static String arrayToString(byte[] arr) {
        StringBuilder builder = new StringBuilder();
        for (var v : arr) {
            builder.append(v);
        }
        return builder.toString();
    }

    public static Text getName(BlockEntity be) {
        return be instanceof Nameable n ? n.getDisplayName() : Text.translatable(be.getCachedState().getBlock().getTranslationKey());
    }
}
