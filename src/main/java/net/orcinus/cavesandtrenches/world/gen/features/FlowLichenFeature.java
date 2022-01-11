package net.orcinus.cavesandtrenches.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.phys.Vec3;
import net.orcinus.cavesandtrenches.init.CTBlocks;

import java.util.Random;

public class FlowLichenFeature extends Feature<NoneFeatureConfiguration> {

    public FlowLichenFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();
        if (!this.ableToPlace(world, pos)) {
            return false;
        } else {
            generate(world, pos, random);
            return true;
        }
    }

    public static void generate(WorldGenLevel world, BlockPos pos, Random random) {
        final int uniformLength = 16;
        for (int i = 0; i <= uniformLength; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                world.setBlock(pos.relative(direction, i), CTBlocks.GLOW_LICHEN_BLOCK.get().defaultBlockState(), 2);
            }
        }

    }

    public static void generateCircle(WorldGenLevel world, BlockPos pos, Random random, BlockState state, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
                if (x * x + z * z <= radius * radius) {
                    world.setBlock(blockPos, state, 2);
                }
            }
        }
    }

    public boolean ableToPlace(WorldGenLevel world, BlockPos pos) {
        return world.getBlockState(pos.above()).is(BlockTags.BASE_STONE_OVERWORLD) && world.isEmptyBlock(pos);
    }
}
