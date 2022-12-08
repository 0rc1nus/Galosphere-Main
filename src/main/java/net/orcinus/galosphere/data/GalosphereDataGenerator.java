package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GConfiguredFeatures;
import net.orcinus.galosphere.init.GPlacedFeatures;

public class GalosphereDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(GalosphereConfiguredFeatureProvider::new);
        pack.addProvider(GalospherePlacedFeatureProvider::new);
        pack.addProvider(GalosphereBiomeProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.BIOME, GBiomes::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, GConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, GPlacedFeatures::bootstrap);
    }

}
