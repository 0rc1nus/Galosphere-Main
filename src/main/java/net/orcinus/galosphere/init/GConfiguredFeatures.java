package net.orcinus.galosphere.init;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;

import java.util.List;

public class GConfiguredFeatures {

    public static void init() {
    }

    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_ALLURITE_CRYSTAL_FLOOR = registerConfiguredFeature("large_allurite_crystal_floor", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_LUMIERE_CRYSTAL_FLOOR = registerConfiguredFeature("large_lumiere_crystal_floor", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_ALLURITE_CRYSTAL_CEILING = registerConfiguredFeature("large_allurite_crystal_ceiling", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_LUMIERE_CRYSTAL_CEILING = registerConfiguredFeature("large_lumiere_crystal_ceiling", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> ALLURITE_CRYSTAL_FLOOR = registerConfiguredFeature("allurite_crystal_floor", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> LUMIERE_CRYSTAL_FLOOR = registerConfiguredFeature("lumiere_crystal_floor", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> ALLURITE_CRYSTAL_CEILING = registerConfiguredFeature("allurite_crystal_ceiling", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING));
    public static final Holder<ConfiguredFeature<CrystalSpikeConfig, ?>> LUMIERE_CRYSTAL_CEILING = registerConfiguredFeature("lumiere_crystal_ceiling", GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SILVER = registerConfiguredFeature("ore_silver", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SILVER_SMALL = registerConfiguredFeature("ore_silver_small", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 4));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, F feature, FC featureConfiguration) {
        ResourceLocation resourceLocation = new ResourceLocation(Galosphere.MODID, id);

        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Placed Feature ID: \"" + resourceLocation + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, resourceLocation.toString(), new ConfiguredFeature<>(feature, featureConfiguration));
    }


}
