package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class ReinforcedDoorBlock extends DoorBlock implements Reinforced {
    public ReinforcedDoorBlock(Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer instanceof PlayerEntity player && world.getBlockEntity(pos) instanceof OwnerProvider ownable) {
            this.handlePlace(ownable,player);
            super.onPlaced(world,pos,state,placer,itemStack);
            if (world.getBlockEntity(pos.up()) instanceof OwnerProvider ownableUp) {
                this.handlePlace(ownableUp, player);
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable SecurityBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReinforcedBlockEntity(pos,state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(world.isReceivingRedstonePower(pos)) {
            if (SecurityManager.isReinforced(sourceBlock)) {
                super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
            }
            return;
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }
}
