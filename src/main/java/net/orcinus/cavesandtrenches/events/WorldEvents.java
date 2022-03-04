package net.orcinus.cavesandtrenches.events;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
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
        MobSpawnSettingsBuilder mobBuilder = event.getSpawns();
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        if (category == Biome.BiomeCategory.NETHER || category == Biome.BiomeCategory.THEEND || category == Biome.BiomeCategory.NONE) return;

        builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, CTPlacedFeatures.ORE_SILVER_UPPER);
        builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, CTPlacedFeatures.ORE_SILVER_SMALL);
        builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, CTPlacedFeatures.ORE_SILVER_MIDDLE);

        if (biome == CTBiomes.CRYSTAL_CANYONS.get()) {
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
            builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, CTPlacedFeatures.MYSTERIA_TREE);
            builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, CTPlacedFeatures.STIFFENED_VINES);
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CTPlacedFeatures.FLUTTERED_LEAF);
            mobBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        }

    }
}
