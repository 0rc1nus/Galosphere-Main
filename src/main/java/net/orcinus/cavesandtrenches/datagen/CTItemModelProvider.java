package net.orcinus.cavesandtrenches.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

public class CTItemModelProvider extends ItemModelProvider {

    public CTItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CavesAndTrenches.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("amethyst_stairs", new ResourceLocation(CavesAndTrenches.MODID, "amethyst_stairs"));
        withExistingParent("amethyst_slab", new ResourceLocation(CavesAndTrenches.MODID, "amethyst_slab"));
    }
}
