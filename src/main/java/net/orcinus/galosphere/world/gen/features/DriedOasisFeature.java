package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.galosphere.init.GBlocks;

public class DriedOasisFeature extends OasisFeature {

    public DriedOasisFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean isDry() {
        return true;
    }

    @Override
    public void postFeature(WorldGenLevel world, BlockPos.MutableBlockPos mutableBlockPos, RandomSource randomSource) {
        if (randomSource.nextFloat() < 0.035F) {
            world.setBlock(mutableBlockPos, GBlocks.PINK_SALT_CHAMBER.defaultBlockState(), 2);
        }
    }
}
