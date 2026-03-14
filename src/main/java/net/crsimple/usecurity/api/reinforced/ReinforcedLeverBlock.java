package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ReinforcedLeverBlock extends LeverBlock implements Reinforced {
    public ReinforcedLeverBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer instanceof PlayerEntity player && world.getBlockEntity(pos) instanceof OwnerProvider ownable) {
            this.handlePlace(ownable,player);
            super.onPlaced(world,pos,state,placer,itemStack);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (SecurityManager.hasAccess(world.getBlockEntity(pos),player)) {
            super.onUse(state, world, pos, player, hand, hit);
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable SecurityBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReinforcedBlockEntity(pos,state);
    }
}
