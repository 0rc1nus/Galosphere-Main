package net.orcinus.galosphere.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GDataGenerator {

    private GDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        boolean client = event.includeClient();
        boolean server = event.includeServer();
        dataGenerator.addProvider(client, new GBlockstateProvider(packOutput, helper));
        dataGenerator.addProvider(client, new GItemModelProvider(packOutput, helper));
        dataGenerator.addProvider(server, new GRecipeProvider(packOutput));
        dataGenerator.addProvider(server, new GLootTableProvider(packOutput));
        GBlockTagsProvider blockTagsProvider = new GBlockTagsProvider(packOutput, lookupProvider, helper);
        dataGenerator.addProvider(server, blockTagsProvider);
        dataGenerator.addProvider(server, new GItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), helper));
        dataGenerator.addProvider(server, new GEntityTypeTagsProvider(packOutput, lookupProvider, helper));
        dataGenerator.addProvider(server, new GDatapackBuiltinEntriesProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(server, new GBiomeTagsProvider(packOutput, lookupProvider, helper));
        dataGenerator.addProvider(server, new GLanguageProvider(packOutput));
    }

}
