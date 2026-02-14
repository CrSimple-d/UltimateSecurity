package net.crsimple.usecurity.api.owner;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface OwnableBlock extends BlockEntityProvider {
    default void handlePlace(OwnerProvider o, PlayerEntity p) {
        if (!p.getWorld().isClient) {
            o.changeOwner(p.getUuid(), p.getName().getString());
        }
    }
    @Nullable SecurityBlockEntity createBlockEntity(BlockPos pos, BlockState state);
}
