package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;

public class GItemModelProvider extends ItemModelProvider {

    public GItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
        blockItem("aura_ringer");
        blockItem("warped_anchor");
        blockItem("combustion_table");
        blockItem("amethyst_lamp");
        blockItem("allurite_lamp");
        blockItem("lumiere_lamp");
        blockItem("lichen_moss");
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
        itemWithBlockDirectory("lichen_roots");
        itemWithBlockDirectory("bowl_lichen");
        itemWithBlockDirectory("lichen_shelf");
        itemWithBlockDirectory("glow_ink_clumps");

        spawnEggItem("sparkle");
        spawnEggItem("spectre");

        withExistingParent("crossbow_glow_flare", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(Galosphere.MODID, "item/crossbow_glow_flare"));
    }

    private void spawnEggItem(String entityName) {
        withExistingParent(entityName + "_spawn_egg", new ResourceLocation("item/template_spawn_egg"));
    }

    private void blockItem(String parent) {
        withExistingParent(parent, modLoc("block/" + parent));
    }

    private void itemWithBlockDirectory(String parent) {
        withExistingParent(parent, new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(Galosphere.MODID, "block/" + parent));
    }

    private void item(String parent) {
        withExistingParent(parent, new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(Galosphere.MODID, "item/" + parent));
    }

    private void itemWithBlockDir(String parent) {
        withExistingParent(parent, new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(Galosphere.MODID, "block/" + parent));
    }
}
