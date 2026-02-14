package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.owner.BlockWithOwner;
import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ReinforcedBlock extends BlockWithOwner implements Reinforced {
    public ReinforcedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable SecurityBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReinforcedBlockEntity(pos,state);
    }
}
