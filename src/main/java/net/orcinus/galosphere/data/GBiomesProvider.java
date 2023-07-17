package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GPlacedFeatures;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class GBiomesProvider extends FabricDynamicRegistryProvider {

    public GBiomesProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        add(registries, entries, GBiomes.CRYSTAL_CANYONS);
        add(registries, entries, GBiomes.LICHEN_CAVES);
        add(registries, entries, GBiomes.PINK_SALT_CAVES);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<Biome> resourceKey) {
        final HolderLookup.RegistryLookup<Biome> configuredFeatureRegistryLookup = registries.lookupOrThrow(Registries.BIOME);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }

    @Override
    public String getName() {
        return "worldgen/biome";
    }
}
