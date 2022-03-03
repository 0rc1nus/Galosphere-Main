package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.world.gen.features.config.CrystalSpikeConfig;

import java.util.List;

public class CTConfiguredFeatures {

    public static final ConfiguredFeature<CrystalSpikeConfig, ?> LARGE_ALLURITE_CRYSTAL_FLOOR = registerConfiguredFeature("large_allurite_crystal_floor", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> LARGE_LUMIERE_CRYSTAL_FLOOR = registerConfiguredFeature("large_lumiere_crystal_floor", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.LUMIERE_BLOCK.get().defaultBlockState(), CTBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> LARGE_ALLURITE_CRYSTAL_CEILING = registerConfiguredFeature("large_allurite_crystal_ceiling", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> LARGE_LUMIERE_CRYSTAL_CEILING = registerConfiguredFeature("large_lumiere_crystal_ceiling", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.LUMIERE_BLOCK.get().defaultBlockState(), CTBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> ALLURITE_CRYSTAL_FLOOR = registerConfiguredFeature("allurite_crystal_floor", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> LUMIERE_CRYSTAL_FLOOR = registerConfiguredFeature("lumiere_crystal_floor", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.LUMIERE_BLOCK.get().defaultBlockState(), CTBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> ALLURITE_CRYSTAL_CEILING = registerConfiguredFeature("allurite_crystal_ceiling", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.ALLURITE_BLOCK.get().defaultBlockState(), CTBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING)));
    public static final ConfiguredFeature<CrystalSpikeConfig, ?> LUMIERE_CRYSTAL_CEILING = registerConfiguredFeature("lumiere_crystal_ceiling", CTFeatures.CRYSTAL_SPIKE.get().configured(new CrystalSpikeConfig(CTBlocks.LUMIERE_BLOCK.get().defaultBlockState(), CTBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING)));
//    public static final ConfiguredFeature<?, ?> LARGE_ALLURITE_CRYSTALS = registerConfiguredFeature("large_allurite_crystals", Feature.RANDOM_BOOLEAN_SELECTOR.configured(new RandomBooleanFeatureConfiguration(LARGE_ALLURITE_CRYSTAL_CEILING::placed, LARGE_ALLURITE_CRYSTAL_FLOOR::placed)));
//    public static final ConfiguredFeature<?, ?> LARGE_LUMIERE_CRYSTALS = registerConfiguredFeature("large_lumiere_crystals", Feature.RANDOM_BOOLEAN_SELECTOR.configured(new RandomBooleanFeatureConfiguration(LARGE_LUMIERE_CRYSTAL_CEILING::placed, LARGE_LUMIERE_CRYSTAL_FLOOR::placed)));
//    public static final ConfiguredFeature<?, ?> ALLURITE_CRYSTALS = registerConfiguredFeature("allurite_crystals", Feature.RANDOM_BOOLEAN_SELECTOR.configured(new RandomBooleanFeatureConfiguration(ALLURITE_CRYSTAL_CEILING::placed, ALLURITE_CRYSTAL_FLOOR::placed)));
//    public static final ConfiguredFeature<?, ?> LUMIERE_CRYSTALS = registerConfiguredFeature("lumiere_crystals", Feature.RANDOM_BOOLEAN_SELECTOR.configured(new RandomBooleanFeatureConfiguration(LUMIERE_CRYSTAL_CEILING::placed, LUMIERE_CRYSTAL_FLOOR::placed)));
    public static final ConfiguredFeature<?, ?> MYSTERIA_TREE = registerConfiguredFeature("mysteria_tree", CTFeatures.MYSTERIA_TREE.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<?, ?> ORE_SILVER = registerConfiguredFeature("ore_silver", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), CTBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), CTBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 9)));
    public static final ConfiguredFeature<?, ?> ORE_SILVER_SMALL = registerConfiguredFeature("ore_silver_small", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), CTBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), CTBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 4)));
    public static final ConfiguredFeature<?, ?> STIFFINED_ROOF = registerConfiguredFeature("stiffined_roof", CTFeatures.STIFFENED_ROOTS.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<?, ?> FLUTTERED_LEAF = registerConfiguredFeature("fluttered_leaf", CTFeatures.FLUTTERED_LEAF.get().configured(FeatureConfiguration.NONE));

    public static <C extends FeatureConfiguration, F extends Feature<C>, CF extends ConfiguredFeature<C, F>> CF registerConfiguredFeature(String key, CF configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CavesAndTrenches.MODID, key);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Configured Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

}
