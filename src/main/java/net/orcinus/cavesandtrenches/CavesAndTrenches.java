package net.orcinus.cavesandtrenches;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CavesAndTrenches.MODID)
public class CavesAndTrenches {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cavesandtrenches";
    public static final CreativeModeTab CAVESANDTRENCHES = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return CTItems.SILVER_BOMB.get().getDefaultInstance();
        }
    };

    public CavesAndTrenches() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        CTBlocks.BLOCKS.register(modEventBus);
        CTBiomes.BIOMES.register(modEventBus);
        CTEntityTypes.ENTITY_TYPES.register(modEventBus);
        CTFeatures.FEATURES.register(modEventBus);
        CTItems.ITEMS.register(modEventBus);
        CTMenuTypes.MENU_TYPES.register(modEventBus);
        CTParticleTypes.PARTICLES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new WorldEvents());
        MinecraftForge.EVENT_BUS.register(new MobEvents());
        MinecraftForge.EVENT_BUS.register(new MiscEvents());
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
