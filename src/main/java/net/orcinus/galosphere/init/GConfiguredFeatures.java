package net.orcinus.galosphere.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;

import java.util.List;

public class GConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ALLURITE_CRYSTAL_FLOOR = createKey("large_allurite_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LUMIERE_CRYSTAL_FLOOR = createKey("large_lumiere_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ALLURITE_CRYSTAL_CEILING = createKey("large_allurite_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LUMIERE_CRYSTAL_CEILING = createKey("large_lumiere_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALLURITE_CRYSTAL_FLOOR = createKey("allurite_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMIERE_CRYSTAL_FLOOR = createKey("lumiere_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALLURITE_CRYSTAL_CEILING = createKey("allurite_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMIERE_CRYSTAL_CEILING = createKey("lumiere_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = createKey("ore_silver");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL = createKey("ore_silver_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BOWL_LICHEN = createKey("lichen_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LICHEN_VEGETATION = createKey("lichen_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LICHEN_PATCH = createKey("lichen_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LICHEN_CORDYCEPS = createKey("lichen_cordyceps");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerConfiguredFeature(context, LARGE_ALLURITE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR));
        registerConfiguredFeature(context, LARGE_LUMIERE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR));
        registerConfiguredFeature(context, LARGE_ALLURITE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING));
        registerConfiguredFeature(context, LARGE_LUMIERE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING));
        registerConfiguredFeature(context, ALLURITE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR));
        registerConfiguredFeature(context, LUMIERE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR));
        registerConfiguredFeature(context, ALLURITE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING));
        registerConfiguredFeature(context, LUMIERE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING));
        registerConfiguredFeature(context, ORE_SILVER, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState())), 9));
        registerConfiguredFeature(context, ORE_SILVER_SMALL, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState())), 4));
        registerConfiguredFeature(context, BOWL_LICHEN, GFeatures.BOWL_LICHEN, FeatureConfiguration.NONE);
        registerConfiguredFeature(context, LICHEN_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(GBlocks.BOWL_LICHEN.defaultBlockState(), 4).add(GBlocks.LICHEN_ROOTS.defaultBlockState(), 50).add(Blocks.AIR.defaultBlockState(), 15))));
        registerConfiguredFeature(context, LICHEN_PATCH, GFeatures.LICHEN_PATCH, new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(GBlocks.LICHEN_MOSS), PlacementUtils.inlinePlaced(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(LICHEN_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F));
        registerConfiguredFeature(context, LICHEN_CORDYCEPS, GFeatures.LICHEN_CORDYCEPS_COLUMN, FeatureConfiguration.NONE);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
        context.register(resourceKey, new ConfiguredFeature<>(feature, featureConfiguration));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String string) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Galosphere.MODID, string));
    }

}
