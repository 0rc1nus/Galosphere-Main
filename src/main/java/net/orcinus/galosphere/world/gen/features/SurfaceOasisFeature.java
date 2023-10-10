package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;

import java.util.Optional;

public class SurfaceOasisFeature extends OasisFeature {

    public SurfaceOasisFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos.MutableBlockPos mutableBlockPos = featurePlaceContext.origin().mutable();
        WorldGenLevel world = featurePlaceContext.level();
        if (!world.getBlockState(featurePlaceContext.origin()).isAir()) {
            return false;
        }
        for (int i = 0; i < 100; i++) {
            mutableBlockPos.move(Direction.UP);
            BlockPos blockPos2 = mutableBlockPos.below();
             if (world.getFluidState(blockPos2).is(FluidTags.LAVA) || !world.getBlockState(blockPos2).isSolid()) {
                return false;
             }
            boolean place = super.place(new FeaturePlaceContext<>(Optional.empty(), world, featurePlaceContext.chunkGenerator(), featurePlaceContext.random(), mutableBlockPos, featurePlaceContext.config()));
            if (!place) {
                continue;
            }
            return true;
        }
        return super.place(featurePlaceContext);
    }

    @Override
    public void postFeature(WorldGenLevel world, BlockPos.MutableBlockPos mutableBlockPos) {
        if (world.getBlockState(mutableBlockPos).is(GBlockTags.PINK_SALT_BLOCKS) && world.getBlockState(mutableBlockPos.above()).isAir() && world.getRandom().nextBoolean() && world.getRandom().nextFloat() < 0.1F) {
            world.setBlock(mutableBlockPos.above(), GBlocks.SUCCULENT.defaultBlockState(), 2);
        }
    }
}
