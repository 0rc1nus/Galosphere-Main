package net.orcinus.galosphere;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.orcinus.galosphere.crafting.GlintingManager;
import net.orcinus.galosphere.crafting.LumiereReformingManager;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GBiomeModifier;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCreativeModeTabs;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.init.GDataComponents;
import net.orcinus.galosphere.init.GEnchantmentEffectComponents;
import net.orcinus.galosphere.init.GEntityDataSerializers;
import net.orcinus.galosphere.init.GEntitySubPredicates;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GEvents;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GPlacedFeatures;
import net.orcinus.galosphere.init.GPotions;
import net.orcinus.galosphere.init.GRecipeSerializers;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.init.GStructureProcessorTypes;
import net.orcinus.galosphere.init.GVanillaIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Galosphere implements ModInitializer {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "galosphere";

    @Override
    public void onInitialize() {
        GItems.init();
        GBlocks.init();
        GSoundEvents.init();
        GAttributes.init();
        GBiomeModifier.init();
        GCriteriaTriggers.init();
        GCreativeModeTabs.init();
        GBlockEntityTypes.init();
        GEntityDataSerializers.init();
        GEntityTypes.init();
        GDataComponents.init();
        GEnchantmentEffectComponents.init();
        GEntitySubPredicates.init();
        GEvents.init();
        GFeatures.init();
        GPotions.init();
        GParticleTypes.init();
        GPlacedFeatures.init();
        GMobEffects.init();
        GMenuTypes.init();
        GMemoryModuleTypes.init();
        GRecipeSerializers.init();
        GStructureProcessorTypes.init();
        GVanillaIntegration.init();
        GNetwork.init();

        SpawnPlacements.register(GEntityTypes.SPARKLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Sparkle::checkSparkleSpawnRules);
        SpawnPlacements.register(GEntityTypes.SPECTRE, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spectre::checkSpectreSpawnRules);

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new LumiereReformingManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new GlintingManager());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}

