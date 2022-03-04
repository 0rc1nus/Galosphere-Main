package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
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
import net.orcinus.cavesandtrenches.CavesAndTrenches;

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
    public static final Holder<PlacedFeature> ORE_SILVER_UPPER = registerPlacedFeature("ore_silver_upper", CTConfiguredFeatures.ORE_SILVER, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
    public static final Holder<PlacedFeature> ORE_SILVER_MIDDLE = registerPlacedFeature("ore_silver_middle", CTConfiguredFeatures.ORE_SILVER, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
    public static final Holder<PlacedFeature> ORE_SILVER_SMALL = registerPlacedFeature("ore_silver_small", CTConfiguredFeatures.ORE_SILVER_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72))));
    public static final Holder<PlacedFeature> STIFFENED_VINES = registerPlacedFeature("stiffened_vines", CTConfiguredFeatures.STIFFINED_ROOF, CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> FLUTTERED_LEAF = registerPlacedFeature("fluttered_leaf", CTConfiguredFeatures.FLUTTERED_LEAF, CountPlacement.of(160), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

    public static Holder<PlacedFeature> registerPlacedFeature(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
        return BuiltinRegistries.m_206380_(BuiltinRegistries.PLACED_FEATURE, CavesAndTrenches.MODID + ":" + string, new PlacedFeature(Holder.m_205706_(holder), List.copyOf(list)));
    }

    public static Holder<PlacedFeature> registerPlacedFeature(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, PlacementModifier ... placementModifiers) {
        return registerPlacedFeature(string, holder, List.of(placementModifiers));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

}
