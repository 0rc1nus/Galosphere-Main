package net.orcinus.galosphere.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.orcinus.galosphere.Galosphere;

import java.util.List;

public class GPlacedFeatures {

    public static void init() { }

    public static final ResourceKey<PlacedFeature> LARGE_CEILING_ALLURITE_CRYSTALS = registerPlacedFeature("large_ceiling_allurite_crystals");
    public static final ResourceKey<PlacedFeature> LARGE_CEILING_LUMIERE_CRYSTALS = registerPlacedFeature("large_ceiling_lumiere_crystals");
    public static final ResourceKey<PlacedFeature> ALLURITE_CEILING_CRYSTALS = registerPlacedFeature("allurite_ceiling_crystals");
    public static final ResourceKey<PlacedFeature> LUMIERE_CEILING_CRYSTALS = registerPlacedFeature("lumiere_ceiling_crystals");
    public static final ResourceKey<PlacedFeature> LARGE_FLOOR_ALLURITE_CRYSTALS = registerPlacedFeature("large_floor_allurite_crystals");
    public static final ResourceKey<PlacedFeature> LARGE_FLOOR_LUMIERE_CRYSTALS = registerPlacedFeature("large_floor_lumiere_crystals");
    public static final ResourceKey<PlacedFeature> ALLURITE_FLOOR_CRYSTALS = registerPlacedFeature("allurite_floor_crystals");
    public static final ResourceKey<PlacedFeature> LUMIERE_FLOOR_CRYSTALS = registerPlacedFeature("lumiere_floor_crystals");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_SMALL = registerPlacedFeature("ore_silver_small");
    public static final ResourceKey<PlacedFeature> BOWL_LICHEN = registerPlacedFeature("bowl_lichen");
    public static final ResourceKey<PlacedFeature> LICHEN_VEGETATION = registerPlacedFeature("lichen_caves_vegetation");
    public static final ResourceKey<PlacedFeature> GRAVEL_PATCH = registerPlacedFeature("gravel_patch");
    public static final ResourceKey<PlacedFeature> LICHEN_CORDYCEPS_COLUMN = registerPlacedFeature("lichen_cordyceps_column");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_LARGE = registerPlacedFeature("ore_silver_large");
    public static final ResourceKey<PlacedFeature> PINK_SALT_NOISE_GROUND_PATCH = registerPlacedFeature("pink_salt_noise_ground_patch");
    public static final ResourceKey<PlacedFeature> PINK_SALT_NOISE_CEILING_PATCH = registerPlacedFeature("pink_salt_noise_ceiling_patch");
    public static final ResourceKey<PlacedFeature> PINK_SALT_STRAW_CEILING_PATCH = registerPlacedFeature("pink_salt_straw_ceiling_patch");
    public static final ResourceKey<PlacedFeature> PINK_SALT_STRAW_FLOOR_PATCH = registerPlacedFeature("pink_salt_straw_floor_patch");
    public static final ResourceKey<PlacedFeature> OASIS = registerPlacedFeature("oasis");
    public static final ResourceKey<PlacedFeature> BERSERKER = registerPlacedFeature("mobs/berserker");

    public static void bootstrap(BootstapContext<PlacedFeature> bootstapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstapContext.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(bootstapContext, LARGE_CEILING_ALLURITE_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR), CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LARGE_CEILING_LUMIERE_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING), CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, ALLURITE_CEILING_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.ALLURITE_CRYSTAL_CEILING), CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LUMIERE_CEILING_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.LUMIERE_CRYSTAL_CEILING), CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LARGE_FLOOR_ALLURITE_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR), CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LARGE_FLOOR_LUMIERE_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR), CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, ALLURITE_FLOOR_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR), CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LUMIERE_FLOOR_CRYSTALS, holderGetter.getOrThrow(GConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR), CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, ORE_SILVER_SMALL, holderGetter.getOrThrow(GConfiguredFeatures.ORE_SILVER_SMALL), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48))));
        PlacementUtils.register(bootstapContext, BOWL_LICHEN, holderGetter.getOrThrow(GConfiguredFeatures.BOWL_LICHEN), CountPlacement.of(35), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LICHEN_VEGETATION, holderGetter.getOrThrow(GConfiguredFeatures.LICHEN_PATCH), CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, GRAVEL_PATCH, holderGetter.getOrThrow(GConfiguredFeatures.GRAVEL_PATCH), CountPlacement.of(20), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, LICHEN_CORDYCEPS_COLUMN, holderGetter.getOrThrow(GConfiguredFeatures.LICHEN_CORDYCEPS), NoiseBasedCountPlacement.of(200, 4, -0.12), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, ORE_SILVER_LARGE, holderGetter.getOrThrow(GConfiguredFeatures.ORE_SILVER_LARGE), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
        PlacementUtils.register(bootstapContext, PINK_SALT_NOISE_GROUND_PATCH, holderGetter.getOrThrow(GConfiguredFeatures.PINK_SALT_GROUND_NOISE_PATCH), CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, PINK_SALT_NOISE_CEILING_PATCH, holderGetter.getOrThrow(GConfiguredFeatures.PINK_SALT_CEILING_NOISE_PATCH), CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, PINK_SALT_STRAW_CEILING_PATCH, holderGetter.getOrThrow(GConfiguredFeatures.PINK_SALT_STRAW_CEILING_PATCH), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, RandomOffsetPlacement.of(ClampedNormalInt.of(0.0f, 3.0f, -10, 10), ClampedNormalInt.of(0.0f, 0.6f, -2, 2)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, PINK_SALT_STRAW_FLOOR_PATCH, holderGetter.getOrThrow(GConfiguredFeatures.PINK_SALT_STRAW_FLOOR_PATCH), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, RandomOffsetPlacement.of(ClampedNormalInt.of(0.0f, 3.0f, -10, 10), ClampedNormalInt.of(0.0f, 0.6f, -2, 2)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, OASIS, holderGetter.getOrThrow(GConfiguredFeatures.OASIS), CountPlacement.of(50), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesTag(GBlockTags.OASIS_GENERATE_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, BERSERKER, holderGetter.getOrThrow(GConfiguredFeatures.BERSERKER));
    }

    public static ResourceKey<PlacedFeature> registerPlacedFeature(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Galosphere.MODID, id));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

}
