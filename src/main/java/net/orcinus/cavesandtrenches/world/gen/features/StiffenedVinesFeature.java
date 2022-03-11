package net.orcinus.cavesandtrenches.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.cavesandtrenches.blocks.StiffenedRootsBlock;
import net.orcinus.cavesandtrenches.blocks.StiffenedRootsPlantBlock;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Random;

public class StiffenedVinesFeature extends Feature<NoneFeatureConfiguration> {

    public StiffenedVinesFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        int radius = UniformInt.of(5, 7).sample(random) + 1;
        List<BlockPos> placeList = Lists.newArrayList();
        if (!world.getBlockState(blockPos.above()).is(BlockTags.BASE_STONE_OVERWORLD)) {
            return false;
        } else {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos radiusPos = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                    if (random.nextInt(5) == 0 && !((z == -radius && z == radius) || (x == -radius && x == radius))){
                        if (random.nextBoolean()) {
                            placeList.add(radiusPos);
                        }
                    }
                }
            }
            for (BlockPos placePos : placeList) {
                int length = Mth.nextInt(random, 1, 3);
                for (int i = 0; i <= length; i++) {
                    if (world.getBlockState(placePos.above()).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        if (world.isStateAtPosition(placePos.below(i), DripstoneUtils::isEmptyOrWater)) {
                            world.setBlock(placePos.below(i), CTBlocks.STIFFENED_ROOTS_PLANTS.get().defaultBlockState().setValue(StiffenedRootsPlantBlock.WATERLOGGED, world.getFluidState(placePos.below(i)).getType() == Fluids.WATER), 2);
                            if (i == length) {
                                world.setBlock(placePos.below(i), CTBlocks.STIFFENED_ROOTS.get().defaultBlockState().setValue(StiffenedRootsBlock.WATERLOGGED, world.getFluidState(placePos.below(i)).getType() == Fluids.WATER), 2);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            return true;
        }
    }
}
