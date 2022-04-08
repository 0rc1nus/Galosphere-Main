package net.orcinus.galosphere.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
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

public class CTPlacedFeatures {

    //Large Spike 180 212
    //Normal Spike 256 324
    public static final Holder<PlacedFeature> LARGE_CEILING_ALLURITE_CRYSTALS = registerPlacedFeature("large_ceiling_allurite_crystals", CTConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_CEILING_LUMIERE_CRYSTALS = registerPlacedFeature("large_ceiling_lumiere_crystals", CTConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ALLURITE_CEILING_CRYSTALS = registerPlacedFeature("allurite_ceiling_crystals", CTConfiguredFeatures.ALLURITE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LUMIERE_CEILING_CRYSTALS = registerPlacedFeature("lumiere_ceiling_crystals", CTConfiguredFeatures.LUMIERE_CRYSTAL_CEILING, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_FLOOR_ALLURITE_CRYSTALS = registerPlacedFeature("large_floor_allurite_crystals", CTConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_FLOOR_LUMIERE_CRYSTALS = registerPlacedFeature("large_floor_lumiere_crystals", CTConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ALLURITE_FLOOR_CRYSTALS = registerPlacedFeature("allurite_floor_crystals", CTConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LUMIERE_FLOOR_CRYSTALS = registerPlacedFeature("lumiere_floor_crystals", CTConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR, CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> MYSTERIA_TREE = registerPlacedFeature("mysteria_tree", CTConfiguredFeatures.MYSTERIA_TREE, CountPlacement.of(UniformInt.of(4, 7)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ORE_SILVER_MIDDLE = registerPlacedFeature("ore_silver_middle", CTConfiguredFeatures.ORE_SILVER, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
    public static final Holder<PlacedFeature> ORE_SILVER_SMALL = registerPlacedFeature("ore_silver_small", CTConfiguredFeatures.ORE_SILVER_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72))));

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
