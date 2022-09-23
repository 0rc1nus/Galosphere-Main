package net.orcinus.galosphere.world.gen.features;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.orcinus.galosphere.init.GBlocks;

import java.util.Arrays;
import java.util.HashSet;

public class LichenMushroomFeature extends Feature<NoneFeatureConfiguration> {

    public LichenMushroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        if (world.isStateAtPosition(blockPos.below(), state -> state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.DIRT)) && world.isStateAtPosition(blockPos, state -> state.isAir() || state.is(Blocks.WATER) || (state.getMaterial().isReplaceable() && state.getMaterial() != Material.LAVA))) {
            int radius = Mth.nextInt(random, 2, 4);
            int height = radius * 2;
            int tries = 0;
            HashSet<BlockPos> set = Sets.newHashSet();
            return !this.generateLichenMushroom(world, blockPos, random, radius, height, set, tries);
        } else {
            return false;
        }
    }

    private boolean generateLichenMushroom(WorldGenLevel world, BlockPos blockPos, RandomSource random, int radius, int stemHeight, HashSet<BlockPos> set, int tries) {
        for (int checkx = -radius; checkx <= radius; checkx++ ) {
            for (int checkz = -radius; checkz <= radius; checkz++) {
                boolean flag = checkx == -radius || checkx == radius;
                boolean flag1 = checkz == -radius || checkz == radius;
                boolean flag2 = flag && flag1;
                BlockPos checkpos = new BlockPos(blockPos.getX() + checkx, blockPos.getY() - 1, blockPos.getZ() + checkz);
                if ((world.isEmptyBlock(checkpos) || !world.getFluidState(checkpos).isEmpty()) && !flag2) {
                    tries++;
                    if (tries < 5) {
                        return generateLichenMushroom(world, blockPos.below(), random, radius, stemHeight, set, tries);
                    } else {
                        return false;
                    }
                }
            }
        }

        LichenMushroomFeature.generateHugeLichenMushroom(world, blockPos, random, radius, stemHeight, set);
        return false;
    }

    public static void generateHugeLichenMushroom(WorldGenLevel world, BlockPos blockPos, RandomSource random, int radius, int stemHeight, HashSet<BlockPos> set) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = 0; y < stemHeight; y++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    int f4 = 4 * radius;
                    boolean flag = x == -radius || x == radius;
                    boolean flag1 = z == -radius || z == radius;
                    boolean flag2 = y == 0 && flag && flag1;
                    if (x * x + (2 * (Math.log(y * y) * f4) / f4) + z * z <= radius * radius && !flag2) {
                        if (world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava) || world.getBlockState(pos).getMaterial().isReplaceable()) {
                            world.setBlock(pos, Blocks.MUSHROOM_STEM.defaultBlockState(), 2);
                        }
                        for (int xMossRadius = -radius * 2; xMossRadius <= radius * 2; xMossRadius++) {
                            for (int zMossRadius = -radius * 2; zMossRadius <= radius * 2; zMossRadius++) {
                                BlockPos mossPos = new BlockPos(blockPos.getX() + xMossRadius, blockPos.getY() + stemHeight - 1, blockPos.getZ() + zMossRadius);
                                int distance = xMossRadius * xMossRadius + zMossRadius * zMossRadius;
                                if (distance < radius * 4) {
                                    if (world.isStateAtPosition(mossPos, DripstoneUtils::isEmptyOrWaterOrLava) || world.getBlockState(mossPos).getMaterial().isReplaceable()) {
                                        world.setBlock(mossPos, GBlocks.LICHEN_MOSS.get().defaultBlockState(), 2);
                                        set.add(mossPos);
                                    }
                                } else if (distance <= radius * 8 && distance >= radius * 4) {
                                    if (world.isStateAtPosition(mossPos.above(), DripstoneUtils::isEmptyOrWaterOrLava) || world.getBlockState(mossPos.above()).getMaterial().isReplaceable()) {
                                        world.setBlock(mossPos.above(), GBlocks.LICHEN_MOSS.get().defaultBlockState(), 2);
                                        set.add(mossPos.above());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        set.forEach(mossPos -> {
            if (random.nextInt(3) == 0) {
                if (world.isEmptyBlock(mossPos.above()) || world.getBlockState(mossPos).getMaterial().isReplaceable()) {
                    Block block = random.nextInt(10) == 0 ? GBlocks.BOWL_LICHEN.get() : GBlocks.LICHEN_ROOTS.get();
                    world.setBlock(mossPos.above(), block.defaultBlockState(), 2);
                }
            }
            if (random.nextInt(5) == 0) {
                Direction.Plane.HORIZONTAL.forEach(direction -> {
                    BlockPos placePos = mossPos.relative(direction);
                    if (world.isEmptyBlock(placePos) || world.getBlockState(placePos).getMaterial().isReplaceable()) {
                        world.setBlock(placePos, GBlocks.LICHEN_SHELF.get().defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, direction).setValue(BaseCoralWallFanBlock.WATERLOGGED, world.getBlockState(placePos).is(Blocks.WATER)), 2);
                    }
                });
            }
            if (random.nextInt(10) == 0) {
                Arrays.stream(Direction.values()).filter(direction -> world.isStateAtPosition(mossPos.relative(direction), DripstoneUtils::isEmptyOrWater)).forEach(direction -> {
                    world.setBlock(mossPos.relative(direction), Blocks.GLOW_LICHEN.defaultBlockState().setValue(GlowLichenBlock.getFaceProperty(direction.getOpposite()), true).setValue(BlockStateProperties.WATERLOGGED, world.getBlockState(mossPos.relative(direction)).is(Blocks.WATER)), 2);
                });
            }
        });
    }

}
