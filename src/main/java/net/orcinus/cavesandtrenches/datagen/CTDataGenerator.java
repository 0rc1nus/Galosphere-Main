package net.orcinus.cavesandtrenches.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTDataGenerator {

    private CTDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

//        dataGenerator.addProvider(new CTBlockstateProvider(dataGenerator, helper));
//        dataGenerator.addProvider(new CTItemModelProvider(dataGenerator, helper));
//        dataGenerator.addProvider(new CTRecipeProvider(dataGenerator));
        dataGenerator.addProvider(new CTLootTableProvider(dataGenerator));
    }

}
