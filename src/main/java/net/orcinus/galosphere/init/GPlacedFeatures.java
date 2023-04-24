package net.orcinus.galosphere.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
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

    public static void init() {
    }

    public static final Holder<PlacedFeature> LARGE_CEILING_ALLURITE_CRYSTALS = registerPlacedFeature("large_ceiling_allurite_crystals", GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_CEILING_LUMIERE_CRYSTALS = registerPlacedFeature("large_ceiling_lumiere_crystals", GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ALLURITE_CEILING_CRYSTALS = registerPlacedFeature("allurite_ceiling_crystals", GConfiguredFeatures.ALLURITE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LUMIERE_CEILING_CRYSTALS = registerPlacedFeature("lumiere_ceiling_crystals", GConfiguredFeatures.LUMIERE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_FLOOR_ALLURITE_CRYSTALS = registerPlacedFeature("large_floor_allurite_crystals", GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_FLOOR_LUMIERE_CRYSTALS = registerPlacedFeature("large_floor_lumiere_crystals", GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ALLURITE_FLOOR_CRYSTALS = registerPlacedFeature("allurite_floor_crystals", GConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LUMIERE_FLOOR_CRYSTALS = registerPlacedFeature("lumiere_floor_crystals", GConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ORE_SILVER_LARGE = registerPlacedFeature("ore_silver_large", GConfiguredFeatures.ORE_SILVER_LARGE, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(8))));
    public static final Holder<PlacedFeature> ORE_SILVER_SMALL = registerPlacedFeature("ore_silver_small", GConfiguredFeatures.ORE_SILVER_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48))));
    public static final Holder<PlacedFeature> BOWL_LICHEN = registerPlacedFeature("bowl_lichen", GConfiguredFeatures.BOWL_LICHEN, CountPlacement.of(35), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> LICHEN_VEGETATION = registerPlacedFeature("lichen_caves_vegetation", GConfiguredFeatures.LICHEN_PATCH, CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> LICHEN_CORDYCEPS_COLUMN = registerPlacedFeature("lichen_cordyceps_column", GConfiguredFeatures.LICHEN_CORDYCEPS, CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> registerPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        return registerPlacedFeature(id, feature, List.of(placementModifiers));
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> registerPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
        ResourceLocation resourceLocation = new ResourceLocation(Galosphere.MODID, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Placed Feature ID: \"" + resourceLocation + "\" already exists in the Placed Features registry!");

        PlacedFeature placedFeature = new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placementModifiers));

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, resourceLocation, placedFeature);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

}
