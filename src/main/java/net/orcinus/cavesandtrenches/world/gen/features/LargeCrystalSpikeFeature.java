package net.orcinus.cavesandtrenches.world.gen.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.orcinus.cavesandtrenches.blocks.PollinatedClusterBlock;
import net.orcinus.cavesandtrenches.world.gen.features.config.LargeCrystalConfig;

import java.util.List;
import java.util.Random;

public class LargeCrystalSpikeFeature extends Feature<LargeCrystalConfig> {

    public LargeCrystalSpikeFeature(Codec<LargeCrystalConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LargeCrystalConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        LargeCrystalConfig config = context.config();
        if (world.isEmptyBlock(blockPos.below())) {
            return false;
        } else {
            List<BlockPos> trigList = Lists.newArrayList();
            List<BlockPos> crystalPos = Lists.newArrayList();
            final int randomChance = random.nextInt(4);
            final int randomValue = UniformInt.of(4, 6).sample(random) + 1;
            int stepHeight = randomValue + 14 + Mth.nextInt(random, 10, 14);
            for (int i = 0; i < stepHeight; i++) {
                int xRadius = (int) (randomValue - (i / 2.0D));
                int zRadius = (int) (randomValue - (i / 2.0D));
                for (int x = -xRadius; x <= xRadius; x++) {
                    for (int z = -zRadius; z <= zRadius; z++) {
                        if ((x * x) + (z * z) <= xRadius * zRadius) {
                            BlockPos.MutableBlockPos finalPos = new BlockPos.MutableBlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                            if (world.isEmptyBlock(finalPos.relative(config.crystal_direction.getOpposite(), 2)) || world.getBlockState(finalPos.relative(config.crystal_direction.getOpposite())).is(Blocks.WATER) || world.getBlockState(finalPos.relative(config.crystal_direction.getOpposite())).is(Blocks.LAVA)) return false;
                            if (world.isEmptyBlock(finalPos.relative(config.crystal_direction.getOpposite()))) finalPos = finalPos.relative(config.crystal_direction.getOpposite()).mutable();
                            float floatvalue = switch (randomChance) {
                                case 1 -> 11 * Mth.PI / 6;
                                case 2 -> Mth.PI / 6;
                                case 3 -> 7 * Mth.PI / 6;
                                case 0 -> 5 * Mth.PI / 6;
                                default -> throw new IllegalStateException("Unexpected value: " + randomChance);
                            };
                            float q = Mth.cos(floatvalue) * i;
                            float k = Mth.sin(Mth.PI / 2) * i;
                            float l = Mth.sin(floatvalue) * i;
                            if (config.crystal_direction == Direction.DOWN) {
                                q = -q;
                                k = -k;
                                l = -l;
                            }
                            BlockPos trigPos = finalPos.offset(q, k, l);
                            trigList.add(trigPos);
                        }
                    }
                }
            }
            for (BlockPos pos : trigList) {
                if (world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater)) {
                    this.setBlock(world, pos, config.crystal_state);
                    crystalPos.add(pos);
                }
            }
            for (BlockPos position : crystalPos) {
                if (random.nextInt(6) == 0) {
                    for (Direction direction : Direction.values()) {
                        if (random.nextBoolean() && world.getBlockState(position.relative(direction)).getMaterial().isReplaceable() && world.getBlockState(position.relative(direction)).getMaterial() != Material.LAVA) {
                            this.setBlock(world, position.relative(direction), config.cluster_state.setValue(PollinatedClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, world.getFluidState(position.relative(direction)).getType() == Fluids.WATER));
                        }
                    }
                }
            }
            return false;
        }
    }
}
