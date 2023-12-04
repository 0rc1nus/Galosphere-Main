package net.orcinus.galosphere.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;

public class BerserkerFeature extends Feature<NoneFeatureConfiguration> {

    public BerserkerFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos pos = featurePlaceContext.origin();
        WorldGenLevel world = featurePlaceContext.level();
        Berserker berserker = GEntityTypes.BERSERKER.create(world.getLevel());
        berserker.setPersistenceRequired();
        berserker.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        BlockState directionalState = world.getBlockState(pos.below(2));
        int rot = 180;
        if (directionalState.is(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            Direction direction = directionalState.getValue(HorizontalDirectionalBlock.FACING);
            if (direction == Direction.EAST) {
                rot = 90;
            } else if (direction == Direction.NORTH) {
                rot = 0;
            } else if (direction == Direction.WEST) {
                rot = 270;
            }
            berserker.setYRot(rot);
            world.setBlock(pos.below(2), Blocks.CHISELED_DEEPSLATE.defaultBlockState(), 2);
        }
        berserker.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
        world.addFreshEntityWithPassengers(berserker);
        return true;
    }
}
