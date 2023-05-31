package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class LichenMossBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public LichenMossBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            if (!blockState.getValue(LIT) && !level.isClientSide) {
                level.setBlock(blockPos, blockState.setValue(LIT, true), 2);
                level.scheduleTick(blockPos, this, 100);
            }
        }
        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (level.isClientSide) {
            return;
        }
        boolean bl2 = state.getValue(LIT);
        if (bl2 != level.hasNeighborSignal(blockPos)) {
            if (bl2) {
                level.scheduleTick(blockPos, this, 4);
            } else {
                level.setBlock(blockPos, state.cycle(LIT), 2);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        boolean flag = serverLevel.getEntitiesOfClass(Entity.class, new AABB(pos.above())).isEmpty();
        boolean flag1 = state.getValue(LIT) && !serverLevel.hasNeighborSignal(pos) && flag;
        if (flag1) {
            serverLevel.setBlock(pos, state.cycle(LIT), 2);
        } else {
            serverLevel.scheduleTick(pos, this, 40);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(LIT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 1.0F;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(LIT, blockPlaceContext.getLevel().hasNeighborSignal(blockPlaceContext.getClickedPos()));
    }

}
