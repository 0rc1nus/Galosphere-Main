package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.galosphere.blocks.blockentities.GlowInkClumpsBlockEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class GlowInkClumpsBlock extends MultifaceBlock implements SimpleWaterloggedBlock, EntityBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    public GlowInkClumpsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AGE, 0));
    }

    public static ToIntFunction<BlockState> emission(int light, int diminished) {
        return (state) -> state.getValue(WATERLOGGED) && MultifaceBlock.hasAnyFace(state) ? light : diminished;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, AGE);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState p_153304_, LevelAccessor world, BlockPos pos, BlockPos p_153307_) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, p_153304_, world, pos, p_153307_);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        return !ctx.getItemInHand().is(GBlocks.GLOW_INK_CLUMPS.asItem()) || super.canBeReplaced(state, ctx);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos blockPos) {
        return state.getFluidState().isEmpty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GlowInkClumpsBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState p_153213_, BlockEntityType<T> type) {
        return world.isClientSide ? null : type == GBlockEntityTypes.GLOW_INK_CLUMPS ? (level, pos, state, te) -> GlowInkClumpsBlockEntity.serverTick(level, pos, state, (GlowInkClumpsBlockEntity) te) : null;
    }

}
