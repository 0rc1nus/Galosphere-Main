package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.orcinus.galosphere.Galosphere;

public class GCreativeModeTabs {
    public static final ResourceKey<CreativeModeTab> GALOSPHERE = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(Galosphere.MODID, "galosphere"));

    public static void init() {
        initGalosphereTab();
        initItemGroupEvents();
    }

    public static void initGalosphereTab() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, GALOSPHERE, FabricItemGroup.builder()
                .icon(GItems.ICON_ITEM::getDefaultInstance)
                .title(Component.translatable("itemGroup.galosphere.galosphere"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE);
                    output.accept(GItems.PRESERVED_TEMPLATE);
                    output.accept(GItems.SPARKLE_SPAWN_EGG);
                    output.accept(GItems.SPECTRE_SPAWN_EGG);
                    output.accept(GItems.SPECTERPILLAR_SPAWN_EGG);
                    output.accept(GItems.BERSERKER_SPAWN_EGG);
                    output.accept(GItems.PRESERVED_SPAWN_EGG);
                    output.accept(GItems.BOTTLE_OF_SPECTRE);
                    output.accept(GItems.ALLURITE_SHARD);
                    output.accept(GItems.LUMIERE_SHARD);
                    output.accept(GItems.PINK_SALT_SHARD);
                    output.accept(GItems.RAW_SILVER);
                    output.accept(GItems.SILVER_INGOT);
                    output.accept(GItems.SILVER_NUGGET);
                    output.accept(GItems.BAROMETER);
                    output.accept(GItems.SILVER_BOMB);
                    output.accept(GItems.STERLING_HELMET);
                    output.accept(GItems.STERLING_CHESTPLATE);
                    output.accept(GItems.STERLING_LEGGINGS);
                    output.accept(GItems.STERLING_BOOTS);
                    output.accept(GItems.STERLING_HORSE_ARMOR);
                    output.accept(GItems.LICHEN_CORDYCEPS);
                    output.accept(GItems.GOLDEN_LICHEN_CORDYCEPS);
                    output.accept(GItems.SALTED_JERKY);
                    output.accept(GItems.PRESERVED_FLESH);
                    output.accept(GItems.CURED_MEMBRANE);
                    output.accept(GItems.GLOW_FLARE);
                    output.accept(GItems.SPECTRE_FLARE);
                    output.accept(GBlocks.POTPOURRI);
                    output.accept(GItems.CHANDELIER);
                    output.accept(GBlocks.GILDED_BEADS);
                    output.accept(GBlocks.MONSTROMETER);
                    output.accept(GBlocks.COMBUSTION_TABLE);
                    output.accept(GBlocks.WARPED_ANCHOR);
                    output.accept(GBlocks.SHADOW_FRAME);
                    output.accept(GBlocks.SILVER_BALANCE);
                    output.accept(GBlocks.SILVER_TILES);
                    output.accept(GBlocks.SILVER_TILES_STAIRS);
                    output.accept(GBlocks.SILVER_TILES_SLAB);
                    output.accept(GBlocks.SILVER_PANEL);
                    output.accept(GBlocks.SILVER_PANEL_STAIRS);
                    output.accept(GBlocks.SILVER_PANEL_SLAB);
                    output.accept(GBlocks.SILVER_LATTICE);
                    output.accept(GBlocks.SILVER_ORE);
                    output.accept(GBlocks.DEEPSLATE_SILVER_ORE);
                    output.accept(GBlocks.SILVER_BLOCK);
                    output.accept(GBlocks.RAW_SILVER_BLOCK);
                    output.accept(GBlocks.ALLURITE_BLOCK);
                    output.accept(GBlocks.LUMIERE_BLOCK);
                    output.accept(GBlocks.CHARGED_LUMIERE_BLOCK);
                    output.accept(GBlocks.ALLURITE_CLUSTER);
                    output.accept(GBlocks.LUMIERE_CLUSTER);
                    output.accept(GBlocks.GLINTED_ALLURITE_CLUSTER);
                    output.accept(GBlocks.GLINTED_LUMIERE_CLUSTER);
                    output.accept(GBlocks.GLINTED_AMETHYST_CLUSTER);
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
                    output.accept(GBlocks.LICHEN_MOSS);
                    output.accept(GBlocks.LICHEN_ROOTS);
                    output.accept(GBlocks.BOWL_LICHEN);
                    output.accept(GBlocks.LICHEN_SHELF);
                    output.accept(GBlocks.SUCCULENT);
                    output.accept(GItems.SUCCULENT_PETALS);
                    output.accept(GBlocks.GLOW_INK_CLUMPS);
                    output.accept(GBlocks.PINK_SALT);
                    output.accept(GBlocks.ROSE_PINK_SALT);
                    output.accept(GBlocks.PASTEL_PINK_SALT);
                    output.accept(GBlocks.PINK_SALT_STAIRS);
                    output.accept(GBlocks.ROSE_PINK_SALT_STAIRS);
                    output.accept(GBlocks.PASTEL_PINK_SALT_STAIRS);
                    output.accept(GBlocks.PINK_SALT_SLAB);
                    output.accept(GBlocks.ROSE_PINK_SALT_SLAB);
                    output.accept(GBlocks.PASTEL_PINK_SALT_SLAB);
                    output.accept(GBlocks.PINK_SALT_WALL);
                    output.accept(GBlocks.ROSE_PINK_SALT_WALL);
                    output.accept(GBlocks.PASTEL_PINK_SALT_WALL);
                    output.accept(GBlocks.POLISHED_PINK_SALT);
                    output.accept(GBlocks.POLISHED_ROSE_PINK_SALT);
                    output.accept(GBlocks.POLISHED_PASTEL_PINK_SALT);
                    output.accept(GBlocks.POLISHED_PINK_SALT_STAIRS);
                    output.accept(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS);
                    output.accept(GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS);
                    output.accept(GBlocks.POLISHED_PINK_SALT_SLAB);
                    output.accept(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB);
                    output.accept(GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB);
                    output.accept(GBlocks.POLISHED_PINK_SALT_WALL);
                    output.accept(GBlocks.POLISHED_ROSE_PINK_SALT_WALL);
                    output.accept(GBlocks.POLISHED_PASTEL_PINK_SALT_WALL);
                    output.accept(GBlocks.PINK_SALT_BRICKS);
                    output.accept(GBlocks.ROSE_PINK_SALT_BRICKS);
                    output.accept(GBlocks.PASTEL_PINK_SALT_BRICKS);
                    output.accept(GBlocks.PINK_SALT_BRICK_STAIRS);
                    output.accept(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS);
                    output.accept(GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS);
                    output.accept(GBlocks.PINK_SALT_BRICK_SLAB);
                    output.accept(GBlocks.ROSE_PINK_SALT_BRICK_SLAB);
                    output.accept(GBlocks.PASTEL_PINK_SALT_BRICK_SLAB);
                    output.accept(GBlocks.PINK_SALT_BRICK_WALL);
                    output.accept(GBlocks.ROSE_PINK_SALT_BRICK_WALL);
                    output.accept(GBlocks.PASTEL_PINK_SALT_BRICK_WALL);
                    output.accept(GBlocks.CHISELED_PINK_SALT);
                    output.accept(GBlocks.CHISELED_ROSE_PINK_SALT);
                    output.accept(GBlocks.CHISELED_PASTEL_PINK_SALT);
                    output.accept(GBlocks.PINK_SALT_LAMP);
                    output.accept(GBlocks.PINK_SALT_STRAW);
                    output.accept(GBlocks.PINK_SALT_CLUSTER);
                    output.accept(GBlocks.CURED_MEMBRANE_BLOCK);
                    output.accept(GBlocks.STRANDED_MEMBRANE_BLOCK);
                })
                .build());
    }

    public static void initItemGroupEvents() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.AMETHYST_BLOCK, GBlocks.AMETHYST_STAIRS, GBlocks.AMETHYST_SLAB, GBlocks.CHISELED_AMETHYST, GBlocks.AMETHYST_LAMP, GBlocks.SMOOTH_AMETHYST, GBlocks.SMOOTH_AMETHYST_STAIRS, GBlocks.SMOOTH_AMETHYST_SLAB, GBlocks.AMETHYST_BRICKS, GBlocks.AMETHYST_BRICK_STAIRS, GBlocks.AMETHYST_BRICK_SLAB, GBlocks.ALLURITE_BLOCK, GBlocks.ALLURITE_STAIRS, GBlocks.ALLURITE_SLAB, GBlocks.CHISELED_ALLURITE, GBlocks.ALLURITE_LAMP, GBlocks.SMOOTH_ALLURITE, GBlocks.SMOOTH_ALLURITE_STAIRS, GBlocks.SMOOTH_ALLURITE_SLAB, GBlocks.ALLURITE_BRICKS, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.LUMIERE_BLOCK, GBlocks.LUMIERE_STAIRS, GBlocks.LUMIERE_SLAB, GBlocks.CHISELED_LUMIERE, GBlocks.LUMIERE_LAMP, GBlocks.SMOOTH_LUMIERE, GBlocks.SMOOTH_LUMIERE_STAIRS, GBlocks.SMOOTH_LUMIERE_SLAB, GBlocks.LUMIERE_BRICKS, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.LUMIERE_BRICK_SLAB);
            entries.accept(GBlocks.SILVER_BLOCK);
            entries.accept(GBlocks.SILVER_PANEL);
            entries.accept(GBlocks.SILVER_PANEL_STAIRS);
            entries.accept(GBlocks.SILVER_PANEL_SLAB);
            entries.accept(GBlocks.SILVER_TILES);
            entries.accept(GBlocks.SILVER_TILES_STAIRS);
            entries.accept(GBlocks.SILVER_TILES_SLAB);
            entries.accept(GBlocks.SILVER_LATTICE);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.DEEPSLATE_IRON_ORE, GBlocks.SILVER_ORE, GBlocks.DEEPSLATE_SILVER_ORE);
            entries.addAfter(Blocks.RAW_GOLD_BLOCK, GBlocks.RAW_SILVER_BLOCK);
            entries.addAfter(Blocks.AMETHYST_CLUSTER, GBlocks.GLINTED_AMETHYST_CLUSTER, GBlocks.ALLURITE_BLOCK, GBlocks.ALLURITE_CLUSTER, GBlocks.GLINTED_ALLURITE_CLUSTER, GBlocks.LUMIERE_BLOCK, GBlocks.LUMIERE_CLUSTER, GBlocks.GLINTED_LUMIERE_CLUSTER);
            entries.addAfter(Blocks.GLOW_LICHEN, GBlocks.GLOW_INK_CLUMPS);
            entries.addAfter(Blocks.PEARLESCENT_FROGLIGHT, GBlocks.AMETHYST_LAMP, GBlocks.ALLURITE_LAMP, GBlocks.LUMIERE_LAMP);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, GItems.SILVER_UPGRADE_SMITHING_TEMPLATE);
            entries.addAfter(Items.RAW_GOLD, GItems.RAW_SILVER);
            entries.addAfter(Items.GOLD_INGOT, GItems.SILVER_INGOT);
            entries.addAfter(Items.GOLD_NUGGET, GItems.SILVER_NUGGET);
            entries.addAfter(Items.AMETHYST_SHARD, GItems.ALLURITE_SHARD, GItems.LUMIERE_SHARD);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.END_ROD, GBlocks.CHANDELIER);
            entries.addAfter(Blocks.SMITHING_TABLE, GBlocks.COMBUSTION_TABLE);
            entries.addAfter(Blocks.RESPAWN_ANCHOR, GBlocks.MONSTROMETER, GBlocks.WARPED_ANCHOR);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.SNOW, GBlocks.LICHEN_MOSS);
            entries.addAfter(Blocks.RED_MUSHROOM, GBlocks.BOWL_LICHEN);
            entries.addAfter(Blocks.CACTUS, GBlocks.LICHEN_SHELF, GBlocks.LICHEN_ROOTS);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.addAfter(Items.GLOW_BERRIES, GItems.LICHEN_CORDYCEPS, GItems.GOLDEN_LICHEN_CORDYCEPS);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.SHEEP_SPAWN_EGG, GItems.SPARKLE_SPAWN_EGG, GItems.SPECTRE_SPAWN_EGG, GItems.SPECTERPILLAR_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addAfter(Items.CLOCK, GItems.BAROMETER);
            entries.addBefore(Items.SADDLE, GItems.GLOW_FLARE, GItems.SPECTRE_FLARE);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.addAfter(Items.CHAINMAIL_BOOTS, GItems.STERLING_HELMET, GItems.STERLING_CHESTPLATE, GItems.STERLING_LEGGINGS, GItems.STERLING_BOOTS);
            entries.addAfter(Items.TNT, GItems.SILVER_BOMB);
        });
    }

}
