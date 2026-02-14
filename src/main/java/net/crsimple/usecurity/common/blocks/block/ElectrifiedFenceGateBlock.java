package net.crsimple.usecurity.common.blocks.block;

import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.crsimple.usecurity.api.reinforced.ReinforcedBlockEntity;
import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.api.reinforced.ReinforcedFenceGateBlock;
import net.crsimple.usecurity.common.registry.ModDamageTypes;
import net.crsimple.usecurity.common.registry.ModSounds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ElectrifiedFenceGateBlock extends ReinforcedFenceGateBlock {
    public ElectrifiedFenceGateBlock(Settings settings) {
        super(settings,WoodType.OAK);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world,pos,state,placer,itemStack);
        world.playSound(null,pos, ModSounds.ELECTRIFIED, SoundCategory.BLOCKS);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(OPEN) && entity instanceof LivingEntity living && living.canTakeDamage()) {
            living.damage(ModDamageTypes.of(world,ModDamageTypes.ELECTRICITY),3f);
            //world.playSound(null,pos, ModSounds.ELECTRIFIED, SoundCategory.BLOCKS);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ActionResult.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
