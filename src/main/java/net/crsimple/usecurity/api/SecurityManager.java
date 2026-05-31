package net.crsimple.usecurity.api;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class SecurityManager {
    public static final String LAST_USED_TIME_KEY = ModMain.createKey("last_used_time");

    public static boolean isSecurity(Block b) {
        return Registries.BLOCK.getId(b).getNamespace().equals(ModMain.ID);
    }
    public static boolean isSecurity(Identifier id) {
        return id.getNamespace().equals(ModMain.ID);
    }

    public static boolean isReinforced(BlockState state) {
        return isReinforced(state.getBlock());
    }
    public static boolean isReinforced(Block b) {
        return ReinforcedManager.isReinforced(b);
    }
    public static boolean isReinforced(Identifier id) {
        return ReinforcedManager.isReinforced(id);
    }

    public static void setLastUsedTime(ItemStack stack, long time) {
        stack.getOrCreateNbt().putLong(LAST_USED_TIME_KEY,time);
    }
    public static void setLastUsedTime(ItemStack stack) {
        setLastUsedTime(stack,System.currentTimeMillis());
    }
    public static boolean wasRecentlyUsed(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains(LAST_USED_TIME_KEY)) {
            long lastUsedTime = stack.getNbt().getLong(LAST_USED_TIME_KEY);
            return lastUsedTime <= 0 && System.currentTimeMillis() - lastUsedTime < 3000L;
        }
        return false;
    }

    public static boolean hasAccess(OwnerProvider be, PlayerEntity p) {
        return be.hasOwner() && be.ownerId().equals(p.getUuid()) && !isIncognito(p);
    }
    public static boolean hasAccess(OwnerProvider be, Entity e) {
        return e instanceof PlayerEntity p && hasAccess(be,p);
    }
    public static boolean hasAccess(BlockEntity be, Entity e) {
        return be instanceof OwnerProvider o && hasAccess(o,e);
    }

    public static boolean isIncognito(LivingEntity e) {
        return e.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.INCOGNITO_MASK;
    }
    public static boolean isAdminToolAtPrimarySlot(PlayerEntity p) {
        return p.getMainHandStack().getItem() == ModItems.ADMIN_TOOL;
    }
    public static Optional<OwnerProvider> convertToOwnable(World w, BlockPos pos) {
        return convertToOwnable(w.getBlockEntity(pos));
    }
    public static Optional<OwnerProvider> convertToOwnable(BlockEntity be) {
        OwnerProvider res = null;
        if (be instanceof OwnerProvider o) {
            res = o;
        }
        return Optional.ofNullable(res);
    }
}
