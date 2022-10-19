package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBiomes;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GBiomeTagsProvider extends BiomeTagsProvider {

    public GBiomeTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        GBiomes.BIOMES.getEntries().stream().map(Supplier::get).forEach(this::addBiomeTag);
    }

    public void addBiomeTag(Biome biome) {
        this.tag(BiomeTags.HAS_MINESHAFT).add(biome);
        this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(biome);
        this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(biome);
        this.tag(BiomeTags.IS_OVERWORLD).add(biome);
        this.tag(Tags.Biomes.IS_UNDERGROUND).add(biome);
    }

}
