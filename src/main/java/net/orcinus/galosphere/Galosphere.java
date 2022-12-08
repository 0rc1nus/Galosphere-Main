package net.orcinus.galosphere;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.crafting.LumiereReformingManager;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GBiomeModifier;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GConfiguredFeatures;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GEvents;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GPlacedFeatures;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.init.GVanillaIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Galosphere implements ModInitializer {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "galosphere";
    public static final CreativeModeTab GALOSPHERE = FabricItemGroup
            .builder(Galosphere.id(MODID))
            .icon(() -> new ItemStack(GItems.ICON_ITEM))
            .build();

    @Override
    public void onInitialize() {
        GItems.init();
        GBlocks.init();
        GBiomeModifier.init();
        GSoundEvents.init();
        GAttributes.init();
        GCriteriaTriggers.init();
        GBlockEntityTypes.init();
        GEntityTypes.init();
        GEvents.init();
        GFeatures.init();
        GParticleTypes.init();
        GMenuTypes.init();
        GMobEffects.init();
        GVanillaIntegration.init();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new LumiereReformingManager());
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }

}
