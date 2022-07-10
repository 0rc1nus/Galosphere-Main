package net.orcinus.galosphere;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orcinus.galosphere.events.MiscEvents;
import net.orcinus.galosphere.events.MobEvents;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GBiomeModifiers;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GConfiguredFeatures;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GPlacedFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Galosphere implements ModInitializer {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "galosphere";
    public static final CreativeModeTab GALOSPHERE = FabricItemGroupBuilder.create(new ResourceLocation(MODID, MODID)).icon(() -> new ItemStack(GItems.ICON_ITEM)).build();

    public Galosphere() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);

        GAttributes.ATTRIBUTES.register(modEventBus);
        GMenuTypes.MENU_TYPES.register(modEventBus);
        eventBus.register(this);
        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());

    }

    @Override
    public void onInitialize() {
        GItems.init();
        GBiomes.init();
        GBlocks.init();
        GBlockEntityTypes.init();
        GFeatures.init();
        GConfiguredFeatures.init();
        GPlacedFeatures.init();
        GMobEffects.init();

        Util.make(ImmutableMap.<Holder<PlacedFeature>, GenerationStep.Decoration>builder(), map -> {
            map.put(GPlacedFeatures.ORE_SILVER_MIDDLE, GenerationStep.Decoration.UNDERGROUND_ORES);
            map.put(GPlacedFeatures.ORE_SILVER_SMALL, GenerationStep.Decoration.UNDERGROUND_ORES);
        }).build().forEach((featureHolder, decoration) -> {
            featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), decoration, placedFeatureResourceKey));
        });

        Util.make(ImmutableMap.<Holder<PlacedFeature>, GenerationStep.Decoration>builder(), map -> {
            map.put(GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.ALLURITE_CEILING_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LUMIERE_CEILING_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
        }).build().forEach((featureHolder, decoration) -> {
            featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), decoration, placedFeatureResourceKey));
        });

    }
}
