package net.orcinus.galosphere.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

public class GalosphereTab {

    public static CreativeModeTab construct() {
        return FabricItemGroup
                .builder(Galosphere.id(Galosphere.MODID))
                .icon(() -> new ItemStack(GItems.ICON_ITEM))
                .displayItems(GalosphereTab::createiveTabInit)
                .build();
    }

    private static void createiveTabInit(FeatureFlagSet featureFlagSet, CreativeModeTab.Output output, boolean flag) {
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
    }

}
