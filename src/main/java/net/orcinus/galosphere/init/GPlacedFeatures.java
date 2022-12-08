package net.orcinus.galosphere.init;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.orcinus.galosphere.Galosphere;

import java.util.List;

public class GPlacedFeatures {

    public static void init() { }

    public static final ResourceKey<PlacedFeature> LARGE_CEILING_ALLURITE_CRYSTALS = createKey("large_ceiling_allurite_crystals");
    public static final ResourceKey<PlacedFeature> LARGE_CEILING_LUMIERE_CRYSTALS = createKey("large_ceiling_lumiere_crystals");
    public static final ResourceKey<PlacedFeature> ALLURITE_CEILING_CRYSTALS = createKey("allurite_ceiling_crystals");
    public static final ResourceKey<PlacedFeature> LUMIERE_CEILING_CRYSTALS = createKey("lumiere_ceiling_crystals");
    public static final ResourceKey<PlacedFeature> LARGE_FLOOR_ALLURITE_CRYSTALS = createKey("large_floor_allurite_crystals");
    public static final ResourceKey<PlacedFeature> LARGE_FLOOR_LUMIERE_CRYSTALS = createKey("large_floor_lumiere_crystals");
    public static final ResourceKey<PlacedFeature> ALLURITE_FLOOR_CRYSTALS = createKey("allurite_floor_crystals");
    public static final ResourceKey<PlacedFeature> LUMIERE_FLOOR_CRYSTALS = createKey("lumiere_floor_crystals");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_MIDDLE = createKey("ore_silver_middle");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_SMALL = createKey("ore_silver_small");
    public static final ResourceKey<PlacedFeature> BOWL_LICHEN = createKey("bowl_lichen");
    public static final ResourceKey<PlacedFeature> LICHEN_VEGETATION = createKey("lichen_caves_vegetation");
    public static final ResourceKey<PlacedFeature> LICHEN_CORDYCEPS_COLUMN = createKey("lichen_cordyceps_column");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        register(context, LARGE_CEILING_ALLURITE_CRYSTALS, GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, LARGE_CEILING_LUMIERE_CRYSTALS, GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, ALLURITE_CEILING_CRYSTALS, GConfiguredFeatures.ALLURITE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, LUMIERE_CEILING_CRYSTALS, GConfiguredFeatures.LUMIERE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, LARGE_FLOOR_ALLURITE_CRYSTALS, GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, LARGE_FLOOR_LUMIERE_CRYSTALS, GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, ALLURITE_FLOOR_CRYSTALS, GConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, LUMIERE_FLOOR_CRYSTALS, GConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        register(context, ORE_SILVER_MIDDLE, GConfiguredFeatures.ORE_SILVER, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(8))));
        register(context, ORE_SILVER_SMALL, GConfiguredFeatures.ORE_SILVER_SMALL, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48))));
        register(context, BOWL_LICHEN, GConfiguredFeatures.BOWL_LICHEN, CountPlacement.of(35), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        register(context, LICHEN_VEGETATION, GConfiguredFeatures.LICHEN_PATCH, CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        register(context, LICHEN_CORDYCEPS_COLUMN, GConfiguredFeatures.LICHEN_CORDYCEPS, CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());    
    }

    public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
        context.register(resourceKey, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredFeature), List.copyOf(placementModifiers)));
    }

    public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
        register(context, resourceKey, configuredFeature, List.of(placementModifiers));
    }

    public static ResourceKey<PlacedFeature> createKey(String string) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Galosphere.MODID, string));
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), modifier, BiomeFilter.biome());
    }

}
