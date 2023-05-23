package net.orcinus.galosphere.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Galosphere.MODID);

    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_ALLURITE_CRYSTAL_FLOOR = register("large_allurite_crystal_floor", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_LUMIERE_CRYSTAL_FLOOR = register("large_lumiere_crystal_floor", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.FLOOR)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_ALLURITE_CRYSTAL_CEILING = register("large_allurite_crystal_ceiling", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> LARGE_LUMIERE_CRYSTAL_CEILING = register("large_lumiere_crystal_ceiling", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(4, 7), CaveSurface.CEILING)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> ALLURITE_CRYSTAL_FLOOR = register("allurite_crystal_floor", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> LUMIERE_CRYSTAL_FLOOR = register("lumiere_crystal_floor", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.FLOOR)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> ALLURITE_CRYSTAL_CEILING = register("allurite_crystal_ceiling", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.ALLURITE_BLOCK.get().defaultBlockState(), GBlocks.ALLURITE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_ALLURITE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING)));
    public static final RegistryObject<ConfiguredFeature<CrystalSpikeConfig, ?>> LUMIERE_CRYSTAL_CEILING = register("lumiere_crystal_ceiling", () -> new ConfiguredFeature<>(GFeatures.CRYSTAL_SPIKE.get(), new CrystalSpikeConfig(GBlocks.LUMIERE_BLOCK.get().defaultBlockState(), GBlocks.LUMIERE_CLUSTER.get().defaultBlockState(), GBlocks.GLINTED_LUMIERE_CLUSTER.get().defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING)));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> ORE_SILVER_SMALL = register("ore_silver_small", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 9)));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> ORE_SILVER_LARGE = register("ore_silver_large", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), GBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), GBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 12)));
    public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> BOWL_LICHEN = register("lichen_mushroom", () -> new ConfiguredFeature<>(GFeatures.BOWL_LICHEN.get(), FeatureConfiguration.NONE));
    public static final RegistryObject<ConfiguredFeature<SimpleBlockConfiguration, ?>> LICHEN_VEGETATION = register("lichen_vegetation", () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(GBlocks.BOWL_LICHEN.get().defaultBlockState(), 4).add(GBlocks.LICHEN_ROOTS.get().defaultBlockState(), 50).add(Blocks.AIR.defaultBlockState(), 15)))));
    public static final RegistryObject<ConfiguredFeature<VegetationPatchConfiguration, ?>> LICHEN_PATCH = register("lichen_patch", () -> new ConfiguredFeature<>(GFeatures.LICHEN_PATCH.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(GBlocks.LICHEN_MOSS.get()), PlacementUtils.inlinePlaced(LICHEN_VEGETATION.getHolder().get()), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F)));
    public static final RegistryObject<ConfiguredFeature<VegetationPatchConfiguration, ?>> GRAVEL_PATCH = register("gravel_patch", () -> new ConfiguredFeature<>(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(GBlockTags.GRAVEL_MAY_REPLACE, BlockStateProvider.simple(Blocks.GRAVEL), PlacementUtils.inlinePlaced(LICHEN_VEGETATION.getHolder().get()), CaveSurface.FLOOR, ConstantInt.of(3), 0.8f, 2, 0.05f, UniformInt.of(4, 7), 0.7f)));
    public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> LICHEN_CORDYCEPS = register("lichen_cordyceps", () -> new ConfiguredFeature<>(GFeatures.LICHEN_CORDYCEPS_COLUMN.get(), new NoneFeatureConfiguration()));

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name, Supplier<ConfiguredFeature<FC, F>> feature) {
        return CONFIGURED_FEATURES.register(name, feature);
    }


}
