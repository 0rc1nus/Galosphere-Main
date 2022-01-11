package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

import java.util.List;

public class CTPlacedFeatures {

    public static final PlacedFeature LARGE_ALLURITE_CRYSTAL_CEILING = registerPlacedFeature("large_allurite_crystal_ceiling", CTConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_CEILING.placed(CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LARGE_LUMIERE_CRYSTAL_CEILING = registerPlacedFeature("large_lumiere_crystal_ceiling", CTConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING.placed(CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LARGE_ALLURITE_CRYSTAL_FLOOR = registerPlacedFeature("large_allurite_crystal_floor", CTConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR.placed(CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LARGE_LUMIERE_CRYSTAL_FLOOR = registerPlacedFeature("large_lumiere_crystal_floor", CTConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR.placed(CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature MYSTERIA_TREE = registerPlacedFeature("mysteria_tree", CTConfiguredFeatures.MYSTERIA_TREE.placed(CountPlacement.of(UniformInt.of(4, 7)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature ORE_SILVER_UPPER = registerPlacedFeature("ore_silver_upper", CTConfiguredFeatures.ORE_SILVER.placed(commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)))));
    public static final PlacedFeature ORE_SILVER_MIDDLE = registerPlacedFeature("ore_silver_middle", CTConfiguredFeatures.ORE_SILVER.placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature ORE_SILVER_SMALL = registerPlacedFeature("ore_silver_small", CTConfiguredFeatures.ORE_SILVER_SMALL.placed(commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)))));

    public static <P extends PlacedFeature> P registerPlacedFeature(String key, P configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CavesAndTrenches.MODID, key);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

}
