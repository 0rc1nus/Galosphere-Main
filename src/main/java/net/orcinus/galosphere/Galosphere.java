package net.orcinus.galosphere;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
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
import net.orcinus.galosphere.init.GParticleTypes;
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
        GBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);
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

        for (Holder<PlacedFeature> placedFeatureHolder : new Holder<>[]{ GPlacedFeatures.ORE_SILVER_MIDDLE, GPlacedFeatures.ORE_SILVER_MIDDLE }) {
            placedFeatureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, placedFeatureResourceKey));
        }

    }
}
