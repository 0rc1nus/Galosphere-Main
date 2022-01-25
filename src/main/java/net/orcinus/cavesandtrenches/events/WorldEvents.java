package net.orcinus.cavesandtrenches.events;

import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.init.CTBiomes;
import net.orcinus.cavesandtrenches.init.CTPlacedFeatures;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        Biome.BiomeCategory category = event.getCategory();
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        if (category == Biome.BiomeCategory.NETHER || category == Biome.BiomeCategory.THEEND || category == Biome.BiomeCategory.NONE) return;

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CTPlacedFeatures.ORE_SILVER_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CTPlacedFeatures.ORE_SILVER_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CTPlacedFeatures.ORE_SILVER_MIDDLE);

        if (biome == CTBiomes.CRYSTAL_CANYONS.get()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LARGE_ALLURITE_CRYSTALS);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LARGE_LUMIERE_CRYSTALS);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.ALLURITE_CRYSTALS);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LUMIERE_CRYSTALS);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CTPlacedFeatures.MYSTERIA_TREE);
        }
    }
}
