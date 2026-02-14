package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.util.PlayerUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

public class AdminTool extends Item {
    public AdminTool(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        if (!ctx.getWorld().isClient && ctx.getPlayer() != null && ctx.getWorld().getBlockEntity(ctx.getBlockPos()) instanceof OwnerProvider data) {
            PlayerUtil.sendMessage(ctx.getPlayer(), this, Text.literal("Owner name: " + (data.hasOwner()?data.ownerName():"null")).formatted(Formatting.YELLOW));
            PlayerUtil.sendMessage(ctx.getPlayer(),this, Text.literal("Owner id: " + (data.hasOwner()?data.ownerId():"null")).formatted(Formatting.YELLOW));
        }
        return super.useOnBlock(ctx);
    }
}
