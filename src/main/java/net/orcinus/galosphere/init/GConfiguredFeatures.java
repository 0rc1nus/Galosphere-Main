package net.orcinus.galosphere.init;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
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
import net.orcinus.galosphere.world.gen.features.config.NoisePatchConfig;
import net.orcinus.galosphere.world.gen.features.config.PinkSaltStrawPatchConfig;

import java.util.List;

public class GConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ALLURITE_CRYSTAL_FLOOR = registerConfiguredFeature("large_allurite_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LUMIERE_CRYSTAL_FLOOR = registerConfiguredFeature("large_lumiere_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ALLURITE_CRYSTAL_CEILING = registerConfiguredFeature("large_allurite_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LUMIERE_CRYSTAL_CEILING = registerConfiguredFeature("large_lumiere_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALLURITE_CRYSTAL_FLOOR = registerConfiguredFeature("allurite_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMIERE_CRYSTAL_FLOOR = registerConfiguredFeature("lumiere_crystal_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALLURITE_CRYSTAL_CEILING = registerConfiguredFeature("allurite_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMIERE_CRYSTAL_CEILING = registerConfiguredFeature("lumiere_crystal_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL = registerConfiguredFeature("ore_silver_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_LARGE = registerConfiguredFeature("ore_silver_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BOWL_LICHEN = registerConfiguredFeature("lichen_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LICHEN_VEGETATION = registerConfiguredFeature("lichen_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LICHEN_PATCH = registerConfiguredFeature("lichen_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRAVEL_PATCH = registerConfiguredFeature("gravel_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LICHEN_CORDYCEPS = registerConfiguredFeature("lichen_cordyceps");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_SALT_GROUND_NOISE_PATCH = registerConfiguredFeature("pink_salt_ground_noise_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_SALT_CEILING_NOISE_PATCH = registerConfiguredFeature("pink_salt_ceiling_noise_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_SALT_STRAW_CEILING_PATCH = registerConfiguredFeature("pink_salt_straw_ceiling_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_SALT_STRAW_FLOOR_PATCH = registerConfiguredFeature("pink_salt_straw_floor_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS = registerConfiguredFeature("oasis");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRIED_OASIS = registerConfiguredFeature("dried_oasis");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BERSERKER = registerConfiguredFeature("mobs/berserker");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstapContext.lookup(Registries.CONFIGURED_FEATURE);
        FeatureUtils.register(bootstapContext, LARGE_ALLURITE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR, 0.25F));
        FeatureUtils.register(bootstapContext, LARGE_LUMIERE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR, 0.25F));
        FeatureUtils.register(bootstapContext, LARGE_ALLURITE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING, 0.25F));
        FeatureUtils.register(bootstapContext, LARGE_LUMIERE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING, 0.25F));
        FeatureUtils.register(bootstapContext, ALLURITE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR, 0.25F));
        FeatureUtils.register(bootstapContext, LUMIERE_CRYSTAL_FLOOR, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR, 0.25F));
        FeatureUtils.register(bootstapContext, ALLURITE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.defaultBlockState(), GBlocks.ALLURITE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING, 0.25F));
        FeatureUtils.register(bootstapContext, LUMIERE_CRYSTAL_CEILING, GFeatures.CRYSTAL_SPIKE, new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.defaultBlockState(), GBlocks.LUMIERE_CLUSTER.defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING, 0.25F));
        FeatureUtils.register(bootstapContext, ORE_SILVER_SMALL, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState())), 9));
        FeatureUtils.register(bootstapContext, ORE_SILVER_LARGE, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState())), 12));
        FeatureUtils.register(bootstapContext, BOWL_LICHEN, GFeatures.BOWL_LICHEN, FeatureConfiguration.NONE);
        FeatureUtils.register(bootstapContext, LICHEN_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(GBlocks.BOWL_LICHEN.defaultBlockState(), 4).add(GBlocks.LICHEN_ROOTS.defaultBlockState(), 50).add(Blocks.AIR.defaultBlockState(), 15))));
        FeatureUtils.register(bootstapContext, LICHEN_PATCH, GFeatures.LICHEN_PATCH, new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(GBlocks.LICHEN_MOSS), PlacementUtils.inlinePlaced(holderGetter.getOrThrow(LICHEN_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F));
        FeatureUtils.register(bootstapContext, GRAVEL_PATCH, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(GBlockTags.GRAVEL_MAY_REPLACE, BlockStateProvider.simple(Blocks.GRAVEL), PlacementUtils.inlinePlaced(holderGetter.getOrThrow(LICHEN_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8f, 2, 0.05f, UniformInt.of(4, 7), 0.7f));
        FeatureUtils.register(bootstapContext, LICHEN_CORDYCEPS, GFeatures.LICHEN_CORDYCEPS_COLUMN, FeatureConfiguration.NONE);
        FeatureUtils.register(bootstapContext, PINK_SALT_GROUND_NOISE_PATCH, GFeatures.NOISE_PATCH, new NoisePatchConfig(CaveSurface.FLOOR));
        FeatureUtils.register(bootstapContext, PINK_SALT_CEILING_NOISE_PATCH, GFeatures.NOISE_PATCH, new NoisePatchConfig(CaveSurface.CEILING));
        FeatureUtils.register(bootstapContext, PINK_SALT_STRAW_CEILING_PATCH, GFeatures.PINK_SALT_STRAW_PATCH, new PinkSaltStrawPatchConfig(UniformInt.of(2, 8), UniformInt.of(1, 4), UniformInt.of(1, 3), Direction.UP));
        FeatureUtils.register(bootstapContext, PINK_SALT_STRAW_FLOOR_PATCH, GFeatures.PINK_SALT_STRAW_PATCH, new PinkSaltStrawPatchConfig(UniformInt.of(2, 8), UniformInt.of(1, 4), UniformInt.of(1, 3), Direction.DOWN));
        FeatureUtils.register(bootstapContext, OASIS, GFeatures.OASIS, FeatureConfiguration.NONE);
        FeatureUtils.register(bootstapContext, DRIED_OASIS, GFeatures.DRIED_OASIS, FeatureConfiguration.NONE);
        FeatureUtils.register(bootstapContext, BERSERKER, GFeatures.BERSERKER, FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
        bootstapContext.register(resourceKey, new ConfiguredFeature<>(feature, featureConfiguration));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfiguredFeature(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Galosphere.MODID, id));
    }


}
