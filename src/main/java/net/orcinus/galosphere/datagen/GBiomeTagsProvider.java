package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBiomes;
import org.jetbrains.annotations.Nullable;

public class GBiomeTagsProvider extends BiomeTagsProvider {

    public GBiomeTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for (RegistryObject<Biome> biome : GBiomes.BIOMES.getEntries()) {
            Biome value = biome.get();
            this.tag(BiomeTags.HAS_MINESHAFT).add(value);
            this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(value);
            this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(value);
            this.tag(BiomeTags.IS_OVERWORLD).add(value);
            this.tag(Tags.Biomes.IS_UNDERGROUND).add(value);
        }
    }
}
