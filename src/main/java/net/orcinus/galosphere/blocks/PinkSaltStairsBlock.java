package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PinkSaltStairsBlock extends StairBlock implements PinkSalt {

    public PinkSaltStairsBlock(BlockState blockState, Properties properties) {
        super(blockState, properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return PinkSalt.getNext(blockState.getBlock()).isPresent();
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.burningRandomTick(blockState, serverLevel, blockPos);
    }

}