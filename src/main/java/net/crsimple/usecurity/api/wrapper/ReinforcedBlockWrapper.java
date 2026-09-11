package net.crsimple.usecurity.api.wrapper;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.crsimple.usecurity.api.reinforced.ReinforcedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ReinforcedBlockWrapper extends SecurityBlockWrapper implements Reinforced {
    protected ReinforcedBlockWrapper(Block block) {
        super(block);
        settings.strength(getHardness()*1.6f);
    }

    @Override
    public @Nullable SecurityBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReinforcedBlockEntity(pos,state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (sourceBlock instanceof Reinforced) {
            super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (SecurityManager.hasAccess(world.getBlockEntity(pos),player)) {
            return super.onUse(state, world, pos, player, hand, hit);
        }
        return ActionResult.PASS;
    }

    public static ReinforcedBlockWrapper create(Block block) {
        SecurityBlockWrapper.init(block);
        return new ReinforcedBlockWrapper(block);
    }
}
