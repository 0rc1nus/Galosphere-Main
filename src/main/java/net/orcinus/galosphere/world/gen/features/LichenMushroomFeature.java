package net.orcinus.galosphere.world.gen.features;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.galosphere.init.GBlocks;

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
        int radius = Mth.nextInt(random, 2, 4);
        int height = radius * 2;
        int tries = 0;
        return !this.generateLichenMushroom(world, blockPos, random, radius, height, Sets.newHashSet(), tries);
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
                        return this.generateLichenMushroom(world, blockPos.below(), random, radius, stemHeight, set, tries);
                    } else {
                        return false;
                    }
                }
            }
        }

        LichenMushroomFeature.generateHugeLichenMushroom(world, blockPos, random, radius, stemHeight, set);
        return false;
    }

    public static boolean generateHugeLichenMushroom(WorldGenLevel world, BlockPos blockPos, RandomSource random, int radius, int stemHeight, HashSet<BlockPos> set) {
        if (!LichenMushroomFeature.checkAirExposure(world, blockPos, stemHeight)) {
            return false;
        } else {
            if (radius == 1) {
                LichenMushroomFeature.generateUnqiueLichenMushroom(world, blockPos, set);
            } else {
                LichenMushroomFeature.generateDefaultLichenMushroom(world, blockPos, radius, stemHeight, set);
            }
            set.forEach(mossPos -> {
                if (random.nextInt(3) == 0) {
                    if (world.isStateAtPosition(mossPos.above(), DripstoneUtils::isEmptyOrWater)) {
                        Block block = random.nextBoolean() ? GBlocks.BOWL_LICHEN : GBlocks.LICHEN_ROOTS;
                        world.setBlock(mossPos.above(), block.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, world.getBlockState(mossPos.above()).is(Blocks.WATER)), 2);
                    }
                }
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                if (world.isStateAtPosition(mossPos.relative(direction), LichenMushroomFeature::canGenerate)) {
                    if (random.nextInt(3) == 0) {
                        world.setBlock(mossPos.relative(direction), GBlocks.LICHEN_SHELF.defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, direction).setValue(BaseCoralWallFanBlock.WATERLOGGED, world.getFluidState(mossPos.relative(direction)).is(FluidTags.WATER)), 2);
                    }
                }
                Direction randomDir = Direction.getRandom(random);
                if (world.isStateAtPosition(mossPos.relative(randomDir), LichenMushroomFeature::canGenerate)) {
                    if (random.nextInt(3) == 0) {
                        world.setBlock(mossPos.relative(randomDir), Blocks.GLOW_LICHEN.defaultBlockState().setValue(GlowLichenBlock.getFaceProperty(randomDir.getOpposite()), true).setValue(BlockStateProperties.WATERLOGGED, world.getFluidState(mossPos.relative(randomDir)).is(FluidTags.WATER)), 2);
                    }
                }
            });
            return true;
        }
    }

    private static void generateUnqiueLichenMushroom(WorldGenLevel world, BlockPos blockPos, HashSet<BlockPos> set) {
        int stemRadius = 1;
        int capRadius = 2;
        int radiusOneHeight = 3;
        for (int x = -stemRadius; x <= stemRadius; x++) {
            for (int z = -stemRadius; z <= stemRadius; z++) {
                BlockPos generatePos = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                for (int y = 0; y < radiusOneHeight; y++) {
                    BlockPos pos = generatePos.above(y);
                    boolean flag = x == stemRadius || x == -stemRadius;
                    boolean flag1 = z == stemRadius || z == -stemRadius;
                    if (y == 1) {
                        if (flag && flag1) continue;
                    } else if (y == 2) {
                        if (flag || flag1) continue;
                    }
                    if (world.isStateAtPosition(pos, LichenMushroomFeature::canGenerate)) {
                        world.setBlock(pos, Blocks.MUSHROOM_STEM.defaultBlockState(), 2);
                    }
                }
                BlockPos capPos = generatePos.above(radiusOneHeight);
                if (world.isStateAtPosition(capPos, LichenMushroomFeature::canGenerate)) {
                    world.setBlock(capPos, GBlocks.LICHEN_MOSS.defaultBlockState(), 2);
                    set.add(capPos);
                }
            }
        }
        for (int x = -capRadius; x <= capRadius; x++) {
            for (int z = -capRadius; z <= capRadius; z++) {
                BlockPos capPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + radiusOneHeight + 1, blockPos.getZ() + z);
                boolean flag = (x == capRadius || x == -capRadius) && (z == capRadius || z == -capRadius);
                if (flag) continue;
                if (x * x + z * z <= 1) continue;
                if (world.isStateAtPosition(capPos, LichenMushroomFeature::canGenerate)) {
                    world.setBlock(capPos, GBlocks.LICHEN_MOSS.defaultBlockState(), 2);
                    set.add(capPos);
                }
            }
        }
    }

    private static boolean canGenerate(BlockState state) {
        return state.canBeReplaced() || state.isAir() || state.is(Blocks.WATER) || state.is(GBlocks.BOWL_LICHEN);
    }

    private static void generateDefaultLichenMushroom(WorldGenLevel world, BlockPos blockPos, int radius, int stemHeight, HashSet<BlockPos> set) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = 0; y < stemHeight; y++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    int f4 = 4 * radius;
                    boolean flag = x == -radius || x == radius;
                    boolean flag1 = z == -radius || z == radius;
                    boolean flag2 = y == 0 && flag && flag1;
                    if (x * x + (2 * (Math.log(y * y) * f4) / f4) + z * z <= radius * radius && !flag2) {
                        if (world.isStateAtPosition(pos, LichenMushroomFeature::canGenerate)) {
                            world.setBlock(pos, Blocks.MUSHROOM_STEM.defaultBlockState(), 2);
                        }
                        for (int xMossRadius = -radius * 2; xMossRadius <= radius * 2; xMossRadius++) {
                            for (int zMossRadius = -radius * 2; zMossRadius <= radius * 2; zMossRadius++) {
                                BlockPos mossPos = new BlockPos(blockPos.getX() + xMossRadius, blockPos.getY() + stemHeight - 1, blockPos.getZ() + zMossRadius);
                                int distance = xMossRadius * xMossRadius + zMossRadius * zMossRadius;
                                if (distance < radius * 4) {
                                    if (world.isStateAtPosition(mossPos, LichenMushroomFeature::canGenerate)) {
                                        world.setBlock(mossPos, GBlocks.LICHEN_MOSS.defaultBlockState(), 2);
                                        set.add(mossPos);
                                    }
                                } else if (distance <= radius * 8 && distance >= radius * 4) {
                                    if (world.isStateAtPosition(mossPos.above(), LichenMushroomFeature::canGenerate)) {
                                        world.setBlock(mossPos.above(), GBlocks.LICHEN_MOSS.defaultBlockState(), 2);
                                        set.add(mossPos.above());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean checkAirExposure(LevelAccessor world, BlockPos blockPos, int height) {
        for (int y = 0; y < height; y++) {
            if (world.isStateAtPosition(blockPos.above(y), LichenMushroomFeature::canGenerate)) {
                return true;
            }
        }
        return false;
    }

}
