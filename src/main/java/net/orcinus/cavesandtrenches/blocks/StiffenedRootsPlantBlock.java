package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
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

public class StiffenedRootsPlantBlock extends GrowingPlantBodyBlock implements SimpleWaterloggedBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StiffenedRootsPlantBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(true) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos blockPos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canSurvive(world, blockPos)) {
            world.scheduleTick(blockPos, this, 1);
        }

        GrowingPlantHeadBlock growingplantheadblock = this.getHeadBlock();
        if (direction == this.growthDirection && !neighborState.is(this) && !neighborState.is(growingplantheadblock)) {
            return this.updateHeadAfterConvertedFromBody(state, growingplantheadblock.getStateForPlacement(world));
        } else {
            if (state.getValue(WATERLOGGED)) {
                world.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            }

            return super.updateShape(state, direction, neighborState, world, blockPos, neighborPos);
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
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) CTBlocks.STIFFENED_ROOTS.get();
    }
}
