package net.orcinus.galosphere.world.gen.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.blocks.PinkSaltChamberBlock;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.mixin.access.WorldGenRegionAccessor;
import net.orcinus.galosphere.world.gen.FastNoise;
import net.orcinus.galosphere.world.gen.PinkSaltUtil;

import java.util.List;

public class OasisFeature extends Feature<NoneFeatureConfiguration> {

    public OasisFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        WorldGenLevel world = featurePlaceContext.level();
        BlockPos blockPos = featurePlaceContext.origin();
        RandomSource random = featurePlaceContext.random();
        int worldSeed = (int) world.getSeed();
        FastNoise fastNoise = new FastNoise(worldSeed);
        fastNoise.SetNoiseType(FastNoise.NoiseType.Simplex);
        fastNoise.SetFrequency(0.8f);
        int xRadius = this.getRandomRadius(random) / 2;
        int yRadius = UniformInt.of(15, 20).sample(random);
        int zRadius = this.getRandomRadius(random) / 2;
        List<BlockPos> positions = Lists.newArrayList();
        for (int x = -xRadius; x <= xRadius; x++) {
            for (int z = -zRadius; z <= zRadius; z++) {
                for (int y = -yRadius; y <= 0; y++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    StructureManager structureManager = ((WorldGenRegionAccessor) featurePlaceContext.level()).getStructureManager();
                    Structure structure = structureManager.registryAccess().registryOrThrow(Registries.STRUCTURE).get(new ResourceLocation(Galosphere.MODID, "pink_salt_shrine"));
                    boolean flag = structure != null && structureManager.getStructureAt(blockPos, structure).isValid();
                    if (flag) {
                        return false;
                    }
                    double xSquared = x * x;
                    double ySquared = y * y;
                    double zSquared = z * z;
                    double threshold = 1 + 3 * fastNoise.GetNoise(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    int yThreshold = this.isDry() ? 2 : 0;
                    if (xSquared / (xRadius * 2) + ySquared / (yRadius * 2) + zSquared / (zRadius * 2) < threshold && xSquared + ySquared + zSquared <= xRadius * zRadius && y < yThreshold) {
                        positions.add(pos);
                    }
                }
            }
        }
        int waterLevel = blockPos.getY() - 1;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (BlockPos position : positions) {
            BlockPos.MutableBlockPos mutable3 = mutableBlockPos.set(position.getX(), Math.min(position.getY(), waterLevel), position.getZ());
            for (int i = mutable3.getY(); i <= waterLevel; i++) {
                this.generateMiniAquifer(world, mutable3, random);
                mutable3.move(Direction.UP);
            }
        }
        if (this.isDry()) {
            for (BlockPos position : positions) {
                BlockPos.MutableBlockPos mutable3 = mutableBlockPos.set(position.getX(), Math.min(position.getY(), waterLevel), position.getZ());
                for (int i = mutable3.getY(); i <= waterLevel; i++) {
                    BlockState mutableState = world.getBlockState(mutable3);
                    boolean flag = mutableState.is(GBlockTags.PINK_SALT_BLOCKS) || mutableState.is(GBlocks.PINK_SALT_CHAMBER);
                    boolean flag1 = flag && (world.getBlockState(mutable3.above()).isAir() || world.getBlockState(mutable3.above()).is(GBlockTags.PINK_SALT_BLOCKS));
                    boolean flag2 = mutableState.is(Blocks.WATER);
                    if (flag1 || flag2) {
                        world.setBlock(mutable3, Blocks.AIR.defaultBlockState(), 2);
                        for (Direction direction : Direction.values()) {
                            if (world.getBlockState(mutable3.relative(direction)).is(Blocks.WATER)) {
                                world.scheduleTick(mutable3.relative(direction), Fluids.WATER, 0);
                            }
                        }
                    }
                    mutable3.move(Direction.UP);
                }
            }
        }
        return true;
    }

    private int getRandomRadius(RandomSource random) {
        return UniformInt.of(15, 21).sample(random);
    }

    private void generateMiniAquifer(WorldGenLevel world, BlockPos mutable2, RandomSource randomSource) {
        if (world.getBlockState(mutable2.above()).isAir()) {
            world.setBlock(mutable2, Blocks.WATER.defaultBlockState(), 2);
            world.scheduleTick(mutable2, Fluids.WATER, 0);
        }
        BlockPos.MutableBlockPos mutable3 = new BlockPos.MutableBlockPos();
        for (Direction value : Direction.values()) {
            if (value != Direction.UP) {
                mutable3.setWithOffset(mutable2, value);
                if (!world.getBlockState(mutable3).is(Blocks.WATER)) {
                    world.setBlock(mutable3, PinkSaltUtil.getBlock(world.getSeed(), mutable3).defaultBlockState(), 2);
                    postFeature(world, mutable3, randomSource);
                }
            }
        }
    }

    public boolean isDry() {
        return false;
    }

    public void postFeature(WorldGenLevel world, BlockPos.MutableBlockPos mutableBlockPos, RandomSource randomSource) {
    }

}
