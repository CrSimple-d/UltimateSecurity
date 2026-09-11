package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.Clickable;
import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ClickableReinforcedBlock extends ReinforcedBlock {
    public ClickableReinforcedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && world.getBlockEntity(pos) instanceof Clickable clickable) {
            return clickable.handleClick(world,player,hand,hit);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean shouldInteract(SecurityBlockEntity be,PlayerEntity p) {
        return true;
    }
}
