package net.orcinus.galosphere.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.orcinus.galosphere.Galosphere;

import javax.annotation.Nullable;

public class GBiomes {

    public static final ResourceKey<Biome> CRYSTAL_CANYONS_KEY = register("crystal_canyons");
    public static final ResourceKey<Biome> LICHEN_CAVES_KEY = register("lichen_caves");

    public static Biome lichenCaves(BootstapContext<Biome> bootstapContext) {
        HolderGetter<PlacedFeature> holderGetter = bootstapContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = bootstapContext.lookup(Registries.CONFIGURED_CARVER);
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        GBiomeModifier.addLichenCavesSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        GBiomeModifier.addLichenCavesFeatures(biomeBuilder);
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_LICHEN_CAVES);
        return biome(0.5F, 0.5F, mobBuilder, biomeBuilder, music, 4159204);
    }

    public static Biome crystalCanyons(BootstapContext<Biome> bootstapContext) {
        HolderGetter<PlacedFeature> holderGetter = bootstapContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = bootstapContext.lookup(Registries.CONFIGURED_CARVER);
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        GBiomeModifier.addCrystalCanyonsSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        GBiomeModifier.addCrystalCanyonsFeatures(biomeBuilder);
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_CRYSTAL_CANYONS);
        return biome(0.5F, 0.5F, mobBuilder, biomeBuilder, music, 4445678);
    }

    private static Biome biome(float temperature, float downFall, MobSpawnSettings.Builder mobBuilder, BiomeGenerationSettings.Builder generationBuilder, @Nullable Music music, int waterColor) {
        return biome(temperature, downFall, waterColor, 329011, mobBuilder, generationBuilder, music);
    }

    private static Biome biome(float temperature, float downFall, int waterColor, int p_194857_, MobSpawnSettings.Builder mobBuilder, BiomeGenerationSettings.Builder generationBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).temperature(temperature).downfall(downFall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(p_194857_).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(mobBuilder.build()).generationSettings(generationBuilder.build()).build();
    }

    protected static int calculateSkyColor(float temperature) {
        float value = temperature / 3.0F;
        value = Mth.clamp(value, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - value * 0.05F, 0.5F + value * 0.1F, 1.0F);
    }

    private static ResourceKey<Biome> register(String string) {
        return ResourceKey.create(Registries.BIOME, Galosphere.id(string));
    }

    public static void bootstrap(BootstapContext<Biome> biomeRegisterable) {
        biomeRegisterable.register(CRYSTAL_CANYONS_KEY, GBiomes.crystalCanyons(biomeRegisterable));
        biomeRegisterable.register(LICHEN_CAVES_KEY, GBiomes.lichenCaves(biomeRegisterable));
    }
}