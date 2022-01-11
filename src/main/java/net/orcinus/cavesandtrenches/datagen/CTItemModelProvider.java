package net.orcinus.cavesandtrenches.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

public class CTItemModelProvider extends ItemModelProvider {

    public CTItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CavesAndTrenches.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
