package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.SignatureImpl;
import net.crsimple.usecurity.api.SignatureProtected;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class KeycardItem extends Item {
    public static final String LEVEL_KEY = ModMain.createKey("level");
    public static final String PLAYERS_KEY = ModMain.createKey("access");
    public static final String USES_KEY = ModMain.createKey("uses_left");
    public static final String SIGNATURE_KEY = SignatureProtected.SIGNATURE_KEY;
    public static final int INFINITE = -1;

    public KeycardItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        MutableText uses = Text.literal((usesLeft(stack))+"");
        tooltip.add(uses.append(Text.literal(" uses left").formatted(hasUses(stack)?Formatting.YELLOW:Formatting.DARK_RED)));

        MutableText lvl = Text.literal("Level: ").formatted(Formatting.YELLOW);
        tooltip.add(lvl.append(Text.literal((getLevel(stack)+1)+"").formatted(Formatting.DARK_RED)));

        MutableText signature = Text.literal("Signature: ").formatted(Formatting.YELLOW);
        tooltip.add(!hasSignature(stack) ? signature.append(Text.literal("unknown").formatted(Formatting.DARK_GRAY))
                : signature.append(Text.literal("*".repeat(getSignature(stack).size())).formatted(Formatting.DARK_GRAY)));
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(getTranslationKey(),getLevel(stack)+1);
    }

    public boolean isKeycardValid(ItemStack stack) {
        return hasSignature(stack) && hasUses(stack);
    }
    public int getLevel(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(LEVEL_KEY);
    }
    public void setLevel(ItemStack stack, int lvl) {
        stack.getOrCreateNbt().putInt(LEVEL_KEY,lvl);
    }
    public int usesLeft(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(USES_KEY);
    }
    public void setUses(ItemStack stack, int i) {
        stack.getOrCreateNbt().putInt(USES_KEY,i);
        updateState(stack);
    }
    public void onUse(ItemStack stack) {
        setUses(stack,usesLeft(stack)-1);
    }
    public boolean hasUses(ItemStack stack) {
        return usesLeft(stack) == INFINITE || usesLeft(stack) > 0;
    }
    public void updateState(ItemStack stack) {
        if (!hasUses(stack)) {
        }
    }
    public Set<String> getValidPlayers(ItemStack stack) {
        return stack.getOrCreateNbt().getList(PLAYERS_KEY,NbtElement.STRING_TYPE).stream()
                .map(NbtElement::asString)
                .collect(Collectors.toSet());
    }
    public void setValidPlayers(ItemStack stack, Collection<String> collection) {
        NbtList list = new NbtList();
        Set.copyOf(collection).forEach(s -> list.add(NbtString.of(s)));
        stack.getOrCreateNbt().put(PLAYERS_KEY,list);
    }
    public SignatureImpl getSignature(ItemStack stack) {
        return SignatureImpl.fromNbt(stack.getOrCreateNbt().getCompound(SIGNATURE_KEY));
    }
    public void setSignature(ItemStack stack,SignatureImpl s) {
        stack.getOrCreateNbt().put(SIGNATURE_KEY,s.saveToNbt(new NbtCompound()));
    }
    public boolean hasSignature(ItemStack stack) {
        return getSignature(stack) != null && getSignature(stack).hasSignature();
    }
    public ItemStack createKeycard(int lvl) {
        return createKeycard(lvl,SignatureImpl.EMPTY);
    }
    public ItemStack createKeycard(int lvl,SignatureImpl signature) {
        return createKeycard(lvl,signature,new HashSet<>(),INFINITE);
    }
    public ItemStack createKeycard(int lvl,SignatureImpl signature,Collection<String> players, int uses) {
        ItemStack stack = super.getDefaultStack();
        this.setLevel(stack,lvl);
        this.setSignature(stack,signature);
        this.setValidPlayers(stack,players);
        this.setUses(stack,uses);
        return stack;
    }
    @Override
    public ItemStack getDefaultStack() {
        return createKeycard(0);
    }
}
