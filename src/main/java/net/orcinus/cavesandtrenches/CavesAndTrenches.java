package net.orcinus.cavesandtrenches;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orcinus.cavesandtrenches.events.MiscEvents;
import net.orcinus.cavesandtrenches.events.MobEvents;
import net.orcinus.cavesandtrenches.events.WorldEvents;
import net.orcinus.cavesandtrenches.init.CTBiomes;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTEntityTypes;
import net.orcinus.cavesandtrenches.init.CTFeatures;
import net.orcinus.cavesandtrenches.init.CTItems;
import net.orcinus.cavesandtrenches.init.CTMenuTypes;
import net.orcinus.cavesandtrenches.init.CTParticleTypes;
import net.orcinus.cavesandtrenches.util.CavesAndTrenchesTab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CavesAndTrenches.MODID)
public class CavesAndTrenches {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cavesandtrenches";
    public static final CreativeModeTab CAVESANDTRENCHES = new CavesAndTrenchesTab(MODID);

    public CavesAndTrenches() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);

        CTBlocks.BLOCKS.register(modEventBus);
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

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
