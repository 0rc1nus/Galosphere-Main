package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

public class SucculentCropBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public SucculentCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return GItems.SUCCULENT_POD;
    }

    @Override
    public BlockState getStateForAge(int i) {
        return i == 2 ? GBlocks.SUCCULENT.defaultBlockState() : super.getStateForAge(i);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(3) != 0) {
            super.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }
}
