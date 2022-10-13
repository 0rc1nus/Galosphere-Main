package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GBiomeModifier {

    public static void init() {
        addOres();
        addCrystalCanyonsFeatures();
        addCrystalCanyonsSpawns();
        addLichenCavesFeatures();
        addLichenCavesSpawns();
    }

    public static void addOres() {
        Util.make(ImmutableList.<Holder<PlacedFeature>>builder(), list -> {
            list.add(GPlacedFeatures.ORE_SILVER_MIDDLE);
            list.add(GPlacedFeatures.ORE_SILVER_SMALL);
        }).build().forEach(featureHolder -> featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, placedFeatureResourceKey)));
    }

    public static void addCrystalCanyonsFeatures() {
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
        addFeature(GBiomes.CRYSTAL_CANYONS_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
    }

    public static void addCrystalCanyonsSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), MobCategory.UNDERGROUND_WATER_CREATURE, GEntityTypes.SPARKLE, 10, 4, 6);
    }

    public static void addLichenCavesFeatures() {
        addFeature(GBiomes.LICHEN_CAVES_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.BOWL_LICHEN);
        addFeature(GBiomes.LICHEN_CAVES_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LICHEN_VEGETATION);
        addFeature(GBiomes.LICHEN_CAVES_KEY, GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LICHEN_CORDYCEPS_COLUMN);
    }

    public static void addLichenCavesSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(GBiomes.LICHEN_CAVES_KEY), MobCategory.UNDERGROUND_WATER_CREATURE, GEntityTypes.SPECTRE, 20, 8, 8);
    }

    public static void addFeature(ResourceKey<Biome> biomeResourceKey, GenerationStep.Decoration decoration, Holder<PlacedFeature> featureHolder) {
        featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.includeByKey(biomeResourceKey), decoration, placedFeatureResourceKey));
    }

}
