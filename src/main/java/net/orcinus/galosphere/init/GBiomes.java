package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.orcinus.galosphere.Galosphere;

import javax.annotation.Nullable;
import java.util.Map;

public class GBiomes {
    private static final Map<Biome, ResourceKey<Biome>> BIOME_KEYS = Maps.newLinkedHashMap();

    public static final Biome CRYSTAL_CANYONS = GBiomes.crystalCanyons();
    public static final ResourceKey<Biome> CRYSTAL_CANYONS_KEY = register("crystal_canyons", CRYSTAL_CANYONS);

    public static Biome crystalCanyons() {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(0.5F, 0.5F, mobBuilder, biomeBuilder, music, 4445678);
    }

    private static Biome biome(float temperature, float downFall, MobSpawnSettings.Builder mobBuilder, BiomeGenerationSettings.Builder generationBuilder, @Nullable Music music, int waterColor) {
        return biome(temperature, downFall, waterColor, 329011, mobBuilder, generationBuilder, music);
    }

    private static Biome biome(float temperature, float downFall, int waterColor, int p_194857_, MobSpawnSettings.Builder mobBuilder, BiomeGenerationSettings.Builder generationBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).biomeCategory(Biome.BiomeCategory.UNDERGROUND).precipitation(Biome.Precipitation.RAIN).temperature(temperature).downfall(downFall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(p_194857_).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(mobBuilder.build()).generationSettings(generationBuilder.build()).build();
    }

    protected static int calculateSkyColor(float temperature) {
        float value = temperature / 3.0F;
        value = Mth.clamp(value, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - value * 0.05F, 0.5F + value * 0.1F, 1.0F);
    }

    private static ResourceKey<Biome> register(String string, Biome biome) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Galosphere.MODID, string));
        BIOME_KEYS.put(biome, key);
        return key;
    }

    public static void init() {
        for (Biome biome : BIOME_KEYS.keySet()) {
            Registry.register(BuiltinRegistries.BIOME, BIOME_KEYS.get(biome).location(), biome);
        }
    }

}
