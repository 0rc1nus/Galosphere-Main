package net.orcinus.cavesandtrenches.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.cavesandtrenches.init.CTBlocks;

import java.util.Random;

public class MysteriaTreeFeature extends Feature<NoneFeatureConfiguration> {

    public MysteriaTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        if (checkPosition(world, blockPos)) return false;
        if (!(world.isEmptyBlock(blockPos) || world.getBlockState(blockPos.below()).is(BlockTags.BASE_STONE_OVERWORLD))) {
            return false;
        } else {
            buildTree(world, blockPos, random);
            return true;
        }
    }

    public static boolean checkPosition(LevelAccessor world, BlockPos pos) {
        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            flag = world.isEmptyBlock(pos.above(i));
        }
        return !flag;
    }

    public static void buildTree(WorldGenLevel world, BlockPos pos, Random random) {
        if (checkPosition(world, pos)) return;
        final int height = UniformInt.of(6, 10).sample(random);
        BlockPos.MutableBlockPos mut = pos.below().mutable();
        for (int nubX = -1; nubX <= 1; nubX++) {
            for (int nubZ = -1; nubZ <= 1; nubZ++) {
                int i = UniformInt.of(3, 5).sample(random);
                for (int nubY = 0; nubY <= i; nubY++) {
                    BlockPos nubPos = new BlockPos(pos.getX() + nubX, pos.getY(), pos.getZ() + nubZ);
                    if (nubX * nubX + nubZ * nubZ <= 1) {
                        world.setBlock(nubPos.above(nubY), CTBlocks.MYSTERIA_LOG.get().defaultBlockState(), 2);
                    } else {
                        if (random.nextInt(5) == 0) {
                            world.setBlock(nubPos, CTBlocks.MYSTERIA_LOG.get().defaultBlockState(), 2);
                            for (int t = 0; t < random.nextInt(4) + 2; t++) {
                                if (world.getBlockState(nubPos.below(t)).getMaterial().isReplaceable()) {
                                    world.setBlock(nubPos.below(t), CTBlocks.MYSTERIA_LOG.get().defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int y = 0; y <= height; y++) {
            mut.move(Direction.UP);
            world.setBlock(mut, CTBlocks.MYSTERIA_LOG.get().defaultBlockState(), 2);
        }
        int branchLength = UniformInt.of(3, 5).sample(random);
        for (Direction direction : Direction.values()) {
            if (direction != Direction.DOWN) {
                for (int w = 0; w <= branchLength; w++) {
                    BlockPos branchPos = new BlockPos(pos.getX(), pos.getY() + height, pos.getZ());
                    BlockPos.MutableBlockPos mutable = branchPos.mutable().move(Direction.UP, w).move(direction, w);
                    if (direction != Direction.UP) {
                        world.setBlock(mutable, CTBlocks.MYSTERIA_LOG.get().defaultBlockState(), 2);
                    }
                }
                int radius = Mth.nextInt(random, 2, 3);
                for (int leaveX = -radius; leaveX <= radius; leaveX++) {
                    for (int leaveZ = -radius; leaveZ <= radius; leaveZ++) {
                        for (int leaveY = -radius; leaveY <= radius; leaveY++) {
                            BlockPos leavePos = new BlockPos(mut.getX() + leaveX, mut.getY() + 1 + leaveY, mut.getZ() + leaveZ);
                            if (direction == Direction.UP) {
                                if (leaveX * leaveX * leaveY * leaveY * leaveZ * leaveZ <= radius * radius) {
                                    BlockPos.MutableBlockPos topMut = leavePos.mutable().move(Direction.UP, branchLength);
                                    world.setBlock(topMut, CTBlocks.MYSTERIA_CINDERS.get().defaultBlockState(), 2);
                                }
                            } else {
                                if (leaveX * leaveX + leaveY * leaveY + leaveZ * leaveZ <= radius * radius) {
                                    BlockPos.MutableBlockPos move = leavePos.mutable().move(Direction.UP, branchLength).move(direction, branchLength);
                                    int uniform = UniformInt.of(0, 5).sample(random);
                                    for (int p = 0; p <= uniform; p++) {
                                        BlockPos below = move.below(p);
                                        if (Feature.isAir(world, below)) {
                                            world.setBlock(below, CTBlocks.MYSTERIA_CINDERS.get().defaultBlockState(), 2);
                                        }
                                    }
                                    int length = UniformInt.of(4, 6).sample(random);
                                    for (int chances = 0; chances < 40; chances++) {
                                        BlockPos.MutableBlockPos vinePos = move.below(uniform + 1).mutable();
                                        if (world.isEmptyBlock(vinePos)) {
                                            for (int t = 0; t <= length; t++) {
                                                if (world.isEmptyBlock(vinePos)) {
                                                    if (t == length || !world.isEmptyBlock(vinePos.below())) {
                                                        world.setBlock(vinePos, CTBlocks.MYSTERIA_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, 17, 25)), 2);
                                                        break;
                                                    }
                                                    world.setBlock(vinePos, CTBlocks.MYSTERIA_VINES_PLANTS.get().defaultBlockState(), 2);
                                                }
                                                vinePos.move(Direction.DOWN);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
