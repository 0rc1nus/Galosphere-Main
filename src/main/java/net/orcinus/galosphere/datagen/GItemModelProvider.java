package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;

public class GItemModelProvider extends ItemModelProvider {

    public GItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("allurite_stairs", new ResourceLocation(Galosphere.MODID, "allurite_stairs"));
        withExistingParent("allurite_slab", new ResourceLocation(Galosphere.MODID, "allurite_slab"));
        withExistingParent("lumiere_stairs", new ResourceLocation(Galosphere.MODID, "lumiere_stairs"));
        withExistingParent("lumiere_slab", new ResourceLocation(Galosphere.MODID, "lumiere_slab"));
    }
}
