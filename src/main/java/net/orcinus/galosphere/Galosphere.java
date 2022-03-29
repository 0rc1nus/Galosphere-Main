package net.orcinus.galosphere;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orcinus.galosphere.config.GConfig;
import net.orcinus.galosphere.events.MiscEvents;
import net.orcinus.galosphere.events.MobEvents;
import net.orcinus.galosphere.events.WorldEvents;
import net.orcinus.galosphere.init.CTBiomes;
import net.orcinus.galosphere.init.CTBlockEntities;
import net.orcinus.galosphere.init.CTBlocks;
import net.orcinus.galosphere.init.CTEntityTypes;
import net.orcinus.galosphere.init.CTFeatures;
import net.orcinus.galosphere.init.CTItems;
import net.orcinus.galosphere.init.CTMenuTypes;
import net.orcinus.galosphere.init.CTParticleTypes;
import net.orcinus.galosphere.util.GalosphereTab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Galosphere.MODID)
public class Galosphere {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "galosphere";
    public static final CreativeModeTab GALOSPHERE = new GalosphereTab(MODID);
    //seed -7714140795261595653
    //seed coords -145 95 77
    //seed 7856658500923416262
    //seed coords -73 69 150

    //-7189274751734808686

    public Galosphere() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);

        CTBlocks.BLOCKS.register(modEventBus);
        CTBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        CTBiomes.BIOMES.register(modEventBus);
        CTEntityTypes.ENTITY_TYPES.register(modEventBus);
        CTFeatures.FEATURES.register(modEventBus);
        CTItems.ITEMS.register(modEventBus);
        CTMenuTypes.MENU_TYPES.register(modEventBus);
        CTParticleTypes.PARTICLES.register(modEventBus);

        eventBus.register(this);
        eventBus.register(new WorldEvents());
        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GConfig.COMMON);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
