package net.orcinus.galosphere;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orcinus.galosphere.config.GalosphereConfig;
import net.orcinus.galosphere.events.MiscEvents;
import net.orcinus.galosphere.events.MobEvents;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCreativeModeTabs;
import net.orcinus.galosphere.init.GEnchantments;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GNetworkHandler;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GPlacedFeatures;
import net.orcinus.galosphere.init.GPotions;
import net.orcinus.galosphere.init.GRecipeSerializers;
import net.orcinus.galosphere.init.GSensorTypes;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.init.GStructureProcessorTypes;
import net.orcinus.galosphere.init.GVanillaIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO: Preserved nbt
@Mod(Galosphere.MODID)
public class Galosphere {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "galosphere";

    public Galosphere() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GalosphereConfig.COMMON);

        GAttributes.ATTRIBUTES.register(modEventBus);
        GBlocks.BLOCKS.register(modEventBus);
        GBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        GCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        GEntityTypes.ENTITY_TYPES.register(modEventBus);
        GEnchantments.ENCHANTMENTS.register(modEventBus);
        GFeatures.FEATURES.register(modEventBus);
        GItems.ITEMS.register(modEventBus);
        GMobEffects.MOB_EFFECTS.register(modEventBus);
        GMemoryModuleTypes.MEMORY_MODULE_TYPES.register(modEventBus);
        GMenuTypes.MENU_TYPES.register(modEventBus);
        GPotions.POTIONS.register(modEventBus);
        GParticleTypes.PARTICLES.register(modEventBus);
        GRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        GStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPES.register(modEventBus);
        GSensorTypes.SENSOR_TYPES.register(modEventBus);
        GSoundEvents.SOUND_EVENTS.register(modEventBus);

        eventBus.register(this);
        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GPlacedFeatures.init();
            GNetworkHandler.init();
            GVanillaIntegration.init();
            GPotions.init();
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Galosphere.MODID, path);
    }

}
