package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ChandelierBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final Function<BlockState, VoxelShape> FUNCTION = blockState -> blockState.getValue(VERTICAL_DIRECTION) == Direction.DOWN ? box(2.0D, 6.0D, 2.0D, 14.0D, 16.0D, 14.0D) : box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);

    public ChandelierBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(VERTICAL_DIRECTION, Direction.UP).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
        return blockState.getValue(HALF) != DoubleBlockHalf.LOWER;
    }

    public static int getLightEmission(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? 15 : 0;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        DoubleBlockHalf half = blockState.getValue(HALF);
        Direction verticalDirection = blockState.getValue(VERTICAL_DIRECTION);
        Direction oppositeDirection = verticalDirection.getOpposite();
        boolean flag = !blockState.canSurvive(levelAccessor, blockPos) && half == DoubleBlockHalf.LOWER && direction == verticalDirection.getOpposite();
        boolean flag1 = half == DoubleBlockHalf.UPPER && !levelAccessor.getBlockState(blockPos.relative(oppositeDirection)).is(this);
        if (flag || flag1) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        Direction direction = blockPlaceContext.getNearestLookingVerticalDirection().getOpposite();
        if (blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.relative(direction)).canBeReplaced(blockPlaceContext)) {
            return copyWaterloggedFrom(level, blockPos, this.defaultBlockState().setValue(VERTICAL_DIRECTION, direction));
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        Direction verticalDirection = blockState.getValue(VERTICAL_DIRECTION);
        BlockPos blockPos2 = blockPos.relative(verticalDirection);
        level.setBlock(blockPos2, ChandelierBlock.copyWaterloggedFrom(level, blockPos2, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(VERTICAL_DIRECTION, verticalDirection)), 3);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        Direction direction = blockState.getValue(VERTICAL_DIRECTION);
        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction.getOpposite()));
            return blockState2.is(this) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return Block.canSupportCenter(levelReader, blockPos.relative(direction.getOpposite()), direction);
    }

    public static BlockState copyWaterloggedFrom(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
            return blockState.setValue(BlockStateProperties.WATERLOGGED, levelReader.isWaterAt(blockPos));
        }
        return blockState;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                ChandelierBlock.preventCreativeDropFromBottomPart(level, blockPos, blockState, player);
            } else {
                ChandelierBlock.dropResources(blockState, level, blockPos, null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, Blocks.AIR.defaultBlockState(), blockEntity, itemStack);
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        BlockPos blockPos2;
        BlockState blockState2;
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState2 = level.getBlockState(blockPos2 = blockPos.relative(blockState.getValue(VERTICAL_DIRECTION).getOpposite()))).is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState3 = blockState2.hasProperty(BlockStateProperties.WATERLOGGED) && blockState2.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(blockPos2, blockState3, 35);
            level.levelEvent(player, 2001, blockPos2, Block.getId(blockState2));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, WATERLOGGED, HALF);
    }

    @Override
    public long getSeed(BlockState blockState, BlockPos blockPos) {
        return Mth.getSeed(blockPos.getX(), blockPos.below(blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(HALF) == DoubleBlockHalf.UPPER ? FUNCTION.apply(blockState) : SHAPE;
    }

}
