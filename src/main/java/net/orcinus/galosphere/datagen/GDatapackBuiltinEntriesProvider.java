package net.orcinus.galosphere.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBiomeModifiers;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GConfiguredFeatures;
import net.orcinus.galosphere.init.GPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, GConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, GPlacedFeatures::bootstrap)
            .add(Registries.BIOME, GBiomes::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, GBiomeModifiers::bootstrap);

    public GDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Galosphere.MODID));
    }

}
