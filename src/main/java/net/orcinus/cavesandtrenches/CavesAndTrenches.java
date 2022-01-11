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
import net.orcinus.cavesandtrenches.init.CTItems;
import net.orcinus.cavesandtrenches.init.CTMenuTypes;
import net.orcinus.cavesandtrenches.util.RegistryHandler;
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
    public static final RegistryHandler REGISTRY = new RegistryHandler();

    public CavesAndTrenches() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        RegistryHandler.init(modEventBus);
        CTMenuTypes.REGISTRY.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new WorldEvents());
        MinecraftForge.EVENT_BUS.register(new MobEvents());
        MinecraftForge.EVENT_BUS.register(new MiscEvents());
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
