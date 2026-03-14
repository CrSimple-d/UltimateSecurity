package net.crsimple.usecurity.api;

import net.crsimple.usecurity.api.reinforced.ClickableReinforcedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class ProtectedBlock<T extends SecurityBlockEntity> extends ClickableReinforcedBlock {
    public ProtectedBlock(Settings settings) {
        super(settings);
    }
    @Override public abstract @Nullable T createBlockEntity(BlockPos pos, BlockState state);
}
