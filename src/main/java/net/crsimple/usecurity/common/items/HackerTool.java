package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.schedule.ItemScheduledStateManager;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.passcode.CodeBreakable;
import net.crsimple.usecurity.util.PlayerUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HackerTool extends Item {
    public static final int DEFAULT_STATE = 0, SUCCESS_STATE = 3, FAILURE_STATE = 2, DECODING_STATE = 1;
    public static final String SUCCESS_CHANCE_KEY = ModMain.createKey("chance");

    public HackerTool(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.usecurty.codebreaker.chance",(getChance(stack)*100)+"%").formatted(Formatting.YELLOW));
    }

    public boolean tryToHack(ItemStack stack, PlayerEntity p, CodeBreakable passcodeProtected) {
        if (p.getWorld().isClient) return false;
        if (!passcodeProtected.hasPasscode()) {
            PlayerUtil.sendMessage(p, (BlockEntity) passcodeProtected, Text.translatable("message.usecurity.passcode.not_set").formatted(Formatting.DARK_RED));
        } else {
            return hack(stack, p);
        }
        return false;
    }
    public boolean hack(ItemStack stack, PlayerEntity p) {
        if (!p.getWorld().isClient) {
            double chance = getChance(stack);
            if (chance <= 0d) {
                PlayerUtil.sendMessage(p,this,Text.translatable("message.usecurity.codebreaker.disabled").formatted(Formatting.DARK_RED));
            } else {
                if (p.isCreative() || !SecurityManager.wasRecentlyUsed(stack)) {
                    PlayerUtil.damageUnlessCreative(p, stack);
                    SecurityManager.setLastUsedTime(stack);
                    if (p.isCreative() || p.getRandom().nextDouble() < chance) {
                        ItemScheduledStateManager.builder(p.getWorld().getServer(),stack)
                                .schedule(DECODING_STATE, 1000)
                                .schedule(SUCCESS_STATE, 2000)
                                .schedule(DEFAULT_STATE, 0)
                                .build().start();
                        return true;
                    }
                }
            }
        }
        ItemScheduledStateManager.builder(p.getWorld().getServer(),stack)
                .schedule(DECODING_STATE,1000)
                .schedule(FAILURE_STATE,2000)
                .schedule(DEFAULT_STATE,0)
                .build().start();
        return false;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public double getChance(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if(nbt.contains(SUCCESS_CHANCE_KEY)) {
            return nbt.getDouble(SUCCESS_CHANCE_KEY);
        } else return 1d;
    }
}
