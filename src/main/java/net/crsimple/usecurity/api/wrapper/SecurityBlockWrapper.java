package net.crsimple.usecurity.api.wrapper;

import com.google.common.collect.ImmutableMap;
import net.crsimple.usecurity.api.owner.BlockWithOwner;
import net.crsimple.usecurity.api.reflection.ReflectionApi;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public abstract class SecurityBlockWrapper extends BlockWithOwner {
    private static final ThreadLocal<Block> TEMP = new ThreadLocal<>();
    private static final WrapperInitializationException NOT_INITIALIZED = new WrapperInitializationException("not initialized or block is null");
    protected final Block block;

    @SuppressWarnings("unchecked")
    protected SecurityBlockWrapper(Block block) {
        super(Settings.copy(block));
        this.block = block;
        TEMP.remove();

        BlockState defaultState = getDefaultState();
        for (Property property : getStateManager().getProperties()) {
            defaultState = defaultState.with(property,block.getDefaultState().get(property));
        }
        setDefaultState(defaultState);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        block.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        Block b = TEMP.get();
        checkWrappedBlock(b);
        for(Property<?> property : b.getStateManager().getProperties()) {
            builder.add(property);
        }
    }

    private static void checkWrappedBlock(Block b) {
        if (b == null) {
            throw NOT_INITIALIZED;
        }
    }

    public Block getWrappedBlock() {
        return block;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return block.hasRandomTicks(state);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return block.isTransparent(state, world, pos);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        block.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        block.onBroken(world, pos, state);
    }

    @Override
    public void dropExperience(ServerWorld world, BlockPos pos, int size) {
        ReflectionApi.getHelper().invokeMethod(this,"dropExperience",world,pos,size);
    }

    @Override
    public float getBlastResistance() {
        return block.getBlastResistance();
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        block.onDestroyedByExplosion(world, pos, explosion);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        block.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        block.afterBreak(world, player, pos, state, blockEntity, tool);
    }

    @Override
    public boolean canMobSpawnInside(BlockState state) {
        return block.canMobSpawnInside(state);
    }

    @Override
    public MutableText getName() {
        return block.getName();
    }

    @Override
    public String getTranslationKey() {
        return block.getTranslationKey();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        block.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        block.onEntityLand(world, entity);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return block.getPickStack(world, pos, state);
    }

    @Override
    public float getSlipperiness() {
        return block.getSlipperiness();
    }

    @Override
    public float getVelocityMultiplier() {
        return block.getVelocityMultiplier();
    }

    @Override
    public float getJumpVelocityMultiplier() {
        return block.getJumpVelocityMultiplier();
    }

    @Override
    public void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        ReflectionApi.getHelper().invokeMethod(this,"spawnBreakParticles",world,player,pos,state);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        block.onBreak(world, pos, state, player);
    }

    @Override
    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        block.precipitationTick(state, world, pos, precipitation);
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return block.shouldDropItemsOnExplosion(explosion);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return block.getSoundGroup(state);
    }

    @Override
    public boolean hasDynamicBounds() {
        return block.hasDynamicBounds();
    }

    @Override
    public String toString() {
        return block.toString();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        block.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ImmutableMap<BlockState, VoxelShape> getShapesForStates(Function<BlockState, VoxelShape> stateToShape) {
        return (ImmutableMap<BlockState, VoxelShape>) ReflectionApi.getHelper().invokeMethod(this,"getShapesForStates",stateToShape);
    }

    @Override
    public void dropExperienceWhenMined(ServerWorld world, BlockPos pos, ItemStack tool, IntProvider experience) {
        ReflectionApi.getHelper().invokeMethod(this,"dropExperienceWhenMined",world,pos,tool,experience);
    }

    @Deprecated
    @Override
    public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
        block.prepare(state, world, pos, flags, maxUpdateDepth);
    }

    @Deprecated
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return block.canPathfindThrough(state, world, pos, type);
    }

    @Deprecated
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return block.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Deprecated
    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return block.isSideInvisible(state, stateFrom, direction);
    }

    @Deprecated
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        block.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Deprecated
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        block.onBlockAdded(state, world, pos, oldState, notify);
    }

    @Deprecated
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        block.onStateReplaced(state, world, pos, newState, moved);
    }

    @Deprecated
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return block.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    @Deprecated
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        return block.onSyncedBlockEvent(state, world, pos, type, data);
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
        return block.getRenderType(state);
    }

    @Deprecated
    @Override
    public boolean hasSidedTransparency(BlockState state) {
        Block b = TEMP.get();
        checkWrappedBlock(b);
        return b.hasSidedTransparency(state);
    }

    @Deprecated
    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return block.emitsRedstonePower(state);
    }

    @Deprecated
    @Override
    public FluidState getFluidState(BlockState state) {
        return block.getFluidState(state);
    }

    @Deprecated
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return block.hasComparatorOutput(state);
    }

    @Override
    public float getMaxHorizontalModelOffset() {
        return block.getMaxHorizontalModelOffset();
    }

    @Override
    public float getVerticalModelOffsetMultiplier() {
        return block.getVerticalModelOffsetMultiplier();
    }

    @Override
    public FeatureSet getRequiredFeatures() {
        return block.getRequiredFeatures();
    }

    @Deprecated
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return block.rotate(state, rotation);
    }

    @Deprecated
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return block.mirror(state, mirror);
    }

    @Deprecated
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return block.canReplace(state, context);
    }

    @Deprecated
    @Override
    public boolean canBucketPlace(BlockState state, Fluid fluid) {
        return block.canBucketPlace(state, fluid);
    }

    @Deprecated
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return List.of(asItem().getDefaultStack());
    }

    @Deprecated
    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return block.getRenderingSeed(state, pos);
    }

    @Deprecated
    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return block.getCullingShape(state, world, pos);
    }

    @Deprecated
    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return block.getSidesShape(state, world, pos);
    }

    @Deprecated
    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return block.getRaycastShape(state, world, pos);
    }

    @Deprecated
    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return block.getOpacity(state, world, pos);
    }

    @Override
    @Deprecated
    public @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return block.createScreenHandlerFactory(state, world, pos);
    }

    @Deprecated
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return block.canPlaceAt(state, world, pos);
    }

    @Deprecated
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return block.getAmbientOcclusionLightLevel(state, world, pos);
    }

    @Deprecated
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return block.getComparatorOutput(state, world, pos);
    }

    @Deprecated
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return block.getOutlineShape(state, world, pos, context);
    }

    @Deprecated
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return block.getCollisionShape(state, world, pos, context);
    }

    @Deprecated
    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return block.isShapeFullCube(state, world, pos);
    }

    @Deprecated
    @Override
    public boolean isCullingShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return block.isCullingShapeFullCube(state, world, pos);
    }

    @Deprecated
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return block.getCameraCollisionShape(state, world, pos, context);
    }

    @Deprecated
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        block.randomTick(state, world, pos, random);
    }

    @Deprecated
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        block.scheduledTick(state, world, pos, random);
    }

    @Deprecated
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return block.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Deprecated
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        block.onStacksDropped(state, world, pos, tool, dropExperience);
    }

    @Deprecated
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        block.onBlockBreakStart(state, world, pos, player);
    }

    @Deprecated
    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return block.getWeakRedstonePower(state, world, pos, direction);
    }

    @Deprecated
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        block.onEntityCollision(state, world, pos, entity);
    }

    @Deprecated
    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return block.getStrongRedstonePower(state, world, pos, direction);
    }

    @Deprecated
    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        block.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public MapColor getDefaultMapColor() {
        return block.getDefaultMapColor();
    }

    @Override
    public float getHardness() {
        return block.getHardness();
    }

    protected static void init(Block block) {
        TEMP.set(block);
    }
}
