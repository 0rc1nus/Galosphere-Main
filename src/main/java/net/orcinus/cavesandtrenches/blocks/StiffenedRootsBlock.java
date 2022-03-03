package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class StiffenedRootsBlock extends GrowingPlantHeadBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StiffenedRootsBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos blockPos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canSurvive(world, blockPos)) {
            world.scheduleTick(blockPos, this, 1);
        }

        if (direction != this.growthDirection || !neighborState.is(this) && !neighborState.is(this.getBodyBlock())) {
            if (state.getValue(WATERLOGGED)) {
                world.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            }
            return super.updateShape(state, direction, neighborState, world, blockPos, neighborPos);
        } else {
            return this.updateBodyAfterConvertedFromHead(state, this.getBodyBlock().defaultBlockState().setValue(StiffenedRootsPlantBlock.WATERLOGGED, world.getFluidState(blockPos).getType() == Fluids.WATER));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidstate = world.getFluidState(blockPos);
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(Random random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    @Override
    protected Block getBodyBlock() {
        return CTBlocks.STIFFENED_ROOTS_PLANTS.get();
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos blockPos, BlockState state) {
        BlockPos blockpos = blockPos.relative(this.growthDirection);
        int i = Math.min(state.getValue(AGE) + 1, 25);
        int j = this.getBlocksToGrowWhenBonemealed(random);

        for(int k = 0; k < j && this.canGrowInto(world.getBlockState(blockpos)); ++k) {
            world.setBlockAndUpdate(blockpos, state.setValue(AGE, i).setValue(WATERLOGGED, world.getFluidState(blockPos).getType() == Fluids.WATER));
            blockpos = blockpos.relative(this.growthDirection);
            i = Math.min(i + 1, 25);
        }
    }
}
