package net.orcinus.galosphere.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.compat.init.ForgeItemTags;
import net.orcinus.galosphere.init.GBlockFamilies;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class GRecipeProvider extends RecipeProvider {
    private static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(GBlocks.SILVER_ORE.get().asItem(), GBlocks.DEEPSLATE_SILVER_ORE.get().asItem(), GItems.RAW_SILVER.get());

    public GRecipeProvider(PackOutput packoutput) {
        super(packoutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        GBlockFamilies.getAllFamilies().forEach((blockFamily) -> {
            generateRecipes(consumer, blockFamily);
        });
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, GBlocks.SILVER_BALANCE.get()).pattern("#R#").pattern(" # ").pattern(" # ").define('#', GItems.SILVER_INGOT.get()).define('R', Items.REDSTONE).unlockedBy("has_silver_ingot", has(GItems.SILVER_INGOT.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER).requires(Items.BONE_MEAL).requires(Items.COAL).requires(GItems.PINK_SALT_SHARD.get()).unlockedBy("has_pink_salt_shard", has(GItems.PINK_SALT_SHARD.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, GItems.SUCCULENT_PETALS.get(), 3).requires(GBlocks.SUCCULENT.get().asItem()).unlockedBy("has_succulent", has(GBlocks.SUCCULENT.get().asItem())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, GBlocks.POTPOURRI.get()).requires(GItems.SUCCULENT_PETALS.get(), 3).requires(Items.BOW).unlockedBy("has_succulent_petals", has(GItems.SUCCULENT_PETALS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GBlocks.PINK_SALT.get())
                .define('#', GBlocks.PINK_SALT_STRAW.get().asItem())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_pink_salt_straw", has(GBlocks.PINK_SALT_STRAW.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GBlocks.GILDED_BEADS.get(), 8)
                .define('#', Items.GOLD_NUGGET)
                .define('G', Items.GOLD_INGOT)
                .pattern("GGG")
                .pattern("###")
                .pattern("GGG")
                .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GBlocks.STRANDED_MEMBRANE_BLOCK.get())
                .define('#', GItems.ALLURITE_SHARD.get())
                .define('C', GBlocks.CURED_MEMBRANE_BLOCK.get())
                .pattern(" # ")
                .pattern("#C#")
                .pattern(" # ")
                .unlockedBy("has_cured_membrane_block", has(GBlocks.CURED_MEMBRANE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GBlocks.SHADOW_FRAME.get())
                .define('S', GItems.SILVER_INGOT.get())
                .define('#', GItems.CURED_MEMBRANE.get())
                .pattern("S#S")
                .pattern("#S#")
                .pattern("S#S")
                .unlockedBy("has_cured_membrane", has(GItems.CURED_MEMBRANE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GBlocks.PINK_SALT_LAMP.get()).define('#', GBlocks.PINK_SALT_CLUSTER.get()).define('S', GItems.SILVER_INGOT.get()).pattern("#").pattern("S").unlockedBy("has_pink_salt_cluster", has(GBlocks.PINK_SALT_CLUSTER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, GItems.SALTED_JERKY.get()).define('S', GItems.PINK_SALT_SHARD.get()).define('#', Items.ROTTEN_FLESH).pattern(" S ").pattern("S#S").pattern(" S ").unlockedBy("has_pink_salt_shard", has(GItems.PINK_SALT_SHARD.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GItems.CURED_MEMBRANE.get()).define('S', GItems.PINK_SALT_SHARD.get()).define('#', Items.PHANTOM_MEMBRANE).pattern(" S ").pattern("S#S").pattern(" S ").unlockedBy("has_pink_salt_shard", has(GItems.PINK_SALT_SHARD.get())).save(consumer);
        twoXtwo(consumer, GBlocks.CURED_MEMBRANE_BLOCK.get(), GItems.CURED_MEMBRANE.get());
        twoXtwo(consumer, GBlocks.PINK_SALT_BRICKS.get(), GBlocks.POLISHED_PINK_SALT.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.ROSE_PINK_SALT_BRICKS.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.ALLURITE_BLOCK.get(), GItems.ALLURITE_SHARD.get(), 1);
        twoXtwo(consumer, GBlocks.LUMIERE_BLOCK.get(), GItems.LUMIERE_SHARD.get(), 1);
        twoXtwo(consumer, GBlocks.SMOOTH_AMETHYST.get(), Items.AMETHYST_BLOCK, 4);
        twoXtwo(consumer, GBlocks.SMOOTH_ALLURITE.get(), GBlocks.ALLURITE_BLOCK.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.SMOOTH_LUMIERE.get(), GBlocks.LUMIERE_BLOCK.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.AMETHYST_BRICKS.get(), GBlocks.SMOOTH_AMETHYST.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.ALLURITE_BRICKS.get(), GBlocks.SMOOTH_ALLURITE.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.LUMIERE_BRICKS.get(), GBlocks.SMOOTH_LUMIERE.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.SILVER_PANEL.get(), GBlocks.SILVER_BLOCK.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.SILVER_TILES.get(), GBlocks.SILVER_PANEL.get().asItem(), 4);
        threeXthree(consumer, GBlocks.SILVER_BLOCK.get(), GItems.SILVER_INGOT.get());
        threeXthree(consumer, GBlocks.RAW_SILVER_BLOCK.get(), GItems.RAW_SILVER.get());
        stairsBlock(consumer, GBlocks.AMETHYST_STAIRS.get(), Items.AMETHYST_BLOCK);
        slabBlock(consumer, GBlocks.AMETHYST_SLAB.get(), Items.AMETHYST_BLOCK);
        stairsBlock(consumer, GBlocks.SILVER_PANEL_STAIRS.get(), GBlocks.SILVER_PANEL.get().asItem());
        slabBlock(consumer, GBlocks.SILVER_PANEL_SLAB.get(), GBlocks.SILVER_PANEL.get().asItem());
        stairsBlock(consumer, GBlocks.SILVER_TILES_STAIRS.get(), GBlocks.SILVER_TILES.get().asItem());
        slabBlock(consumer, GBlocks.SILVER_TILES_SLAB.get(), GBlocks.SILVER_TILES.get().asItem());
        stairsBlock(consumer, GBlocks.ALLURITE_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get().asItem());
        slabBlock(consumer, GBlocks.ALLURITE_SLAB.get(), GBlocks.ALLURITE_BLOCK.get().asItem());
        stairsBlock(consumer, GBlocks.LUMIERE_STAIRS.get(), GBlocks.LUMIERE_BLOCK.get().asItem());
        slabBlock(consumer, GBlocks.LUMIERE_SLAB.get(), GBlocks.LUMIERE_BLOCK.get().asItem());
        stairsBlock(consumer, GBlocks.SMOOTH_AMETHYST_STAIRS.get(), GBlocks.SMOOTH_AMETHYST.get().asItem());
        slabBlock(consumer, GBlocks.SMOOTH_AMETHYST_SLAB.get(), GBlocks.SMOOTH_AMETHYST.get().asItem());
        stairsBlock(consumer, GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.SMOOTH_ALLURITE.get().asItem());
        slabBlock(consumer, GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.SMOOTH_ALLURITE.get().asItem());
        stairsBlock(consumer, GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.SMOOTH_LUMIERE.get().asItem());
        slabBlock(consumer, GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.SMOOTH_LUMIERE.get().asItem());
        stairsBlock(consumer, GBlocks.AMETHYST_BRICK_STAIRS.get(), GBlocks.AMETHYST_BRICKS.get().asItem());
        slabBlock(consumer, GBlocks.AMETHYST_BRICK_SLAB.get(), GBlocks.AMETHYST_BRICKS.get().asItem());
        stairsBlock(consumer, GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.ALLURITE_BRICKS.get().asItem());
        slabBlock(consumer, GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.ALLURITE_BRICKS.get().asItem());
        stairsBlock(consumer, GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.LUMIERE_BRICKS.get().asItem());
        slabBlock(consumer, GBlocks.LUMIERE_BRICK_SLAB.get(), GBlocks.LUMIERE_BRICKS.get().asItem());
        chiseled(consumer, GBlocks.AMETHYST_SLAB.get().asItem(), GBlocks.CHISELED_AMETHYST.get().asItem());
        chiseled(consumer, GBlocks.ALLURITE_SLAB.get().asItem(), GBlocks.CHISELED_ALLURITE.get().asItem());
        chiseled(consumer, GBlocks.LUMIERE_SLAB.get().asItem(), GBlocks.CHISELED_LUMIERE.get().asItem());

        nineBlockStorageRecipesWithCustomPacking(consumer, GItems.SILVER_NUGGET.get(), GItems.SILVER_INGOT.get(), "silver_ingot_from_nuggets", "silver_ingot");

        oreSmelting(consumer, SILVER_SMELTABLES, GItems.SILVER_INGOT.get(), 0.7F, 200, "silver_ingot");
        oreBlasting(consumer, SILVER_SMELTABLES, GItems.SILVER_INGOT.get(), 0.7F, 100, "silver_ingot");

        shaplessOne(consumer, GItems.SILVER_INGOT.get(), GBlocks.SILVER_BLOCK.get().asItem(), 9);
        shaplessOne(consumer, GItems.RAW_SILVER.get(), GBlocks.RAW_SILVER_BLOCK.get().asItem(), 9);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GItems.SILVER_UPGRADE_SMITHING_TEMPLATE.get(), 2)
                .define('#', ItemTags.PLANKS)
                .define('C', GItems.SILVER_INGOT.get())
                .define('S', GItems.SILVER_UPGRADE_SMITHING_TEMPLATE.get())
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###")
                .unlockedBy("has_silver_upgrade_smithing_template", has(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GItems.BAROMETER.get())
                .define('#', GItems.SILVER_INGOT.get())
                .define('@', Items.REDSTONE)
                .pattern(" # ")
                .pattern("#@#")
                .pattern(" # ")
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GBlocks.SILVER_LATTICE.get(), 16)
                .define('#', GItems.SILVER_INGOT.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_silver_ingot", has(GItems.SILVER_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GItems.GLOW_FLARE.get(), 4)
                .define('#', Items.GLOW_INK_SAC)
                .define('S', Tags.Items.INGOTS_COPPER)
                .define('F', Items.STICK)
                .pattern(" # ")
                .pattern(" S ")
                .pattern(" F ")
                .unlockedBy("has_glow_ink_sac", has(Items.GLOW_INK_SAC))
                .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GItems.SPECTRE_FLARE.get())
                .define('#', ForgeItemTags.SILVER_INGOT)
                .define('S', GItems.BOTTLE_OF_SPECTRE.get())
                .pattern("S")
                .pattern("#")
                .unlockedBy("has_silver_ingot", has(ForgeItemTags.SILVER_INGOT))
                .unlockedBy("has_bottle_of_spectre", has(GItems.BOTTLE_OF_SPECTRE.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.FOOD, GItems.GOLDEN_LICHEN_CORDYCEPS.get())
                .define('#', Tags.Items.NUGGETS_GOLD)
                .define('@', GItems.LICHEN_CORDYCEPS.get())
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .unlockedBy("has_lichen_cordyceps", has(GItems.LICHEN_CORDYCEPS.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.CHANDELIER.get())
                 .define('#', Tags.Items.INGOTS_COPPER)
                 .define('@', GItems.BOTTLE_OF_SPECTRE.get())
                 .define('C', Tags.Items.INGOTS_IRON)
                 .pattern("@")
                 .pattern("C")
                 .pattern("#")
                 .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
                 .unlockedBy("has_iron_nugget", has(Tags.Items.INGOTS_IRON))
                 .unlockedBy("has_bottle_of_spectre", has(GItems.BOTTLE_OF_SPECTRE.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.COMBUSTION_TABLE.get())
                .define('#', ItemTags.PLANKS)
                .define('@', ForgeItemTags.SILVER_INGOT)
                .pattern("@@")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_silver_ingot", has(ForgeItemTags.SILVER_INGOT)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.AMETHYST_LAMP.get())
                .define('R', Items.AMETHYST_SHARD)
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.ALLURITE_LAMP.get())
                .define('R', GItems.ALLURITE_SHARD.get())
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_allurite_shard", has(GItems.ALLURITE_SHARD.get()))
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.LUMIERE_LAMP.get())
                .define('R', GItems.LUMIERE_SHARD.get())
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_lumiere_shard", has(GItems.LUMIERE_SHARD.get()))
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.COMBAT, GItems.SILVER_BOMB.get(), 2)
                .define('R', ForgeItemTags.SILVER_INGOT)
                .define('G', Items.GUNPOWDER)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_gunpowder", has(Items.GUNPOWDER)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.MONSTROMETER.get())
                .define('S', ForgeItemTags.SILVER_STORAGE_BLOCKS)
                .define('C', GBlocks.LUMIERE_BLOCK.get())
                .pattern("SSS")
                .pattern("CCC")
                .pattern("SSS")
                .unlockedBy("has_lumiere_block", has(GBlocks.LUMIERE_BLOCK.get()))
                .unlockedBy("has_silver_block", has(ForgeItemTags.SILVER_STORAGE_BLOCKS)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.WARPED_ANCHOR.get())
                .define('S', ForgeItemTags.SILVER_STORAGE_BLOCKS)
                .define('C', GBlocks.ALLURITE_BLOCK.get())
                .pattern("CCC")
                .pattern("SSS")
                .unlockedBy("has_allurite_block", has(GBlocks.ALLURITE_BLOCK.get()))
                .unlockedBy("has_silver_block", has(ForgeItemTags.SILVER_STORAGE_BLOCKS)).save(consumer);

        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_STAIRS.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_SLAB.get(), GBlocks.PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_WALL.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_STAIRS.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_SLAB.get(), GBlocks.PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_WALL.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICKS.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_STAIRS.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_SLAB.get(), GBlocks.PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_WALL.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_PINK_SALT.get(), GBlocks.PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_STAIRS.get(), GBlocks.POLISHED_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_SLAB.get(), GBlocks.POLISHED_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_WALL.get(), GBlocks.POLISHED_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICKS.get(), GBlocks.POLISHED_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_STAIRS.get(), GBlocks.POLISHED_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_SLAB.get(), GBlocks.POLISHED_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_WALL.get(), GBlocks.POLISHED_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICKS.get(), GBlocks.PINK_SALT_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_STAIRS.get(), GBlocks.PINK_SALT_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_SLAB.get(), GBlocks.PINK_SALT_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_WALL.get(), GBlocks.PINK_SALT_BRICKS.get());

        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_STAIRS.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_SLAB.get(), GBlocks.ROSE_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_WALL.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get(), GBlocks.ROSE_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_WALL.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICKS.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get(), GBlocks.ROSE_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_WALL.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ROSE_PINK_SALT.get(), GBlocks.ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_WALL.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICKS.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_WALL.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICKS.get(), GBlocks.ROSE_PINK_SALT_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get(), GBlocks.ROSE_PINK_SALT_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get(), GBlocks.ROSE_PINK_SALT_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_WALL.get(), GBlocks.ROSE_PINK_SALT_BRICKS.get());

        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_STAIRS.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_SLAB.get(), GBlocks.PASTEL_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_WALL.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB.get(), GBlocks.PASTEL_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_WALL.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get(), GBlocks.PASTEL_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_WALL.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_PASTEL_PINK_SALT.get(), GBlocks.PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_WALL.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_WALL.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS.get(), GBlocks.PASTEL_PINK_SALT_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get(), GBlocks.PASTEL_PINK_SALT_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get(), GBlocks.PASTEL_PINK_SALT_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_WALL.get(), GBlocks.PASTEL_PINK_SALT_BRICKS.get());

        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE.get(), GBlocks.LUMIERE_BLOCK.get());

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_SLAB.get(), GBlocks.ALLURITE_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_SLAB.get(), GBlocks.LUMIERE_BLOCK.get(), 2);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_STAIRS.get(), GBlocks.LUMIERE_BLOCK.get());

        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.ALLURITE_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.LUMIERE_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_SLAB.get(), GBlocks.SMOOTH_AMETHYST.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.SMOOTH_ALLURITE.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.SMOOTH_LUMIERE.get(), 2);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.ALLURITE_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_SLAB.get(), GBlocks.LUMIERE_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_SLAB.get(), GBlocks.SMOOTH_AMETHYST.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.SMOOTH_ALLURITE.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_SLAB.get(), GBlocks.SMOOTH_LUMIERE.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_SLAB.get(), GBlocks.AMETHYST_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.ALLURITE_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_SLAB.get(), GBlocks.LUMIERE_BRICKS.get(), 2);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_STAIRS.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.LUMIERE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_STAIRS.get(), GBlocks.SMOOTH_AMETHYST.get());
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.SMOOTH_ALLURITE.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.SMOOTH_LUMIERE.get());
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_STAIRS.get(), GBlocks.AMETHYST_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.ALLURITE_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.LUMIERE_BRICKS.get());

        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.LUMIERE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_STAIRS.get(), GBlocks.SMOOTH_AMETHYST.get());
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.SMOOTH_ALLURITE.get());
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.SMOOTH_LUMIERE.get());

        stonecutterResultFromBase(consumer, GBlocks.CHISELED_AMETHYST.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ALLURITE.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_LUMIERE.get(), GBlocks.LUMIERE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_AMETHYST.get(), GBlocks.SMOOTH_AMETHYST.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ALLURITE.get(), GBlocks.SMOOTH_ALLURITE.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_LUMIERE.get(), GBlocks.SMOOTH_LUMIERE.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_AMETHYST.get(), GBlocks.AMETHYST_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ALLURITE.get(), GBlocks.ALLURITE_BRICKS.get());
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_LUMIERE.get(), GBlocks.LUMIERE_BRICKS.get());

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICKS.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICKS.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICKS.get(), GBlocks.LUMIERE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICKS.get(), GBlocks.SMOOTH_AMETHYST.get());
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICKS.get(), GBlocks.SMOOTH_ALLURITE.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICKS.get(), GBlocks.SMOOTH_LUMIERE.get());

        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL.get(), GBlocks.SILVER_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_STAIRS.get(), GBlocks.SILVER_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_SLAB.get(), GBlocks.SILVER_BLOCK.get(), 4);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES.get(), GBlocks.SILVER_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_STAIRS.get(), GBlocks.SILVER_BLOCK.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_SLAB.get(), GBlocks.SILVER_BLOCK.get(), 4);

        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_STAIRS.get(), GBlocks.SILVER_PANEL.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_SLAB.get(), GBlocks.SILVER_PANEL.get(), 4);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES.get(), GBlocks.SILVER_PANEL.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_STAIRS.get(), GBlocks.SILVER_PANEL.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_SLAB.get(), GBlocks.SILVER_PANEL.get(), 4);

        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_STAIRS.get(), GBlocks.SILVER_TILES.get(), 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_SLAB.get(), GBlocks.SILVER_TILES.get(), 4);

        smithing(consumer, Items.LEATHER_CHESTPLATE, GItems.STERLING_CHESTPLATE.get(), ForgeItemTags.SILVER_INGOT);
        smithing(consumer, Items.LEATHER_HELMET, GItems.STERLING_HELMET.get(), ForgeItemTags.SILVER_INGOT);
        smithing(consumer, Items.LEATHER_LEGGINGS, GItems.STERLING_LEGGINGS.get(), ForgeItemTags.SILVER_INGOT);
        smithing(consumer, Items.LEATHER_BOOTS, GItems.STERLING_BOOTS.get(), ForgeItemTags.SILVER_INGOT);
        smithing(consumer, Items.LEATHER_HORSE_ARMOR, GItems.STERLING_HORSE_ARMOR.get(), ForgeItemTags.SILVER_INGOT);

    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> p_176563_, ItemLike p_176564_, ItemLike p_176565_, String p_176566_, String p_176567_) {
        nineBlockStorageRecipes(p_176563_, p_176564_, p_176565_, p_176566_, p_176567_, getItemName(p_176564_), null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike p_176570_, ItemLike p_176571_, String p_176572_, @Nullable String p_176573_, String name, @Nullable String p_176575_) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, p_176570_, 9).requires(p_176571_).group(p_176575_).unlockedBy(getHasName(p_176571_), has(p_176571_)).save(consumer, new ResourceLocation(Galosphere.MODID, name));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, p_176571_).define('#', p_176570_).pattern("###").pattern("###").pattern("###").group(p_176573_).unlockedBy(getHasName(p_176570_), has(p_176570_)).save(consumer, new ResourceLocation(Galosphere.MODID, p_176572_));
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient) {
        stonecutterResultFromBase(consumer, result, ingredient, 1);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, count).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, new ResourceLocation(Galosphere.MODID, getConversionRecipeName(result, ingredient) + "_stonecutting"));
    }

    protected static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        return getItemName(result) + "_from_" + getItemName(ingredient);
    }

    private void chiseled(Consumer<FinishedRecipe> consumer, Item slab, Item result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(slab).getPath(), has(slab))
                .save(consumer);
    }

    private void shaplessOne(Consumer<FinishedRecipe> consumer, Item result, Item item, int count) {
        ShapelessRecipeBuilder.
                shapeless(RecipeCategory.MISC, result, count)
                .requires(item)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike item) {
        twoXtwo(consumer, result, item.asItem(), 4);
    }

    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike result, Item item, int count) {
        ShapedRecipeBuilder.
                shaped(RecipeCategory.BUILDING_BLOCKS, result, count)
                .define('S', item)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void threeXthree(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(RecipeCategory.MISC, result)
                .define('S', item)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void stairsBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('#', item)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void slabBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', item)
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> consumer, List<ItemLike> items, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, items, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> consumer, List<ItemLike> items, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, items, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> consumer, RecipeSerializer<? extends AbstractCookingRecipe> serializer, List<ItemLike> itemLike, ItemLike item, float experience, int time, String group, String name) {
        for (ItemLike itemlike : itemLike) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), RecipeCategory.MISC, item, experience, time, serializer).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(consumer, new ResourceLocation(Galosphere.MODID, getItemName(item) + name + "_" + getItemName(itemlike)));
        }
    }

    private static void smithing(Consumer<FinishedRecipe> consumer, Item armorItem, Item result, TagKey<Item> ingotItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(armorItem), Ingredient.of(ingotItem), RecipeCategory.COMBAT, result).unlocks("has_silver_ingot", has(Items.NETHERITE_INGOT)).save(consumer, new ResourceLocation(Galosphere.MODID, getItemName(result) + "_smithing"));
    }

    protected static String getHasName(ItemLike p_176603_) {
        return "has_" + getItemName(p_176603_);
    }

    protected static String getItemName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }
}
