package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

import java.util.List;

public class CTPlacedFeatures {

    //Large Spike 180 212
    //Normal Spike 256 324
    public static final PlacedFeature LARGE_CEILING_ALLURITE_CRYSTALS = registerPlacedFeature("large_ceiling_allurite_crystals", CTConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_CEILING.placed(CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LARGE_CEILING_LUMIERE_CRYSTALS = registerPlacedFeature("large_ceiling_lumiere_crystals", CTConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING.placed(CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature ALLURITE_CEILING_CRYSTALS = registerPlacedFeature("allurite_ceiling_crystals", CTConfiguredFeatures.ALLURITE_CRYSTAL_CEILING.placed(CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LUMIERE_CEILING_CRYSTALS = registerPlacedFeature("lumiere_ceiling_crystals", CTConfiguredFeatures.LUMIERE_CRYSTAL_CEILING.placed(CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LARGE_FLOOR_ALLURITE_CRYSTALS = registerPlacedFeature("large_floor_allurite_crystals", CTConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR.placed(CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LARGE_FLOOR_LUMIERE_CRYSTALS = registerPlacedFeature("large_floor_lumiere_crystals", CTConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR.placed(CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature ALLURITE_FLOOR_CRYSTALS = registerPlacedFeature("allurite_floor_crystals", CTConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR.placed(CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature LUMIERE_FLOOR_CRYSTALS = registerPlacedFeature("lumiere_floor_crystals", CTConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR.placed(CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature MYSTERIA_TREE = registerPlacedFeature("mysteria_tree", CTConfiguredFeatures.MYSTERIA_TREE.placed(CountPlacement.of(UniformInt.of(4, 7)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature ORE_SILVER_UPPER = registerPlacedFeature("ore_silver_upper", CTConfiguredFeatures.ORE_SILVER.placed(commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)))));
    public static final PlacedFeature ORE_SILVER_MIDDLE = registerPlacedFeature("ore_silver_middle", CTConfiguredFeatures.ORE_SILVER.placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature ORE_SILVER_SMALL = registerPlacedFeature("ore_silver_small", CTConfiguredFeatures.ORE_SILVER_SMALL.placed(commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)))));
    public static final PlacedFeature STIFFENED_VINES = registerPlacedFeature("stiffened_vines", CTConfiguredFeatures.STIFFINED_ROOF.placed(CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome()));
    public static final PlacedFeature FLUTTERED_LEAF = registerPlacedFeature("fluttered_leaf", CTConfiguredFeatures.FLUTTERED_LEAF.placed(CountPlacement.of(160), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));

    public static <P extends PlacedFeature> P registerPlacedFeature(String key, P configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CavesAndTrenches.MODID, key);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

}
