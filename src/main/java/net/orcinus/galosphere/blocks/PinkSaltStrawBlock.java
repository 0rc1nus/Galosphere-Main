package net.orcinus.galosphere.blocks;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class PinkSaltStrawBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty FALLABLE = BooleanProperty.create("fallable");
    public static final EnumProperty<StrawShape> STRAW_SHAPE = EnumProperty.create("straw_shape", StrawShape.class);
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    private static final VoxelShape SHAPE = Block.box(4, 0.0, 4, 12, 16.0, 12);
    private static final VoxelShape TOP_UP_SHAPE = Block.box(4, 0.0, 4, 12, 11.0, 12);
    private static final VoxelShape TOP_DOWN_SHAPE = Block.box(4, 5.0, 4, 12, 16.0, 12);
    private static final Map<Predicate<BlockState>, SaltReaction> REACTIONS = Util.make(Maps.newHashMap(), map -> {
        map.put(blockState -> blockState.is(Blocks.COMPOSTER) && blockState.getValue(ComposterBlock.LEVEL) > 0, new SaltReaction(blockState -> GBlocks.SALINE_COMPOSTER.withPropertiesOf(blockState), 0.5F));
        map.put(blockState -> WeatheringCopper.NEXT_BY_BLOCK.get().containsKey(blockState.getBlock()), new SaltReaction(blockState -> WeatheringCopper.getNext(blockState.getBlock()).orElseThrow().withPropertiesOf(blockState), 0.0015F));
    });

    public PinkSaltStrawBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FALLABLE, false).setValue(TIP_DIRECTION, Direction.UP).setValue(WATERLOGGED, false).setValue(STRAW_SHAPE, StrawShape.TOP));
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.maybeTransferFluid(blockState, serverLevel, blockPos, randomSource.nextFloat());
        if (serverLevel.getBlockState(blockPos.below(2)).is(Blocks.LAVA) && blockState.getValue(WATERLOGGED) && blockState.getValue(TIP_DIRECTION) == Direction.UP) {
            BlockPos tip = findTip(blockState, serverLevel, blockPos, 7);
            if (tip != null && serverLevel.getFluidState(tip.above()).is(FluidTags.WATER)) {
                serverLevel.setBlockAndUpdate(tip.above(), GBlocks.PINK_SALT_STRAW.defaultBlockState().setValue(WATERLOGGED, true));
            }
        }
    }

    private boolean isStalactiteStartPos(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isStalactite(blockState) && !levelReader.getBlockState(blockPos.above()).is(GBlocks.PINK_SALT_STRAW);
    }

    public void maybeTransferFluid(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, float f) {
        BlockPos blockPos2 = findTip(blockState, serverLevel, blockPos, 11);
        if (blockPos2 == null) {
            return;
        }
        if (!isStalactiteStartPos(blockState, serverLevel, blockPos)) {
            return;
        }
        for (Predicate<BlockState> predicate : REACTIONS.keySet()) {
            BlockPos target = this.findTarget(serverLevel, blockPos2, predicate);
            if (target == null) {
                continue;
            }
            SaltReaction saltReaction = REACTIONS.get(predicate);
            BlockState result = saltReaction.function.apply(serverLevel.getBlockState(target));
            if (f > saltReaction.chance()) {
                return;
            }
            serverLevel.levelEvent(3005, target, 0);
            serverLevel.setBlockAndUpdate(target, result);
        }
    }

    @Nullable
    private BlockPos findTip(BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos2, int i) {
        if (isTip(blockState2)) {
            return blockPos2;
        }
        Direction direction = blockState2.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> biPredicate = (blockPos, blockState) -> blockState.is(GBlocks.PINK_SALT_STRAW) && blockState.getValue(TIP_DIRECTION) == direction;
        return findBlockVertical(levelAccessor, blockPos2, direction.getAxisDirection(), biPredicate, PinkSaltStrawBlock::isTip, i).orElse(null);
    }

    private static boolean isStalactite(BlockState state) {
        return state.is(GBlocks.PINK_SALT_STRAW) && state.getValue(TIP_DIRECTION) == Direction.DOWN;
    }

    @Nullable
    private BlockPos findTarget(Level level, BlockPos blockPos2, Predicate<BlockState> blockStatePredicate) {
        BiPredicate<BlockPos, BlockState> biPredicate = (blockPos, blockState) -> canDripThrough(level, blockPos, blockState);
        return findBlockVertical(level, blockPos2, Direction.DOWN.getAxisDirection(), biPredicate, blockStatePredicate, 11).orElse(null);
    }

    private boolean canDripThrough(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        if (blockState.isAir()) {
            return true;
        }
        if (blockState.isSolidRender(blockGetter, blockPos)) {
            return false;
        }
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        }
        VoxelShape voxelShape = blockState.getCollisionShape(blockGetter, blockPos);
        return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelShape, BooleanOp.AND);
    }

    private Optional<BlockPos> findBlockVertical(LevelAccessor levelAccessor, BlockPos blockPos, Direction.AxisDirection axisDirection, BiPredicate<BlockPos, BlockState> biPredicate, Predicate<BlockState> predicate, int i) {
        Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
        for (int j = 1; j < i; ++j) {
            mutableBlockPos.move(direction);
            BlockState blockState = levelAccessor.getBlockState(mutableBlockPos);
            if (predicate.test(blockState)) {
                return Optional.of(mutableBlockPos.immutable());
            }
            if (!levelAccessor.isOutsideBuildHeight(mutableBlockPos.getY()) && biPredicate.test(mutableBlockPos, blockState)) continue;
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        int bound = blockState.getValue(FALLABLE) ? 3 : 6;
        if (randomSource.nextInt(bound) == 0  && blockState.getValue(TIP_DIRECTION) == Direction.DOWN) {
            double d = (double)blockPos.getX() + randomSource.nextDouble();
            double e = (double)blockPos.getY() - 0.05;
            double f = (double)blockPos.getZ() + randomSource.nextDouble();
            level.addParticle(GParticleTypes.PINK_SALT_FALLING_DUST, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STRAW_SHAPE, TIP_DIRECTION, FALLABLE);
    }

    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos pos = blockHitResult.getBlockPos();
        double length = projectile.getDeltaMovement().length();
        if (length <= 0.4F) {
            return;
        }
        int steps = Mth.nextInt(level.getRandom(), 20, 30) * Mth.ceil(length);
        for (int i = 0; i < steps; i++) {
            int radius = 3;
            if (level.getRandom().nextInt(5) == 0) {
                radius *= 2;
            }
            mutableBlockPos.set(pos.getX() + Mth.nextInt(level.getRandom(), -radius, radius), pos.getY() + Mth.nextInt(level.getRandom(), -2, 2), pos.getZ() + Mth.nextInt(level.getRandom(), -radius, radius));
            BlockState chosenStates = level.getBlockState(mutableBlockPos);
            boolean flag = chosenStates.is(this) && chosenStates.getValue(TIP_DIRECTION) == Direction.DOWN && chosenStates.getValue(STRAW_SHAPE) == StrawShape.BOTTOM;
            if (flag) {
                level.setBlockAndUpdate(mutableBlockPos, chosenStates.getBlock().withPropertiesOf(chosenStates).setValue(FALLABLE, true));
                double distance = Math.sqrt(projectile.distanceToSqr(mutableBlockPos.getX(), mutableBlockPos.getY(), mutableBlockPos.getZ()));
                int ticks = Math.max(1, (int)distance);
                level.scheduleTick(mutableBlockPos, this, ticks);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction != Direction.UP && direction != Direction.DOWN) {
            return blockState;
        }
        Direction direction2 = blockState.getValue(TIP_DIRECTION);
        if (direction2 == Direction.DOWN && levelAccessor.getBlockTicks().hasScheduledTick(blockPos, this)) {
            return blockState;
        }
        if (direction == direction2.getOpposite() && !this.canSurvive(blockState, levelAccessor, blockPos)) {
            levelAccessor.scheduleTick(blockPos, this, 1);
        }
        StrawShape pinkSaltStrawShape = PinkSaltStrawBlock.calculateStrawShape(levelAccessor, blockPos, direction2);
        return blockState.setValue(STRAW_SHAPE, pinkSaltStrawShape);
    }

    private static StrawShape calculateStrawShape(LevelReader levelReader, BlockPos blockPos, Direction direction) {
        Direction oppositeDirection = direction.getOpposite();
        BlockState blockState = levelReader.getBlockState(blockPos.relative(direction));
        BlockState oppositeState = levelReader.getBlockState(blockPos.relative(oppositeDirection));
        if (!blockState.is(GBlocks.PINK_SALT_STRAW)) {
            return StrawShape.TOP;
        }
        if (!oppositeState.is(GBlocks.PINK_SALT_STRAW)) {
            return StrawShape.BOTTOM;
        }
        return StrawShape.MIDDLE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getNearestLookingVerticalDirection().getOpposite();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level levelAccessor = blockPlaceContext.getLevel();
        Direction direction2 = calculateTipDirection(levelAccessor, blockPos, direction);
        if (direction2 == null) {
            return null;
        }
        StrawShape pinkSaltStrawShape = calculateStrawShape(levelAccessor, blockPos, direction2);
        if (pinkSaltStrawShape == null) {
            return null;
        }
        return this.defaultBlockState().setValue(TIP_DIRECTION, direction2).setValue(STRAW_SHAPE, pinkSaltStrawShape).setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER);
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader levelReader, BlockPos blockPos, Direction direction) {
        Direction direction2;
        if (isValidPinkSaltStraw(levelReader, blockPos, direction)) {
            direction2 = direction;
        } else if (isValidPinkSaltStraw(levelReader, blockPos, direction.getOpposite())) {
            direction2 = direction.getOpposite();
        } else {
            return null;
        }
        return direction2;
    }

    private static boolean isValidPinkSaltStraw(LevelReader levelReader, BlockPos blockPos, Direction direction) {
        BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
        BlockState blockState = levelReader.getBlockState(blockPos2);
        return blockState.isFaceSturdy(levelReader, blockPos2, direction) || isPinkSaltStrawWithDirection(blockState, direction);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isValidPinkSaltStrawPlacement(levelReader, blockPos, blockState.getValue(TIP_DIRECTION));
    }

    private static boolean isValidPinkSaltStrawPlacement(LevelReader levelReader, BlockPos blockPos, Direction direction) {
        BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
        BlockState blockState = levelReader.getBlockState(blockPos2);
        return blockState.isFaceSturdy(levelReader, blockPos2, direction) || isPinkSaltStrawWithDirection(blockState, direction);
    }

    private static boolean isPinkSaltStrawWithDirection(BlockState blockState, Direction direction) {
        return blockState.is(GBlocks.PINK_SALT_STRAW) && blockState.getValue(TIP_DIRECTION) == direction;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Vec3 vec3 = blockState.getOffset(blockGetter, blockPos);
        VoxelShape voxelShape;
        if (blockState.getValue(STRAW_SHAPE) == StrawShape.TOP) {
            voxelShape = blockState.getValue(TIP_DIRECTION) == Direction.UP ? TOP_UP_SHAPE : TOP_DOWN_SHAPE;
        } else {
            voxelShape = SHAPE;
        }
        return voxelShape.move(vec3.x, 0.0, vec3.z);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(FALLABLE)) {
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
            BlockState blockState2 = blockState;
            while (blockState2.is(GBlocks.PINK_SALT_STRAW) && blockState2.getValue(TIP_DIRECTION) == Direction.DOWN) {
                FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, mutableBlockPos, blockState2);
                if (PinkSaltStrawBlock.isTip(blockState2)) {
                    int i = Math.max(1 + blockPos.getY() - mutableBlockPos.getY(), 6);
                    float f = (float) i;
                    fallingBlockEntity.setHurtsEntities(f, 40);
                    break;
                }
                mutableBlockPos.move(Direction.DOWN);
                blockState2 = serverLevel.getBlockState(mutableBlockPos);
            }
        }
        if (!blockState.canSurvive(serverLevel, blockPos)) {
            serverLevel.destroyBlock(blockPos, true);
        }
    }

    private static boolean isTip(BlockState blockState) {
        if (!blockState.is(GBlocks.PINK_SALT_STRAW)) {
            return false;
        }
        return blockState.getValue(STRAW_SHAPE) == StrawShape.TOP;
    }

    record SaltReaction(Function<BlockState, BlockState> function, float chance) {
    }

    public enum StrawShape implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        private final String name;

        StrawShape(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
