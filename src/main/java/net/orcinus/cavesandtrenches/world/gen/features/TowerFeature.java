package net.orcinus.cavesandtrenches.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TowerFeature extends Feature<NoneFeatureConfiguration> {

    public TowerFeature(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        buildTower(world, blockPos);
        return true;
    }

    public static void buildTower(LevelAccessor world, BlockPos blockPos) {
        int height = 3;
        for (int i = 0; i < height; i++) {
            BlockPos placePos = blockPos.below(height);
            if (world.getBlockState(blockPos.below(i)).is(BlockTags.BASE_STONE_OVERWORLD)) {
                break;
            } else {
                world.setBlock(placePos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
            }
        }
    }
}
