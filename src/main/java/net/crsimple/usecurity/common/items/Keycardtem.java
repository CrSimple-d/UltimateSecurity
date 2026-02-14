package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.ModMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Keycardtem extends Item {
    public static final String LEVEL_KEY = ModMain.createKey("level");

    public Keycardtem(Settings settings) {
        super(settings);
    }

    public int getLevel(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(LEVEL_KEY);
    }
}
