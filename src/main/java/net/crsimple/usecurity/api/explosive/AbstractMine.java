package net.crsimple.usecurity.api.explosive;

import net.crsimple.usecurity.api.owner.BlockWithOwner;
import net.crsimple.usecurity.api.owner.OwnerProvider;
import net.crsimple.usecurity.common.registry.ModItemTags;
import net.crsimple.usecurity.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMine extends BlockWithOwner implements Defusable {
    public static final BooleanProperty ENABLED = Properties.ENABLED;

    public AbstractMine(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ENABLED,true));
    }

    @Override
    public void defuse(World world, BlockState state, BlockPos pos) {
        if (state.get(ENABLED)) {
            world.setBlockState(pos, state.with(ENABLED, false));
        }
    }

    @Override
    public void activate(World world, BlockState state, BlockPos pos) {
        if (!state.get(ENABLED)) {
            world.setBlockState(pos, state.with(ENABLED, true));
        }
    }

    @Override
    public boolean isActivate(World world, BlockState state, BlockPos pos) {
        return state.get(ENABLED);
    }

    @Override
    public void explode(World world, BlockState state, BlockPos pos) {
        world.createExplosion(null,pos.getX(),pos.getY(),pos.getZ(),force(),true, World.ExplosionSourceType.BLOCK);
    }

    public float force() {
        return 12f;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity p, Hand hand, BlockHitResult hit) {
        if (world.isClient || !(world.getBlockEntity(pos) instanceof OwnerProvider o)) return ActionResult.PASS;

        if (p.getStackInHand(hand).isOf(Items.FLINT_AND_STEEL) && !state.get(ENABLED)) {
            PlayerUtil.damageUnlessCreative(p,p.getStackInHand(hand));
            this.activate(world, state, pos);
            world.playSound(null,pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS,1f,1f);
        }

        else if (p.getStackInHand(hand).isIn(ModItemTags.WIRE_CUTTERS) && state.get(ENABLED) && isDefusable()) {
            PlayerUtil.damageUnlessCreative(p,p.getStackInHand(hand));
            this.defuse(world, state, pos);
            world.playSound(null,pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS,1f,1f);
        }

        else if (!p.getStackInHand(hand).isIn(ModItemTags.IGNORABLE) && shouldExplode(o,p,world,state,pos)) {
            this.explode(world, state, pos);
        }

        return super.onUse(state, world, pos, p, hand, hit);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || !(world.getBlockEntity(pos) instanceof OwnerProvider o)) return;
        if (shouldExplode(o,entity,world,state,pos)) {
            this.explode(world, state, pos);
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (world.isClient || !(blockEntity instanceof OwnerProvider o)) return;
        if (!player.isCreative() && shouldExplode(o,player,world,state,pos)) {
            this.explode(world, state, pos);
            return;
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ENABLED);
    }
}
