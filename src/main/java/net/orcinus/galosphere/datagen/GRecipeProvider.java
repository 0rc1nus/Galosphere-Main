package net.orcinus.galosphere.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class GRecipeProvider extends RecipeProvider {
    private static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(GBlocks.SILVER_ORE.get().asItem(), GBlocks.DEEPSLATE_SILVER_ORE.get().asItem(), GItems.RAW_SILVER.get());

    public GRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        twoXtwo(consumer, GBlocks.ALLURITE_BLOCK.get(), GItems.ALLURITE_SHARD.get(), 1);
        twoXtwo(consumer, GBlocks.LUMIERE_BLOCK.get(), GItems.LUMIERE_SHARD.get(), 1);
        twoXtwo(consumer, GBlocks.SMOOTH_AMETHYST.get(), Items.AMETHYST_BLOCK, 4);
        twoXtwo(consumer, GBlocks.SMOOTH_ALLURITE.get(), GBlocks.ALLURITE_BLOCK.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.SMOOTH_LUMIERE.get(), GBlocks.LUMIERE_BLOCK.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.AMETHYST_BRICKS.get(), GBlocks.SMOOTH_AMETHYST.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.ALLURITE_BRICKS.get(), GBlocks.SMOOTH_ALLURITE.get().asItem(), 4);
        twoXtwo(consumer, GBlocks.LUMIERE_BRICKS.get(), GBlocks.SMOOTH_LUMIERE.get().asItem(), 4);
        threeXthree(consumer, GBlocks.SILVER_BLOCK.get(), GItems.SILVER_INGOT.get());
        threeXthree(consumer, GBlocks.RAW_SILVER_BLOCK.get(), GItems.RAW_SILVER.get());
        stairsBlock(consumer, GBlocks.AMETHYST_STAIRS.get(), Items.AMETHYST_BLOCK);
        slabBlock(consumer, GBlocks.AMETHYST_SLAB.get(), Items.AMETHYST_BLOCK);
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
        chiseled(consumer, GBlocks.AMETHYST_SLAB.get().asItem(), GBlocks.CHISELED_AMETHYST.get().asItem());
        chiseled(consumer, GBlocks.ALLURITE_SLAB.get().asItem(), GBlocks.CHISELED_ALLURITE.get().asItem());
        chiseled(consumer, GBlocks.LUMIERE_SLAB.get().asItem(), GBlocks.CHISELED_LUMIERE.get().asItem());

        nineBlockStorageRecipesWithCustomPacking(consumer, GItems.SILVER_NUGGET.get(), GItems.SILVER_INGOT.get(), "silver_ingot_from_nuggets", "silver_ingot");

        oreSmelting(consumer, SILVER_SMELTABLES, GItems.SILVER_INGOT.get(), 0.7F, 200, "silver_ingot");
        oreBlasting(consumer, SILVER_SMELTABLES, GItems.SILVER_INGOT.get(), 0.7F, 100, "silver_ingot");

        shaplessOne(consumer, GItems.SILVER_INGOT.get(), GBlocks.SILVER_BLOCK.get().asItem(), 9);
        shaplessOne(consumer, GItems.RAW_SILVER.get(), GBlocks.RAW_SILVER_BLOCK.get().asItem(), 9);

        ShapedRecipeBuilder
                .shaped(GBlocks.COMBUSTION_TABLE.get())
                .define('#', ItemTags.PLANKS)
                .define('@', GItems.SILVER_INGOT.get().asItem())
                .pattern("@@")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_silver_ingot", has(GItems.SILVER_INGOT.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(GBlocks.AMETHYST_LAMP.get())
                .define('R', Items.AMETHYST_SHARD)
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(GBlocks.ALLURITE_LAMP.get())
                .define('R', GItems.ALLURITE_SHARD.get())
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(GBlocks.LUMIERE_LAMP.get())
                .define('R', GItems.LUMIERE_SHARD.get())
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(GItems.SILVER_BOMB.get())
                .define('R', GItems.SILVER_INGOT.get())
                .define('G', Items.GUNPOWDER)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_gunpowder", has(Items.GUNPOWDER)).save(consumer);

        ShapedRecipeBuilder
                .shaped(GBlocks.AURA_RINGER.get())
                .define('S', GBlocks.SILVER_BLOCK.get())
                .define('C', GBlocks.ALLURITE_BLOCK.get())
                .pattern("SSS")
                .pattern("CCC")
                .pattern("SSS")
                .unlockedBy("has_silver_block", has(GBlocks.SILVER_BLOCK.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(GBlocks.WARPED_ANCHOR.get())
                .define('S', GBlocks.SILVER_BLOCK.get())
                .define('C', GBlocks.ALLURITE_BLOCK.get())
                .pattern("CCC")
                .pattern("SSS")
                .unlockedBy("has_silver_block", has(GBlocks.SILVER_BLOCK.get())).save(consumer);

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

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICKS.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICKS.get(), GBlocks.ALLURITE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICKS.get(), GBlocks.LUMIERE_BLOCK.get());
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICKS.get(), GBlocks.SMOOTH_AMETHYST.get());
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICKS.get(), GBlocks.SMOOTH_ALLURITE.get());
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICKS.get(), GBlocks.SMOOTH_LUMIERE.get());

        smithing(consumer, Items.LEATHER_CHESTPLATE, GItems.STERLING_CHESTPLATE.get(), GItems.SILVER_INGOT.get());
        smithing(consumer, Items.LEATHER_HELMET, GItems.STERLING_HELMET.get(), GItems.SILVER_INGOT.get());
        smithing(consumer, Items.LEATHER_LEGGINGS, GItems.STERLING_LEGGINGS.get(), GItems.SILVER_INGOT.get());
        smithing(consumer, Items.LEATHER_BOOTS, GItems.STERLING_BOOTS.get(), GItems.SILVER_INGOT.get());
        smithing(consumer, Items.LEATHER_HORSE_ARMOR, GItems.STERLING_HORSE_ARMOR.get(), GItems.SILVER_INGOT.get());

    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> p_176563_, ItemLike p_176564_, ItemLike p_176565_, String p_176566_, String p_176567_) {
        nineBlockStorageRecipes(p_176563_, p_176564_, p_176565_, p_176566_, p_176567_, getItemName(p_176564_), null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike p_176570_, ItemLike p_176571_, String p_176572_, @Nullable String p_176573_, String name, @Nullable String p_176575_) {
        ShapelessRecipeBuilder.shapeless(p_176570_, 9).requires(p_176571_).group(p_176575_).unlockedBy(getHasName(p_176571_), has(p_176571_)).save(consumer, new ResourceLocation(Galosphere.MODID, name));
        ShapedRecipeBuilder.shaped(p_176571_).define('#', p_176570_).pattern("###").pattern("###").pattern("###").group(p_176573_).unlockedBy(getHasName(p_176570_), has(p_176570_)).save(consumer, new ResourceLocation(Galosphere.MODID, p_176572_));
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient) {
        stonecutterResultFromBase(consumer, result, ingredient, 1);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), result, count).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, new ResourceLocation(Galosphere.MODID, getConversionRecipeName(result, ingredient) + "_stonecutting"));
    }

    protected static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        return getItemName(result) + "_from_" + getItemName(ingredient);
    }

    private void chiseled(Consumer<FinishedRecipe> consumer, Item slab, Item result) {
        ShapedRecipeBuilder.shaped(result, 1)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + slab.getRegistryName().getPath(), has(slab))
                .save(consumer);
    }

    private void shaplessOne(Consumer<FinishedRecipe> consumer, Item result, Item item, int count) {
        ShapelessRecipeBuilder.
                shapeless(result, count)
                .requires(item)
                .unlockedBy("has_" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike result, Item item, int count) {
        ShapedRecipeBuilder.
                shaped(result, count)
                .define('S', item)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private void threeXthree(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result)
                .define('S', item)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private void stairsBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result, 4)
                .define('#', item)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private void slabBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result, 6)
                .define('#', item)
                .pattern("###")
                .unlockedBy("has" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> consumer, List<ItemLike> items, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, items, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> consumer, List<ItemLike> items, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, items, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> consumer, SimpleCookingSerializer<?> serializer, List<ItemLike> itemLike, ItemLike item, float experience, int time, String group, String name) {
        for (ItemLike itemlike : itemLike) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), item, experience, time, serializer).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(consumer, new ResourceLocation(Galosphere.MODID, getItemName(item) + name + "_" + getItemName(itemlike)));
        }
    }

    private static void smithing(Consumer<FinishedRecipe> consumer, Item firstItem, Item secondItem, Item combinationItem) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(firstItem), Ingredient.of(combinationItem), secondItem).unlocks("has_netherite_ingot", has(combinationItem)).save(consumer, new ResourceLocation(Galosphere.MODID, getItemName(secondItem) + "_smithing"));
    }

    protected static String getHasName(ItemLike p_176603_) {
        return "has_" + getItemName(p_176603_);
    }

    protected static String getItemName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }
}
