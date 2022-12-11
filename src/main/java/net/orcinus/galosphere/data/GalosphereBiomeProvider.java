package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.orcinus.galosphere.init.GBiomes;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class GalosphereBiomeProvider extends FabricDynamicRegistryProvider {

    public GalosphereBiomeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        add(registries, entries, GBiomes.CRYSTAL_CANYONS);
        add(registries, entries, GBiomes.LICHEN_CAVES);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<Biome> biome) {
        final HolderLookup.RegistryLookup<Biome> biomeRegistry = registries.lookupOrThrow(Registries.BIOME);

        entries.add(biome, biomeRegistry.getOrThrow(biome).value());
    }

    @Override
    public String getName() {
        return "worldgen/biome";
    }
}
