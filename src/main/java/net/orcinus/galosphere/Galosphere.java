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
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GEvents;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GParticleTypes;
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
            .displayItems((featureFlagSet, output, bl) -> {
                output.accept(GItems.SPARKLE_SPAWN_EGG);
                output.accept(GItems.SPECTRE_SPAWN_EGG);
                output.accept(GItems.ALLURITE_SHARD);
                output.accept(GItems.LUMIERE_SHARD);
                output.accept(GBlocks.ALLURITE_BLOCK);
                output.accept(GBlocks.LUMIERE_BLOCK);
                output.accept(GBlocks.CHARGED_LUMIERE_BLOCK);
                output.accept(GBlocks.ALLURITE_CLUSTER);
                output.accept(GBlocks.LUMIERE_CLUSTER);
                output.accept(GBlocks.AMETHYST_STAIRS);
                output.accept(GBlocks.AMETHYST_SLAB);
                output.accept(GBlocks.ALLURITE_STAIRS);
                output.accept(GBlocks.ALLURITE_SLAB);
                output.accept(GBlocks.LUMIERE_STAIRS);
                output.accept(GBlocks.LUMIERE_SLAB);
                output.accept(GBlocks.SMOOTH_AMETHYST);
                output.accept(GBlocks.SMOOTH_AMETHYST_STAIRS);
                output.accept(GBlocks.SMOOTH_AMETHYST_SLAB);
                output.accept(GBlocks.SMOOTH_ALLURITE);
                output.accept(GBlocks.SMOOTH_ALLURITE_STAIRS);
                output.accept(GBlocks.SMOOTH_ALLURITE_SLAB);
                output.accept(GBlocks.SMOOTH_LUMIERE);
                output.accept(GBlocks.SMOOTH_LUMIERE_STAIRS);
                output.accept(GBlocks.SMOOTH_LUMIERE_SLAB);
                output.accept(GBlocks.AMETHYST_BRICKS);
                output.accept(GBlocks.AMETHYST_BRICK_STAIRS);
                output.accept(GBlocks.AMETHYST_BRICK_SLAB);
                output.accept(GBlocks.ALLURITE_BRICKS);
                output.accept(GBlocks.ALLURITE_BRICK_STAIRS);
                output.accept(GBlocks.ALLURITE_BRICK_SLAB);
                output.accept(GBlocks.LUMIERE_BRICKS);
                output.accept(GBlocks.LUMIERE_BRICK_STAIRS);
                output.accept(GBlocks.LUMIERE_BRICK_SLAB);
                output.accept(GBlocks.CHISELED_AMETHYST);
                output.accept(GBlocks.CHISELED_ALLURITE);
                output.accept(GBlocks.CHISELED_LUMIERE);
                output.accept(GBlocks.AMETHYST_LAMP);
                output.accept(GBlocks.ALLURITE_LAMP);
                output.accept(GBlocks.LUMIERE_LAMP);
                output.accept(GBlocks.SILVER_BLOCK);
                output.accept(GBlocks.RAW_SILVER_BLOCK);
                output.accept(GBlocks.SILVER_ORE);
                output.accept(GBlocks.DEEPSLATE_SILVER_ORE);
                output.accept(GBlocks.AURA_RINGER);
                output.accept(GBlocks.WARPED_ANCHOR);
                output.accept(GBlocks.COMBUSTION_TABLE);
                output.accept(GBlocks.LICHEN_MOSS);
                output.accept(GBlocks.LICHEN_ROOTS);
                output.accept(GBlocks.BOWL_LICHEN);
                output.accept(GBlocks.LICHEN_SHELF);
                output.accept(GBlocks.GLOW_INK_CLUMPS);
                output.accept(GBlocks.CHANDELIER);

                output.accept(GItems.SILVER_INGOT);
                output.accept(GItems.RAW_SILVER);
                output.accept(GItems.SILVER_NUGGET);
                output.accept(GItems.SILVER_BOMB);
                output.accept(GItems.STERLING_HELMET);
                output.accept(GItems.STERLING_CHESTPLATE);
                output.accept(GItems.STERLING_LEGGINGS);
                output.accept(GItems.STERLING_BOOTS);
                output.accept(GItems.STERLING_HORSE_ARMOR);
                output.accept(GItems.BOTTLE_OF_SPECTRE);
                output.accept(GItems.GLOW_FLARE);
                output.accept(GItems.LICHEN_CORDYCEPS);
                output.accept(GItems.GOLDEN_LICHEN_CORDYCEPS);
            })
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
