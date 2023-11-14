package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.orcinus.galosphere.blocks.PinkSaltLampBlock;
import net.orcinus.galosphere.blocks.PinkSaltStrawBlock;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.world.gen.features.config.PinkSaltStrawPatchConfig;

public class PinkSaltStrawPatchFeature extends Feature<PinkSaltStrawPatchConfig> {

    public PinkSaltStrawPatchFeature(Codec<PinkSaltStrawPatchConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PinkSaltStrawPatchConfig> featurePlaceContext) {
        PinkSaltStrawPatchConfig config = featurePlaceContext.config();
        WorldGenLevel world = featurePlaceContext.level();
        BlockPos blockPos = featurePlaceContext.origin();
        RandomSource random = featurePlaceContext.random();
        int xRadius = config.radius().sample(random);
        int zRadius = config.radius().sample(random);
        int yRange = config.height().sample(random);
        Direction direction = config.direction();
        if (!world.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWater)) {
            return false;
        } else {
            for (int x = -xRadius; x <= xRadius; x++) {
                for (int z = -zRadius; z <= zRadius; z++) {
                    for (int y = -yRange; y <= yRange; y++) {
                        BlockPos pos = blockPos.offset(x, y, z);
                        if (x * x + z * z <= xRadius * zRadius) {
                            if (world.getBlockState(pos).is(GBlockTags.PINK_SALT_BLOCKS) && world.isStateAtPosition(pos.relative(direction), DripstoneUtils::isEmptyOrWater)) {
                                if (x == 0 && z == 0) {
                                    world.setBlock(pos.relative(direction), GBlocks.PINK_SALT_CLUSTER.defaultBlockState().setValue(PinkSaltLampBlock.WATERLOGGED, world.getBlockState(pos.relative(direction)).is(Blocks.WATER)).setValue(PinkSaltLampBlock.FACING, direction), 2);
                                } else {
                                    if (random.nextInt(3) == 0) {
                                        continue;
                                    }
                                    int sample = config.additionHeight().sample(random);
                                    if (random.nextInt(5) == 0) {
                                        sample *= 2;
                                    }
                                    this.addSaltStraw(world, pos.relative(direction), sample, direction);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
    }

    public void addSaltStraw(WorldGenLevel world, BlockPos blockPos, int height, Direction direction) {
        for (int i = 0; i <= height; i++) {
            BlockPos pos = blockPos.relative(direction, i);
            if (!world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater)) {
                world.setBlock(pos.relative(direction.getOpposite()), GBlocks.PINK_SALT_STRAW.defaultBlockState().setValue(PinkSaltStrawBlock.TIP_DIRECTION, direction.getOpposite()).setValue(PinkSaltStrawBlock.STRAW_SHAPE, PinkSaltStrawBlock.StrawShape.BOTTOM).setValue(PinkSaltStrawBlock.WATERLOGGED, world.getBlockState(pos).is(Blocks.WATER)), 2);
                break;
            }
            PinkSaltStrawBlock.StrawShape strawShape;
            if (i == height) {
                strawShape = PinkSaltStrawBlock.StrawShape.TOP;
            } else if (i == 0) {
                strawShape = PinkSaltStrawBlock.StrawShape.BOTTOM;
            } else {
                strawShape = PinkSaltStrawBlock.StrawShape.MIDDLE;
            }
            world.setBlock(pos, GBlocks.PINK_SALT_STRAW.defaultBlockState().setValue(PinkSaltStrawBlock.TIP_DIRECTION, direction).setValue(PinkSaltStrawBlock.STRAW_SHAPE, strawShape).setValue(PinkSaltStrawBlock.WATERLOGGED, world.getBlockState(pos).is(Blocks.WATER)), 2);
        }
    }


}
