package net.orcinus.cavesandtrenches.blocks;

import com.ibm.icu.text.TimeZoneFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FlutterFrondBlock extends Block implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<FrondShape> SHAPE = EnumProperty.create("shape", FrondShape.class);

    public FlutterFrondBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(SHAPE, FrondShape.TOP));
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader world, BlockPos blockPos) {
        BlockPos below = blockPos.below();
        BlockState belowState = world.getBlockState(below);
        return belowState.isFaceSturdy(world, below, Direction.UP) || belowState.is(CTBlocks.FLUTTERLEAF.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos blockPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        BlockState blockState = state;
        if (direction == Direction.UP) {
            if (neighborState.is(this) && neighborState.getValue(SHAPE) != FrondShape.MIDDLE) {
                FrondShape shape = world.getBlockState(blockPos.below()).is(this) ? FrondShape.MIDDLE : FrondShape.BOTTOM;
                blockState = state.setValue(SHAPE, shape);
            }
            if (state.getValue(SHAPE) != FrondShape.TOP && (world.getBlockState(blockPos.above()).is(Blocks.WATER) || world.getBlockState(blockPos.above()).isAir())) {
                blockState = state.setValue(SHAPE, FrondShape.TOP);
            }
        }
        return !canSurvive(state, world, blockPos) ? Blocks.AIR.defaultBlockState() : blockState;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos blockPos, BlockState blockState, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, SHAPE);
    }

    public enum FrondShape implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        private final String name;

        FrondShape(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
