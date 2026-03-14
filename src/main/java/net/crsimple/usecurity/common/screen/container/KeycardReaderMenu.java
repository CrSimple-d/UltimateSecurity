package net.crsimple.usecurity.common.screen.container;

import net.crsimple.usecurity.api.keycard.KeycardBlockEntity;
import net.crsimple.usecurity.common.items.KeycardItem;
import net.crsimple.usecurity.common.registry.ModScreens;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeycardReaderMenu extends ScreenHandler {
    private final SimpleInventory container = new SimpleInventory(1);
    private final ScreenHandlerContext context;
    public final Slot slot;
    public final PlayerInventory inv;
    public final KeycardBlockEntity be;

    public KeycardReaderMenu(int syncId,PlayerInventory inv,PacketByteBuf buf) {
        this(syncId,inv,inv.player.getWorld(),buf.readBlockPos());
    }

    public KeycardReaderMenu(int syncId, PlayerInventory inv, World world, BlockPos pos) {
        super(ModScreens.KEYCARD_READER, syncId);
        this.be = (KeycardBlockEntity) world.getBlockEntity(pos);
        this.context = ScreenHandlerContext.create(world,pos);
        this.inv = inv;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inv, 9 + j + i * 9, 8 + j * 18, 167 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inv, i, 8 + i * 18, 225));
        }
        this.slot = addSlot(new KeycardSlot(container,0,35,86));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int id) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = slots.get(id);

        if (slot.hasStack()) {
            ItemStack slotStack = slot.getStack();

            copy = slotStack.copy();

            if (id >= 36) {
                if (!insertItem(slotStack, 0, 36, true))
                    return ItemStack.EMPTY;
                slot.onQuickTransfer(slotStack, copy);
            }
            else if (!insertItem(slotStack, 36, 37, false))
                return ItemStack.EMPTY;

            if (slotStack.getCount() == 0)
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();

            if (slotStack.getCount() == copy.getCount())
                return ItemStack.EMPTY;

            slot.onTakeItem(player, slotStack);
        }
        return copy;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        container.clear();
        be.markDirty();
    }

    public void link(ItemStack stack) {
        slot.setStack(stack);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context,player,be.getCachedState().getBlock());
    }

    public static class KeycardSlot extends Slot {
        public KeycardSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof KeycardItem keycard && !keycard.isKeycardValid(stack);
        }
    }
}
