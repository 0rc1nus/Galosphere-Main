package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.init.GBlocks;

public class CordycepsBlock extends GrowingPlantHeadBlock {
    public static final BooleanProperty BULB = BooleanProperty.create("bulb");
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 6.0, 12.0);

    public CordycepsBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BULB, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BULB);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == Direction.UP) {
            return this.getBodyBlock().defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
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
}
