package net.orcinus.galosphere.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.orcinus.galosphere.Galosphere;
import org.jetbrains.annotations.Nullable;

public class GBiomes {

    public static final ResourceKey<Biome> CRYSTAL_CANYONS = register("crystal_canyons");
    public static final ResourceKey<Biome> LICHEN_CAVES = register("lichen_caves");
    public static final ResourceKey<Biome> PINK_SALT_CAVES = register("pink_salt_caves");

    public static void bootstrap(BootstapContext<Biome> bootstapContext) {
        HolderGetter<PlacedFeature> holderGetter = bootstapContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = bootstapContext.lookup(Registries.CONFIGURED_CARVER);
        bootstapContext.register(CRYSTAL_CANYONS, crystalCanyons(holderGetter, holderGetter2));
        bootstapContext.register(LICHEN_CAVES, lichenCaves(holderGetter, holderGetter2));
        bootstapContext.register(PINK_SALT_CAVES, pinkSaltCaves(holderGetter, holderGetter2));
    }

    public static Biome pinkSaltCaves(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.PINK_SALT_NOISE_GROUND_PATCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.PINK_SALT_NOISE_CEILING_PATCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.PINK_SALT_STRAW_CEILING_PATCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.PINK_SALT_STRAW_FLOOR_PATCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.OASIS);
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_LICHEN_CAVES);
        return biome(true, 0.5f, 0.5f, mobBuilder, biomeBuilder, music);
    }

    public static Biome lichenCaves(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.BOWL_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.GRAVEL_PATCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LICHEN_VEGETATION);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LICHEN_CORDYCEPS_COLUMN);
        mobBuilder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(GEntityTypes.SPECTRE, 20, 8, 8));
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_LICHEN_CAVES);
        return biome(true, 0.5f, 0.5f, mobBuilder, biomeBuilder, music);
    }

    public static Biome crystalCanyons(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
        biomeBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GPlacedFeatures.ORE_SILVER_LARGE);
        mobBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        mobBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(GEntityTypes.SPARKLE, 10, 4, 6));
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_CRYSTAL_CANYONS);
        return biome(true, 0.5f, 0.5f, mobBuilder, biomeBuilder, music);
    }

    private static Biome biome(boolean bl, float f, float g, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, @org.jetbrains.annotations.Nullable Music music) {
        return biome(bl, f, g, 4159204, 329011, null, null, builder, builder2, music);
    }

    private static Biome biome(boolean bl, float f, float g, int i, int j, @org.jetbrains.annotations.Nullable Integer integer, @org.jetbrains.annotations.Nullable Integer integer2, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, @Nullable Music music) {
        BiomeSpecialEffects.Builder builder3 = new BiomeSpecialEffects.Builder().waterColor(i).waterFogColor(j).fogColor(12638463).skyColor(OverworldBiomes.calculateSkyColor(f)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music);
        if (integer != null) {
            builder3.grassColorOverride(integer);
        }
        if (integer2 != null) {
            builder3.foliageColorOverride(integer2);
        }
        return new Biome.BiomeBuilder().hasPrecipitation(bl).temperature(f).downfall(g).specialEffects(builder3.build()).mobSpawnSettings(builder.build()).generationSettings(builder2.build()).build();
    }

    private static ResourceKey<Biome> register(String string) {
        return ResourceKey.create(Registries.BIOME, Galosphere.id(string));
    }
}