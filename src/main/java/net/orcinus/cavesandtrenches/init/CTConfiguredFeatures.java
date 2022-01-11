package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.util.RegistryHandler;
import net.orcinus.cavesandtrenches.world.gen.features.config.LargeCrystalConfig;

import java.util.List;

public class CTConfiguredFeatures {

    public static final RegistryHandler REGISTRY = CavesAndTrenches.REGISTRY;

    public static final ConfiguredFeature<?, ?> LARGE_ALLURITE_CRYSTAL_FLOOR = REGISTRY.registerConfiguredFeature("large_allurite_crystal_floor", CTFeatures.LARGE_CRYSTAL_SPIKE.get().configured(new LargeCrystalConfig(CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(3, 7), Direction.UP)));
    public static final ConfiguredFeature<?, ?> LARGE_LUMIERE_CRYSTAL_FLOOR = REGISTRY.registerConfiguredFeature("large_lumiere_crystal_floor", CTFeatures.LARGE_CRYSTAL_SPIKE.get().configured(new LargeCrystalConfig(CTBlocks.LUMIERE_BLOCK.get().defaultBlockState(), CTBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(3, 7), Direction.UP)));
    public static final ConfiguredFeature<?, ?> LARGE_ALLURITE_CRYSTAL_CEILING = REGISTRY.registerConfiguredFeature("large_allurite_crystal_ceiling", CTFeatures.LARGE_CRYSTAL_SPIKE.get().configured(new LargeCrystalConfig(CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(3, 7), Direction.DOWN)));
    public static final ConfiguredFeature<?, ?> LARGE_LUMIERE_CRYSTAL_CEILING = REGISTRY.registerConfiguredFeature("large_lumiere_crystal_ceiling", CTFeatures.LARGE_CRYSTAL_SPIKE.get().configured(new LargeCrystalConfig(CTBlocks.LUMIERE_BLOCK.get().defaultBlockState(), CTBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(3, 7), Direction.DOWN)));
    public static final ConfiguredFeature<?, ?> MYSTERIA_TREE = REGISTRY.registerConfiguredFeature("mysteria_tree", CTFeatures.MYSTERIA_TREE.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<BlockColumnConfiguration, ?> LICHEN_VINE_IN_MOSS = REGISTRY.registerConfiguredFeature("lichen_vines", Feature.BLOCK_COLUMN.configured(new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 3), 5).add(UniformInt.of(1, 7), 1).build()), new WeightedStateProvider(SimpleWeightedRandomList.builder()))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true)));
    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> LICHEN_PATCH_CEILING = REGISTRY.registerConfiguredFeature("lichen_patch_ceiling", Feature.VEGETATION_PATCH.configured(new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE.getName(), BlockStateProvider.simple(CTBlocks.GLOW_LICHEN_BLOCK.get()), LICHEN_VINE_IN_MOSS::placed, CaveSurface.CEILING, UniformInt.of(1, 2), 0.0F, 5, 0.08F, UniformInt.of(3, 6), 0.3F)));
    public static final ConfiguredFeature<?, ?> ORE_SILVER = REGISTRY.registerConfiguredFeature("ore_silver", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), CTBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), CTBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 9)));
    public static final ConfiguredFeature<?, ?> ORE_SILVER_SMALL = REGISTRY.registerConfiguredFeature("ore_silver_small", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), CTBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), CTBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 4)));

}
