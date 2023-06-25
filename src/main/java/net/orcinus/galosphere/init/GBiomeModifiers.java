package net.orcinus.galosphere.init;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.galosphere.Galosphere;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CRYSTAL_CANYONS_FEATURES = register("add_crystal_canyons_features");
    public static final ResourceKey<BiomeModifier> ADD_CRYSTAL_CANYONS_SPAWNS = register("add_crystal_canyons_spawns");
    public static final ResourceKey<BiomeModifier> ADD_LICHEN_CAVES_FEATURES = register("add_lichen_caves_spawns");
    public static final ResourceKey<BiomeModifier> ADD_LICHEN_CAVES_SPAWNS = register("add_lichen_caves_features");
    public static final ResourceKey<BiomeModifier> ADD_SILVER_ORES = register("add_silver_ores");
    public static final ResourceKey<BiomeModifier> ADD_LARGE_SILVER_ORES = register("add_large_silver_ores");

    public static void bootstrap(BootstapContext<BiomeModifier> bootstapContext) {
        bootstapContext.register(ADD_CRYSTAL_CANYONS_FEATURES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(getBiome(bootstapContext, GBiomes.CRYSTAL_CANYONS), getPlacedFeature(bootstapContext, GPlacedFeatures.ALLURITE_CEILING_CRYSTALS, GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS, GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS, GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS, GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS, GPlacedFeatures.LUMIERE_CEILING_CRYSTALS, GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS, GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS), GenerationStep.Decoration.VEGETAL_DECORATION));
        bootstapContext.register(ADD_CRYSTAL_CANYONS_SPAWNS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(getBiome(bootstapContext, GBiomes.CRYSTAL_CANYONS), List.of(new MobSpawnSettings.SpawnerData(GEntityTypes.SPARKLE.get(), 30, 4, 6), new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6))));
        bootstapContext.register(ADD_LICHEN_CAVES_FEATURES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(getBiome(bootstapContext, GBiomes.LICHEN_CAVES), getPlacedFeature(bootstapContext, GPlacedFeatures.BOWL_LICHEN, GPlacedFeatures.LICHEN_VEGETATION, GPlacedFeatures.LICHEN_CORDYCEPS_COLUMN, GPlacedFeatures.GRAVEL_PATCH), GenerationStep.Decoration.VEGETAL_DECORATION));
        bootstapContext.register(ADD_LICHEN_CAVES_SPAWNS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(getBiome(bootstapContext, GBiomes.LICHEN_CAVES), List.of(new MobSpawnSettings.SpawnerData(GEntityTypes.SPECTRE.get(), 20, 8, 8))));
        bootstapContext.register(ADD_SILVER_ORES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(getBiome(bootstapContext, BiomeTags.IS_OVERWORLD), getPlacedFeature(bootstapContext, GPlacedFeatures.ORE_SILVER_SMALL), GenerationStep.Decoration.UNDERGROUND_ORES));
        bootstapContext.register(ADD_LARGE_SILVER_ORES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(getBiome(bootstapContext, GBiomes.CRYSTAL_CANYONS), getPlacedFeature(bootstapContext, GPlacedFeatures.ORE_SILVER_LARGE), GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    @NotNull
    private static HolderSet.Direct<Biome> getBiome(BootstapContext<BiomeModifier> bootstapContext, ResourceKey<Biome> biome) {
        return HolderSet.direct(bootstapContext.lookup(Registries.BIOME).getOrThrow(biome));
    }

    private static HolderSet.Named<Biome> getBiome(BootstapContext<BiomeModifier> bootstapContext, TagKey<Biome> biome) {
        return bootstapContext.lookup(Registries.BIOME).getOrThrow(biome);
    }

    @SafeVarargs
    @NotNull
    private static HolderSet.Direct<PlacedFeature> getPlacedFeature(BootstapContext<BiomeModifier> context, ResourceKey<PlacedFeature>... placedFeature) {
        return HolderSet.direct(Stream.of(placedFeature).map(resourceKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(resourceKey)).collect(Collectors.toList()));
    }

    @NotNull
    private static HolderSet.Direct<PlacedFeature> getPlacedFeature(BootstapContext<BiomeModifier> context, ResourceKey<PlacedFeature> placedFeature) {
        return HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeature));
    }

    @NotNull
    private static ResourceKey<BiomeModifier> register(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Galosphere.MODID, name));
    }

}
