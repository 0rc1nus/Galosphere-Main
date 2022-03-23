package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.init.CTBlocks;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Random;

public class CrystalSpikeFeature extends Feature<CrystalSpikeConfig> {

    public CrystalSpikeFeature(Codec<CrystalSpikeConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CrystalSpikeConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        CrystalSpikeConfig config = context.config();
        List<BlockPos> trigList = Lists.newArrayList();
        List<BlockPos> clusterPos = Lists.newArrayList();
        //-2808380414920390921
        //300 -36 60742
        //499 20 7181
        //TODO: MAKE A TRAILER WITH AS THE WORLD CAVES IN
        //TODO: SEED COORDS = 260 8 2160
        //1/21
        //-2286246326674673194
        //TODO: MAKE A RADIUS CHECK AND RELATIVE DIRECTION  CHECK
        //-346814623854640707
        //-4103 -5 165
        //-1330 35 18572
        boolean flag = false;
        int radiusCheck = config.xzRadius.sample(random) + 1;
        final int randomChance = random.nextInt(4);
        final int stepHeight = radiusCheck + 14 + Mth.nextInt(random, 10, 14);
        if (world.isStateAtPosition(blockPos.relative(config.crystal_direction.getDirection().getOpposite()), DripstoneUtils::isEmptyOrWaterOrLava) && world.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
            if (this.placeSpike(world, blockPos, radiusCheck, stepHeight, randomChance, trigList, config.crystal_direction.getDirection(), random)) {
                flag = placeCrystals(world, random, config, trigList, clusterPos, flag);
            }
        }
        return flag;
    }

    private boolean placeCrystals(WorldGenLevel world, Random random, CrystalSpikeConfig config, List<BlockPos> trigList, List<BlockPos> clusterPos, boolean flag) {
        for (BlockPos pos : trigList) {
            if (world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                this.setBlock(world, pos, config.crystal_state);
                clusterPos.add(pos);
                flag = true;
            }
        }
        for (BlockPos pos : clusterPos) {
            if (random.nextInt(6) == 0) {
                for (Direction direction : Direction.values()) {
                    BlockPos relative = pos.relative(direction);
                    if (random.nextBoolean() && world.isStateAtPosition(relative, DripstoneUtils::isEmptyOrWater)) {
                        this.setBlock(world, relative, config.cluster_state.setValue(PollinatedClusterBlock.POLLINATED, random.nextBoolean()).setValue(PollinatedClusterBlock.FACING, direction).setValue(PollinatedClusterBlock.WATERLOGGED, world.getFluidState(relative).getType() == Fluids.WATER));
                    }
                }
            }
        }
        return flag;
    }

    public boolean placeSpike(LevelAccessor world, BlockPos blockPos, int startRadius, int height, int randomChance, List<BlockPos> crystalPos, Direction direction, Random random) {
        boolean flag = false;
        for (int y = 0; y < height; y++) {
            int radius = startRadius - y / 2;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                    if (x * x + z * z <= radius * radius) {
                        if (direction == Direction.DOWN) {
                            if (world.isStateAtPosition(pos.below(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                                return placeSpike(world, blockPos.below(), startRadius, height, randomChance, crystalPos, direction, random);
                            }
                        }
                        else if (direction == Direction.UP) {
                            BlockPos.MutableBlockPos mut = pos.mutable();
                            for (int i = 0; i < 10; i++) {
                                if (!world.isStateAtPosition(mut.above(), DripstoneUtils::isEmptyOrWaterOrLava)) break;
                                mut.move(Direction.UP);
                            }
                            pos = mut.immutable();

                            if (world.isStateAtPosition(pos.above(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                                return false;
                            }
                        }
                        this.calciteBloom(world, pos.relative(direction), random, radius);
                        float delta = switch (randomChance) {
                            case 1 -> 11 * Mth.PI / 6;
                            case 2 -> Mth.PI / 6;
                            case 3 -> 7 * Mth.PI / 6;
                            case 0 -> 5 * Mth.PI / 6;
                            default -> throw new IllegalStateException("Unexpected value: " + randomChance);
                        };
                        float q = Mth.cos(delta) * y;
                        float k = Mth.sin(Mth.PI / 2) * y;
                        float l = Mth.sin(delta) * y;
                        float xx = direction == Direction.UP ? -q : q;
                        float yy = direction == Direction.UP ? -k : k;
                        float zz = direction == Direction.UP ? -l : l;
                        BlockPos trigPos = pos.offset(xx, yy, zz);
                        if (world.isStateAtPosition(trigPos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                            crystalPos.add(trigPos);
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }

    private boolean calciteBloom(LevelAccessor world, BlockPos blockPos, Random random, int crystalRadius) {
        int radius = crystalRadius / 4;
        int height = ConstantInt.of(2).sample(random);
        boolean flag = false;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -height; y <= height; y++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    for (Direction direction : Direction.values()) {
                        if (world.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD) && world.isStateAtPosition(pos.relative(direction), DripstoneUtils::isEmptyOrWaterOrLava)) {
                            world.setBlock(pos, Blocks.CALCITE.defaultBlockState(), 2);
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }

    public static boolean debugSpike(LevelAccessor world, BlockPos blockPos, Random random) {
        boolean flag = false;
        final int initRadius = UniformInt.of(4, 7).sample(random);
        final int height = initRadius + 14 + Mth.nextInt(random, 10, 14);
        List<BlockPos> crystalPos = Lists.newArrayList();
        List<BlockPos> clusterPos = Lists.newArrayList();
        final int randomChance = random.nextInt(4);
        for (int y = 0; y < height; y++) {
            int radius = initRadius - y / 2;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                    if (x * x + z * z <= radius * radius) {
                        if (world.isStateAtPosition(pos.below(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                            return debugSpike(world, blockPos.below(), random);
                        } else {
                            float delta = switch (randomChance) {
                                case 1 -> 11 * Mth.PI / 6;
                                case 2 -> Mth.PI / 6;
                                case 3 -> 7 * Mth.PI / 6;
                                case 0 -> 5 * Mth.PI / 6;
                                default -> throw new IllegalStateException("Unexpected value: " + randomChance);
                            };
                            float q = Mth.cos(delta) * y;
                            float k = Mth.sin(Mth.PI / 2) * y;
                            float l = Mth.sin(delta) * y;
                            BlockPos trigPos = pos.offset(q, k, l);
                            if (world.isStateAtPosition(trigPos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                                world.setBlock(trigPos, CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), 2);
                                crystalPos.add(trigPos);
                            }
                        }
                    }
                }
            }
        }
        for (BlockPos pos : crystalPos) {
            if (world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                world.setBlock(pos, CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), 2);
                clusterPos.add(pos);
                flag = true;
            }
        }
        for (BlockPos pos : clusterPos) {
            if (random.nextInt(15) == 0) {
                for (Direction direction : Direction.values()) {
                    BlockPos relative = pos.relative(direction);
                    if (random.nextBoolean() && world.isStateAtPosition(relative, DripstoneUtils::isEmptyOrWater)) {
                        world.setBlock(relative, CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState().setValue(PollinatedClusterBlock.FACING, direction).setValue(PollinatedClusterBlock.WATERLOGGED, world.getFluidState(relative).getType() == Fluids.WATER), 2);
                    }
                }
            }
        }
        return flag;
    }

}
