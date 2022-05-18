package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GDataGenerator {

    private GDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();


        dataGenerator.addProvider(new GBlockTagsProvider(dataGenerator, helper));
//        dataGenerator.addProvider(new CTBlockstateProvider(dataGenerator, helper));
//        dataGenerator.addProvider(new CTItemModelProvider(dataGenerator, helper));
        dataGenerator.addProvider(new GRecipeProvider(dataGenerator));
        dataGenerator.addProvider(new GLootTableProvider(dataGenerator));
    }

}
