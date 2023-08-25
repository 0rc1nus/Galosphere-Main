package net.orcinus.galosphere.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBlocks;

public class GItemModelProvider extends ItemModelProvider {

    public GItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(GBlocks.STRANDED_MEMBRANE_BLOCK.get());
        blockItem("silver_block");
        blockItem("raw_silver_block");
        blockItem("silver_ore");
        blockItem("deepslate_silver_ore");
        blockItem("amethyst_stairs");
        blockItem("amethyst_slab");
        blockItem("allurite_block");
        blockItem("allurite_stairs");
        blockItem("allurite_slab");
        blockItem("lumiere_block");
        blockItem("lumiere_stairs");
        blockItem("lumiere_slab");
        blockItem("smooth_amethyst");
        blockItem("smooth_amethyst_stairs");
        blockItem("smooth_amethyst_slab");
        blockItem("smooth_allurite");
        blockItem("smooth_allurite_stairs");
        blockItem("smooth_allurite_slab");
        blockItem("smooth_lumiere");
        blockItem("smooth_lumiere_stairs");
        blockItem("smooth_lumiere_slab");
        blockItem("amethyst_bricks");
        blockItem("amethyst_brick_stairs");
        blockItem("amethyst_brick_slab");
        blockItem("allurite_bricks");
        blockItem("allurite_brick_stairs");
        blockItem("allurite_brick_slab");
        blockItem("lumiere_bricks");
        blockItem("lumiere_brick_stairs");
        blockItem("lumiere_brick_slab");
        blockItem("chiseled_amethyst");
        blockItem("chiseled_allurite");
        blockItem("chiseled_lumiere");
        blockItem("charged_lumiere_block");
        withExistingParent("warped_anchor", modLoc("block/" + "template_warped_anchor"));
        blockItem("combustion_table");
        blockItem("amethyst_lamp");
        blockItem("allurite_lamp");
        blockItem("lumiere_lamp");
        blockItem("lichen_moss");
        blockItem("silver_tiles");
        blockItem("silver_tiles_stairs");
        blockItem("silver_tiles_slab");
        blockItem("silver_panel");
        blockItem("silver_panel_stairs");
        blockItem("silver_panel_slab");
        blockItem(GBlocks.PINK_SALT.get());
        blockItem(GBlocks.ROSE_PINK_SALT.get());
        blockItem(GBlocks.PASTEL_PINK_SALT.get());
        blockItem(GBlocks.PINK_SALT_STAIRS.get());
        blockItem(GBlocks.ROSE_PINK_SALT_STAIRS.get());
        blockItem(GBlocks.PASTEL_PINK_SALT_STAIRS.get());
        blockItem(GBlocks.PINK_SALT_SLAB.get());
        blockItem(GBlocks.ROSE_PINK_SALT_SLAB.get());
        blockItem(GBlocks.PASTEL_PINK_SALT_SLAB.get());
        blockItem(GBlocks.POLISHED_PINK_SALT.get());
        blockItem(GBlocks.POLISHED_ROSE_PINK_SALT.get());
        blockItem(GBlocks.POLISHED_PASTEL_PINK_SALT.get());
        blockItem(GBlocks.POLISHED_PINK_SALT_SLAB.get());
        blockItem(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get());
        blockItem(GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB.get());
        blockItem(GBlocks.POLISHED_PINK_SALT_STAIRS.get());
        blockItem(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get());
        blockItem(GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS.get());
        blockItem(GBlocks.PINK_SALT_BRICKS.get());
        blockItem(GBlocks.ROSE_PINK_SALT_BRICKS.get());
        blockItem(GBlocks.PASTEL_PINK_SALT_BRICKS.get());
        blockItem(GBlocks.PINK_SALT_BRICK_SLAB.get());
        blockItem(GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get());
        blockItem(GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get());
        blockItem(GBlocks.PINK_SALT_BRICK_STAIRS.get());
        blockItem(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get());
        blockItem(GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get());
        blockItem(GBlocks.CHISELED_PINK_SALT.get());
        blockItem(GBlocks.CHISELED_ROSE_PINK_SALT.get());
        blockItem(GBlocks.CHISELED_PASTEL_PINK_SALT.get());
        blockItem(GBlocks.PINK_SALT_LAMP.get());
        blockItem(GBlocks.CURED_MEMBRANE_BLOCK.get());
        blockItem(GBlocks.SHADOW_FRAME.get());
        item("pink_salt_cluster");
        itemWithBlockDirectory("pink_salt_straw", "pink_salt_straw_up_top");
        withExistingParent("monstrometer", new ResourceLocation("block/cube_bottom_top")).texture("top", new ResourceLocation(Galosphere.MODID, "block/monstrometer_top")).texture("bottom", new ResourceLocation(Galosphere.MODID, "block/monstrometer_bottom")).texture("side", new ResourceLocation(Galosphere.MODID, "block/monstrometer_side"));
        item("silver_ingot");
        item("allurite_shard");
        item("lumiere_shard");
        item("raw_silver");
        item("silver_bomb");
        item("silver_nugget");
        item("sterling_helmet");
        item("sterling_chestplate");
        item("sterling_leggings");
        item("sterling_boots");
        item("sterling_horse_armor");
        item("icon_item");
        item("bottle_of_spectre");
        item("chandelier");
        item("lichen_cordyceps");
        item("golden_lichen_cordyceps");
        item("glow_flare");
        item("chandelier");
        item("silver_upgrade_smithing_template");
        item("preserving_template");
        item("pink_salt_shard");
        item("salted_jerky");
        item("cured_membrane");
        itemWithBlockDirectory("silver_lattice");
        itemWithBlockDirectory("lichen_roots");
        itemWithBlockDirectory("bowl_lichen");
        itemWithBlockDirectory("lichen_shelf");
        itemWithBlockDirectory("glow_ink_clumps");

        spawnEggItem("sparkle");
        spawnEggItem("spectre");
        spawnEggItem("specterpillar");

        withExistingParent("crossbow_glow_flare", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(Galosphere.MODID, "item/crossbow_glow_flare"));
    }

    private void spawnEggItem(String entityName) {
        withExistingParent(entityName + "_spawn_egg", new ResourceLocation("item/template_spawn_egg"));
    }

    private void blockItem(Block block) {
        blockItem(ForgeRegistries.BLOCKS.getKey(block).getPath());
    }

    private void blockItem(String parent) {
        withExistingParent(parent, modLoc("block/" + parent));
    }

    private void itemWithBlockDirectory(String parent) {
        itemWithBlockDirectory(parent, parent);
    }

    private void itemWithBlockDirectory(String parent, String direct) {
        withExistingParent(parent, new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(Galosphere.MODID, "block/" + direct));
    }

    private void item(String parent) {
        withExistingParent(parent, new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(Galosphere.MODID, "item/" + parent));
    }
}
