package net.orcinus.cavesandtrenches.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.cavesandtrenches.init.CTBlocks;

import java.util.Random;

public class MoonstoneBoulderFeature extends Feature<NoneFeatureConfiguration> {

    public MoonstoneBoulderFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel world = context.level();
        Random random = context.random();
        if (!world.isEmptyBlock(blockpos) || !world.getBlockState(blockpos.below()).is(BlockTags.DIRT)) {
            return false;
        } else {
            boolean flag = false;
            int radius = 8;
            int height = Mth.nextInt(random, 8, 16);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -1; y <= height; y++) {
                        BlockPos blockPos = new BlockPos(blockpos.getX() + x, blockpos.getY() - 3 + y, blockpos.getZ() + z);
                        if (y > 1) {
                            if (y * (x * x) + ((y * y) / 4) + y * (z * z) <= radius * radius) {
                                if (!world.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                                    flag = false;
                                } else {
                                    if (world.isEmptyBlock(blockPos.below(5))) {
                                        flag = false;
                                    } else {
                                        world.setBlock(blockPos, CTBlocks.MOONSTONE.get().defaultBlockState(), 2);
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return flag;
        }
    }
}
