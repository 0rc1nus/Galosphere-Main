package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class LichenPatchFeature extends Feature<VegetationPatchConfiguration> {

    public LichenPatchFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        VegetationPatchConfiguration vegetationpatchconfiguration = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        Predicate<BlockState> predicate = (state) -> state.is(vegetationpatchconfiguration.replaceable);
        int i = vegetationpatchconfiguration.xzRadius.sample(randomsource) + 1;
        int j = vegetationpatchconfiguration.xzRadius.sample(randomsource) + 1;
        Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, vegetationpatchconfiguration, randomsource, blockpos, predicate, i, j);
        this.distributeVegetation(context, worldgenlevel, vegetationpatchconfiguration, randomsource, set);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel world, VegetationPatchConfiguration config, RandomSource random, BlockPos blockPos, Predicate<BlockState> predicate, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.mutable();
        Direction direction = config.surface.getDirection();
        Direction direction1 = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        for(int i = -xRadius; i <= xRadius; ++i) {
            boolean flag = i == -xRadius || i == xRadius;

            for(int j = -zRadius; j <= zRadius; ++j) {
                boolean flag1 = j == -zRadius || j == zRadius;
                boolean flag2 = flag || flag1;
                boolean flag3 = flag && flag1;
                boolean flag4 = flag2 && !flag3;
                if (!flag3 && (!flag4 || config.extraEdgeColumnChance != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance))) {
                    blockpos$mutableblockpos.setWithOffset(blockPos, i, 0, j);

                    for (int k = 0; world.isStateAtPosition(blockpos$mutableblockpos, BlockBehaviour.BlockStateBase::isAir) && k < config.verticalRange; ++k) {
                        blockpos$mutableblockpos.move(direction);
                    }

                    for (int i1 = 0; world.isStateAtPosition(blockpos$mutableblockpos, (state) -> !state.isAir()) && i1 < config.verticalRange; ++i1) {
                        blockpos$mutableblockpos.move(direction1);
                    }

                    blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, config.surface.getDirection());
                    BlockState blockstate = world.getBlockState(blockpos$mutableblockpos1);
                    if (world.isEmptyBlock(blockpos$mutableblockpos) && blockstate.isFaceSturdy(world, blockpos$mutableblockpos1, config.surface.getDirection().getOpposite())) {
                        int l = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos blockpos = blockpos$mutableblockpos1.immutable();
                        boolean flag5 = this.placeGround(world, config, predicate, random, blockpos$mutableblockpos1, l);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }

    protected void distributeVegetation(FeaturePlaceContext<VegetationPatchConfiguration> context, WorldGenLevel world, VegetationPatchConfiguration config, RandomSource random, Set<BlockPos> set) {
        for (BlockPos blockpos : set) {
            if (config.vegetationChance > 0.0F && random.nextFloat() > 0.85F) {
                this.placeVegetation(world, config, context.chunkGenerator(), random, blockpos);
            }
        }

    }

    protected boolean placeVegetation(WorldGenLevel world, VegetationPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        return config.vegetationFeature.value().place(world, chunkGenerator, random, pos.relative(config.surface.getDirection().getOpposite()));
    }

    protected boolean placeGround(WorldGenLevel world, VegetationPatchConfiguration config, Predicate<BlockState> predicate, RandomSource random, BlockPos.MutableBlockPos mutable, int tries) {
        for(int i = 0; i < tries; ++i) {
            BlockState blockstate = config.groundState.getState(random, mutable);
            BlockState blockstate1 = world.getBlockState(mutable);
            if (!blockstate.is(blockstate1.getBlock())) {
                if (!predicate.test(blockstate1)) {
                    return i != 0;
                }

                world.setBlock(mutable, blockstate, 2);
                mutable.move(config.surface.getDirection());
            }
        }

        return true;
    }

}
