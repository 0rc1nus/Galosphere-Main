package net.orcinus.galosphere.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBiomes;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GBiomeTagsProvider extends BiomeTagsProvider {

    public GBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }

    public void addBiomeTag(ResourceKey<Biome> biome) {
        this.tag(BiomeTags.HAS_MINESHAFT).add(biome);
        this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(biome);
        this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(biome);
        this.tag(BiomeTags.IS_OVERWORLD).add(biome);
        this.tag(Tags.Biomes.IS_UNDERGROUND).add(biome);
    }

}
