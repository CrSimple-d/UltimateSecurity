package net.crsimple.usecurity.api.retinal;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.util.PlayerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class RetinalBlockEntity extends SecurityBlockEntity implements RetinalProtected {
    public RetinalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onSuccess(World world, PlayerEntity player, BlockPos pos) {
        PlayerUtil.sendMessage(player,this, Text.translatable("message.usecurity.retinal:success",player.getName().getString()).formatted(Formatting.GREEN));
    }

    @Override
    public void onError(World world, PlayerEntity player, BlockPos pos) {
        PlayerUtil.sendMessage(player,this, Text.translatable("message.usecurity.retinal:error").formatted(Formatting.RED));
    }
}
