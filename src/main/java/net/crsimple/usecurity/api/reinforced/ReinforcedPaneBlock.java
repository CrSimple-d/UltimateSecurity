package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ReinforcedPaneBlock extends PaneBlock implements ReinforcedWithRender.Translucent {
    public ReinforcedPaneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer instanceof PlayerEntity player && world.getBlockEntity(pos) instanceof OwnerProvider ownable) {
            this.handlePlace(ownable,player);
        }
        super.onPlaced(world,pos,state,placer,itemStack);
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
