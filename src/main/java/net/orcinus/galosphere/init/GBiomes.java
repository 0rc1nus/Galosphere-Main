package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.orcinus.galosphere.Galosphere;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class GBiomes {
    private static final Map<ResourceKey<Biome>, ResourceLocation> VALUES = Maps.newLinkedHashMap();

    public static final ResourceKey<Biome> CRYSTAL_CANYONS = register("crystal_canyons");
    public static final ResourceKey<Biome> LICHEN_CAVES = register("lichen_caves");
    public static final ResourceKey<Biome> PINK_SALT_CAVES = register("pink_salt_caves");

    public static void bootstrap(BootstapContext<Biome> bootstapContext) {
        HolderGetter<PlacedFeature> holdergetter = bootstapContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = bootstapContext.lookup(Registries.CONFIGURED_CARVER);
        bootstapContext.register(CRYSTAL_CANYONS, GBiomes.crystalCanyons(holdergetter, holdergetter1));
        bootstapContext.register(LICHEN_CAVES, GBiomes.lichenCaves(holdergetter, holdergetter1));
        bootstapContext.register(PINK_SALT_CAVES, GBiomes.pinkSaltCaves(holdergetter, holdergetter1));
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
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_LICHEN_CAVES.getHolder().get());
        return biome(0.5f, 0.5f, mobBuilder, biomeBuilder, music, 4159204);
    }

    public static Biome lichenCaves(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter1) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter1);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_LICHEN_CAVES.getHolder().get());
        return biome(0.5F, 0.5F, mobBuilder, biomeBuilder, music, 4159204);
    }

    public static Biome crystalCanyons(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter1) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter1);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(GSoundEvents.MUSIC_CRYSTAL_CANYONS.getHolder().get());
        return biome(0.5F, 0.5F, mobBuilder, biomeBuilder, music, 4445678);
    }

    private static Biome biome(float temperature, float downFall, MobSpawnSettings.Builder mobBuilder, BiomeGenerationSettings.Builder generationBuilder, @Nullable Music music, int waterColor) {
        return biome(temperature, downFall, waterColor, 329011, mobBuilder, generationBuilder, music);
    }

    private static Biome biome(float temperature, float downFall, int waterColor, int p_194857_, MobSpawnSettings.Builder mobBuilder, BiomeGenerationSettings.Builder generationBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).hasPrecipitation(true).temperature(temperature).downfall(downFall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(p_194857_).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(mobBuilder.build()).generationSettings(generationBuilder.build()).build();
    }

    protected static int calculateSkyColor(float temperature) {
        float value = temperature / 3.0F;
        value = Mth.clamp(value, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - value * 0.05F, 0.5F + value * 0.1F, 1.0F);
    }

    private static ResourceKey<Biome> register(String name) {
        ResourceLocation id = new ResourceLocation(Galosphere.MODID, name);
        ResourceKey<Biome> biomeResourceKey = ResourceKey.create(Registries.BIOME, id);
        VALUES.put(biomeResourceKey, id);
        return biomeResourceKey;
    }

    public static Collection<ResourceLocation> getIds() {
        return VALUES.values();
    }
}
