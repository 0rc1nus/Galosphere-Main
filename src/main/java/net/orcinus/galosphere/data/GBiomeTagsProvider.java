package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.orcinus.galosphere.init.GBiomeTags;
import net.orcinus.galosphere.init.GBiomes;

import java.util.concurrent.CompletableFuture;

public class GBiomeTagsProvider extends FabricTagProvider<Biome> {
    public GBiomeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.getOrCreateTagBuilder(BiomeTags.STRONGHOLD_BIASED_TO).add(GBiomes.CRYSTAL_CANYONS, GBiomes.LICHEN_CAVES, GBiomes.PINK_SALT_CAVES);
        this.getOrCreateTagBuilder(BiomeTags.HAS_MINESHAFT).add(GBiomes.CRYSTAL_CANYONS, GBiomes.LICHEN_CAVES, GBiomes.PINK_SALT_CAVES);
        this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(GBiomes.CRYSTAL_CANYONS, GBiomes.LICHEN_CAVES, GBiomes.PINK_SALT_CAVES);
        this.getOrCreateTagBuilder(GBiomeTags.HAS_PINK_SALT_SHRINE).add(GBiomes.PINK_SALT_CAVES);
    }
}
