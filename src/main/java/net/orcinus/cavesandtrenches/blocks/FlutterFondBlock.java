package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import org.jetbrains.annotations.Nullable;

public class FlutterFondBlock extends BushBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<FondShape> FOND_SHAPE = EnumProperty.create("fond_shape", FondShape.class);
    public static final DirectionProperty FACING_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public FlutterFondBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FOND_SHAPE, FondShape.TOP).setValue(FACING_DIRECTION, Direction.UP).setValue(WATERLOGGED, false));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos blockPos) {
        Direction direction = state.getValue(FACING_DIRECTION);
        BlockPos blockpos = blockPos.relative(direction.getOpposite());
        BlockState blockstate = world.getBlockState(blockpos);
        return blockstate.isFaceSturdy(world, blockpos, direction) || blockstate.is(CTBlocks.FLUTTER_FOND.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos blockPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (direction == state.getValue(FACING_DIRECTION)) {
            if (neighborState.is(this) && neighborState.getValue(FOND_SHAPE) != FondShape.MIDDLE) {
                FondShape shape = world.getBlockState(blockPos.relative(state.getValue(FACING_DIRECTION).getOpposite())).is(this) ? FondShape.MIDDLE : FondShape.BOTTOM;
                return state.setValue(FOND_SHAPE, shape).setValue(FACING_DIRECTION, state.getValue(FACING_DIRECTION));
            }
            if (state.getValue(FOND_SHAPE) != FondShape.TOP && world.getBlockState(blockPos.relative(state.getValue(FACING_DIRECTION))).isAir()) {
                return state.setValue(FOND_SHAPE, FondShape.TOP).setValue(FACING_DIRECTION, state.getValue(FACING_DIRECTION));
            }
        }
        return super.updateShape(state, direction, neighborState, world, blockPos, neighborPos);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidstate = world.getFluidState(blockPos);
        return this.defaultBlockState().setValue(FACING_DIRECTION, context.getNearestLookingVerticalDirection().getOpposite()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(world, pos);
        return SHAPE.move(vec3.x, 0.0D, vec3.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FOND_SHAPE, FACING_DIRECTION, WATERLOGGED);
    }

    public enum FondShape implements StringRepresentable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        private final String name;

        FondShape(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
