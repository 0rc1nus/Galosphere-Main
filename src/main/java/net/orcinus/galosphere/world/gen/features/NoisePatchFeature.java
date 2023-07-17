package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.orcinus.galosphere.blocks.PinkSaltLampBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.world.gen.PinkSaltUtil;
import net.orcinus.galosphere.world.gen.features.config.NoisePatchConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class NoisePatchFeature extends Feature<NoisePatchConfig> {

    public NoisePatchFeature(Codec<NoisePatchConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoisePatchConfig> context) {
        WorldGenLevel worldgenlevel = context.level();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        Predicate<BlockState> predicate = (state) -> state.is(BlockTags.BASE_STONE_OVERWORLD);
        int i = UniformInt.of(4, 7).sample(randomsource) + 1;
        int j = UniformInt.of(4, 7).sample(randomsource) + 1;
        Set<BlockPos> set = this.placeGroundPatch(context.config().caveSurface(), worldgenlevel, randomsource, blockpos, predicate, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(CaveSurface caveSurface, WorldGenLevel world, RandomSource random, BlockPos blockPos, Predicate<BlockState> predicate, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.mutable();
        Direction direction = caveSurface.getDirection();
        Direction direction1 = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        for(int i = -xRadius; i <= xRadius; ++i) {
            boolean flag = i == -xRadius || i == xRadius;

            for(int j = -zRadius; j <= zRadius; ++j) {
                boolean flag1 = j == -zRadius || j == zRadius;
                boolean flag2 = flag || flag1;
                boolean flag3 = flag && flag1;
                boolean flag4 = flag2 && !flag3;
                if (!flag3 && (!flag4 || !(random.nextFloat() > 0.3F))) {
                    blockpos$mutableblockpos.setWithOffset(blockPos, i, 0, j);

                    for (int k = 0; world.isStateAtPosition(blockpos$mutableblockpos, DripstoneUtils::isEmptyOrWater) && k < 5; ++k) {
                        blockpos$mutableblockpos.move(direction);
                    }

                    for (int i1 = 0; world.isStateAtPosition(blockpos$mutableblockpos, (state) -> !(state.isAir() || state.is(Blocks.WATER))) && i1 < 5; ++i1) {
                        blockpos$mutableblockpos.move(direction1);
                    }

                    blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, caveSurface.getDirection());
                    BlockState blockstate = world.getBlockState(blockpos$mutableblockpos1);
                    if (world.isStateAtPosition(blockpos$mutableblockpos, DripstoneUtils::isEmptyOrWater) && blockstate.isFaceSturdy(world, blockpos$mutableblockpos1, caveSurface.getDirection().getOpposite())) {
                        int l = ConstantInt.of(3).sample(random);
                        BlockPos blockpos = blockpos$mutableblockpos1.immutable();
                        boolean flag5 = this.placeGround(random, caveSurface, world, predicate, blockpos$mutableblockpos1, l);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }

    protected boolean placeGround(RandomSource random, CaveSurface caveSurface, WorldGenLevel world, Predicate<BlockState> predicate, BlockPos.MutableBlockPos mutable, int tries) {
        Block block = PinkSaltUtil.getBlock(world.getSeed(), mutable);
        for(int i = 0; i < tries; ++i) {
            BlockState blockstate = block.defaultBlockState();
            BlockState blockstate1 = world.getBlockState(mutable);
            if (!blockstate.is(blockstate1.getBlock())) {
                if (!predicate.test(blockstate1)) {
                    return i != 0;
                }

                world.setBlock(mutable, blockstate, 2);
                if (random.nextInt(30) == 0) {
                    for (Direction direction : Direction.values()) {
                        BlockPos clusterPos = mutable.relative(direction);
                        if (random.nextInt(10) == 0 && world.isStateAtPosition(clusterPos, DripstoneUtils::isEmptyOrWater)) {
                            world.setBlock(clusterPos, GBlocks.PINK_SALT_CLUSTER.defaultBlockState().setValue(PinkSaltLampBlock.FACING, direction).setValue(PinkSaltLampBlock.WATERLOGGED, world.getBlockState(clusterPos).is(Blocks.WATER)), 2);
                        }
                    }
                }
                mutable.move(caveSurface.getDirection());
            }
        }

        return true;
    }

}
