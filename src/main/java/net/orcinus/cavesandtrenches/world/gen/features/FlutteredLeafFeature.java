package net.orcinus.cavesandtrenches.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.cavesandtrenches.blocks.FlutterFondBlock;
import net.orcinus.cavesandtrenches.init.CTBlocks;

import java.util.Random;

public class FlutteredLeafFeature extends Feature<NoneFeatureConfiguration> {

    public FlutteredLeafFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        if (world.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWater) && world.getBlockState(blockPos.below()).isFaceSturdy(world, blockPos.below(), Direction.UP)) {
            int height = Mth.nextInt(random, 4, 8);
            for (int i = 0; i <= height; i++) {
                FlutterFondBlock.FondShape shape = i > 0 ? i == height ? FlutterFondBlock.FondShape.TOP : FlutterFondBlock.FondShape.MIDDLE : FlutterFondBlock.FondShape.BOTTOM;
                BlockPos placePos = blockPos.above(i);
                if (world.isStateAtPosition(placePos, DripstoneUtils::isEmptyOrWater)) {
                    world.setBlock(placePos, CTBlocks.FLUTTER_FOND.get().defaultBlockState().setValue(FlutterFondBlock.FOND_SHAPE, shape).setValue(FlutterFondBlock.WATERLOGGED, world.getFluidState(placePos).getType() == Fluids.WATER), 2);
                } else {
                    world.setBlock(placePos, CTBlocks.FLUTTER_FOND.get().defaultBlockState().setValue(FlutterFondBlock.FOND_SHAPE, shape).setValue(FlutterFondBlock.WATERLOGGED, world.getFluidState(placePos).getType() == Fluids.WATER), 2);
                    break;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
