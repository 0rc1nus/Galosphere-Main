package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.blocks.blockentities.CordycepsBlockEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

public class CordycepsBlock extends GrowingPlantHeadBlock implements EntityBlock {
    public static final BooleanProperty BULB = BooleanProperty.create("bulb");
    public static final BooleanProperty ALIVE = BooleanProperty.create("alive");
    public static final IntegerProperty ALIVE_STAGE = IntegerProperty.create("alive_stage", 0, 5);
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D);
    protected static final VoxelShape BULB_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);

    public CordycepsBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.2);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BULB, false).setValue(ALIVE, false).setValue(ALIVE_STAGE, 0));
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState belowState = levelReader.getBlockState(blockPos.below());
        if (belowState.hasProperty(ALIVE) && belowState.getValue(ALIVE) && belowState.is(this.getHeadBlock())) {
            return false;
        }
        return super.canSurvive(blockState, levelReader, blockPos);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(BULB) ? BULB_SHAPE : super.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    protected BlockState getGrowIntoState(BlockState blockState, RandomSource randomSource) {
        return super.getGrowIntoState(blockState, randomSource).setValue(BULB, blockState.getValue(BULB) || (!blockState.getValue(ALIVE) && randomSource.nextFloat() < 0.11f));
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPos2 = blockPos.relative(this.growthDirection);
        int i = Math.min(blockState.getValue(AGE) + 1, 25);
        if (blockState.getValue(BULB)) {
            serverLevel.setBlockAndUpdate(blockPos2, blockState.setValue(AGE, i));
        } else {
            serverLevel.setBlock(blockPos, blockState.setValue(BULB, true), 2);
        }
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean bl) {
        return !blockState.getValue(ALIVE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BULB, ALIVE, ALIVE_STAGE);
    }

    @Override
    public SoundType getSoundType(BlockState state) {
        return this.soundType;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return NetherVines.getBlocksToGrowWhenBonemealed(randomSource);
    }

    @Override
    protected Block getBodyBlock() {
        return GBlocks.LICHEN_CORDYCEPS_PLANT.get();
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return NetherVines.isValidGrowthState(blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockState, BlockEntityType<T> type) {
        return world.isClientSide ? null : type == GBlockEntityTypes.CORDYCEPS.get() ? (level, pos, state, te) -> CordycepsBlockEntity.serverTick(level, pos, state, (CordycepsBlockEntity) te) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CordycepsBlockEntity(blockPos, blockState);
    }
}
