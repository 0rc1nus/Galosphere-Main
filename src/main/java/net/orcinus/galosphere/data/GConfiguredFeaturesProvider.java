package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.orcinus.galosphere.init.GConfiguredFeatures;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class GConfiguredFeaturesProvider extends FabricDynamicRegistryProvider {

    public GConfiguredFeaturesProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        add(registries, entries, GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_FLOOR);
        add(registries, entries, GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_FLOOR);
        add(registries, entries, GConfiguredFeatures.LARGE_ALLURITE_CRYSTAL_CEILING);
        add(registries, entries, GConfiguredFeatures.LARGE_LUMIERE_CRYSTAL_CEILING);
        add(registries, entries, GConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR);
        add(registries, entries, GConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR);
        add(registries, entries, GConfiguredFeatures.ALLURITE_CRYSTAL_CEILING);
        add(registries, entries, GConfiguredFeatures.LUMIERE_CRYSTAL_CEILING);
        add(registries, entries, GConfiguredFeatures.ORE_SILVER_SMALL);
        add(registries, entries, GConfiguredFeatures.ORE_SILVER_LARGE);
        add(registries, entries, GConfiguredFeatures.BOWL_LICHEN);
        add(registries, entries, GConfiguredFeatures.LICHEN_VEGETATION);
        add(registries, entries, GConfiguredFeatures.LICHEN_PATCH);
        add(registries, entries, GConfiguredFeatures.GRAVEL_PATCH);
        add(registries, entries, GConfiguredFeatures.LICHEN_CORDYCEPS);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey) {
        final HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryLookup = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }

    @Override
    public String getName() {
        return "worldgen/configured_feature";
    }
}
