package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GBiomeModifier {

    public static void init() {
//        addOres();
    }

    public static void addOres() {
        Util.make(ImmutableList.<ResourceKey<PlacedFeature>>builder(), list -> {
            list.add(GPlacedFeatures.ORE_SILVER_MIDDLE);
            list.add(GPlacedFeatures.ORE_SILVER_SMALL);
        }).build().forEach(featureHolder -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, featureHolder));
    }

    public static void addCrystalCanyonsFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
    }

    public static void addCrystalCanyonsSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        builder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(GEntityTypes.SPARKLE, 10, 4, 6));
    }

    public static void addLichenCavesFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.BOWL_LICHEN);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LICHEN_VEGETATION);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LICHEN_CORDYCEPS_COLUMN);
    }

    public static void addLichenCavesSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(GEntityTypes.SPECTRE, 20, 8, 8));
    }

}
