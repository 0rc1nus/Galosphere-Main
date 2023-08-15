package net.orcinus.galosphere.world.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.orcinus.galosphere.init.GBlocks;

public interface PinkSaltUtil {

    static Block getBlock(long seed, BlockPos blockPos) {
        return PinkSaltUtil.getBlock(seed, blockPos.mutable());
    }

    static Block getBlock(long seed, BlockPos.MutableBlockPos mutable) {
        return PinkSaltUtil.getBlock((int) seed, mutable, GBlocks.PASTEL_PINK_SALT.get(), GBlocks.ROSE_PINK_SALT.get(), GBlocks.PINK_SALT.get());
    }

    static Block getBlock(long seed, BlockPos blockPos, Block pastel, Block rose, Block normal) {
        return PinkSaltUtil.getBlock((int)seed, blockPos.mutable(), pastel, rose, normal);
    }

    static Block getBlock(int seed, BlockPos.MutableBlockPos mutable, Block pastel, Block rose, Block normal) {
        FastNoise fastNoise = new FastNoise(seed);
        fastNoise.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
        fastNoise.SetFractalOctaves(1);
        fastNoise.SetFractalGain(0.3f);
        fastNoise.SetFrequency(0.07F);
        double noise2 = Math.abs(fastNoise.GetNoise(mutable.getX(), mutable.getZ()) + 1) * 3;
        Block block;
        if (noise2 > 4.0D) {
            block = pastel;
        } else if (noise2 > 3.0D) {
            block = rose;
        } else {
            block = normal;
        }
        return block;
    }

}
