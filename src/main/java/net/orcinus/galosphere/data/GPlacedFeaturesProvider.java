package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.orcinus.galosphere.init.GPlacedFeatures;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class GPlacedFeaturesProvider extends FabricDynamicRegistryProvider {
    public GPlacedFeaturesProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        add(registries, entries, GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
        add(registries, entries, GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
        add(registries, entries, GPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
        add(registries, entries, GPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
        add(registries, entries, GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
        add(registries, entries, GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
        add(registries, entries, GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
        add(registries, entries, GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
        add(registries, entries, GPlacedFeatures.ORE_SILVER_SMALL);
        add(registries, entries, GPlacedFeatures.BOWL_LICHEN);
        add(registries, entries, GPlacedFeatures.LICHEN_VEGETATION);
        add(registries, entries, GPlacedFeatures.GRAVEL_PATCH);
        add(registries, entries, GPlacedFeatures.LICHEN_CORDYCEPS_COLUMN);
        add(registries, entries, GPlacedFeatures.ORE_SILVER_LARGE);
        add(registries, entries, GPlacedFeatures.PINK_SALT_NOISE_GROUND_PATCH);
        add(registries, entries, GPlacedFeatures.PINK_SALT_NOISE_CEILING_PATCH);
        add(registries, entries, GPlacedFeatures.PINK_SALT_STRAW_CEILING_PATCH);
        add(registries, entries, GPlacedFeatures.PINK_SALT_STRAW_FLOOR_PATCH);
        add(registries, entries, GPlacedFeatures.OASIS);
        add(registries, entries, GPlacedFeatures.BERSERKER);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<PlacedFeature> resourceKey) {
        final HolderLookup.RegistryLookup<PlacedFeature> configuredFeatureRegistryLookup = registries.lookupOrThrow(Registries.PLACED_FEATURE);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }

    @Override
    public String getName() {
        return "worldgen/placed_features";
    }
}
