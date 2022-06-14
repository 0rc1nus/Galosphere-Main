package net.orcinus.galosphere.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.orcinus.galosphere.init.GBiomeModifiers;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GPlacedFeatures;

public class GalosphereBiomeProvider implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GPlacedFeatures.ORE_SILVER_SMALL);
            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GPlacedFeatures.ORE_SILVER_MIDDLE);

            if (biome.is(GBiomes.CRYSTAL_CANYONS.getId()) || biome.is(Biomes.DEEP_DARK)) {
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
                builder.getMobSpawnSettings().addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
                builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(GEntityTypes.SPARKLE.get(), 120, 4, 6));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return GBiomeModifiers.GALOSPHERE_BIOME_MODIFIER.get();
    }
}
