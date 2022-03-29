package net.orcinus.galosphere.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.galosphere.init.CTBlocks;
import net.orcinus.galosphere.init.CTItems;

import java.util.List;
import java.util.function.Consumer;

public class CTRecipeProvider extends RecipeProvider {
    private static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(CTBlocks.SILVER_ORE.get().asItem(), CTBlocks.DEEPSLATE_SILVER_ORE.get().asItem(), CTItems.RAW_SILVER.get());

    public CTRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        stairsBlock(consumer, CTBlocks.ALLURITE_STAIRS.get(), CTBlocks.ALLURITE_BLOCK.get().asItem());
        slabBlock(consumer, CTBlocks.ALLURITE_SLAB.get(), CTBlocks.ALLURITE_BLOCK.get().asItem());
        stairsBlock(consumer, CTBlocks.LUMIERE_STAIRS.get(), CTBlocks.LUMIERE_BLOCK.get().asItem());
        slabBlock(consumer, CTBlocks.LUMIERE_SLAB.get(), CTBlocks.LUMIERE_BLOCK.get().asItem());
        stonecutterResultFromBase(consumer, CTBlocks.ALLURITE_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, CTBlocks.ALLURITE_STAIRS.get(), Blocks.AMETHYST_BLOCK, 1);
        stonecutterResultFromBase(consumer, CTBlocks.LUMIERE_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, CTBlocks.LUMIERE_STAIRS.get(), Blocks.AMETHYST_BLOCK, 1);
//        shaplessOne(consumer, CTItems.SILVER_INGOT.get(), CTBlocks.SILVER_BLOCK.get().asItem(), 9);
//        twoXtwo(consumer, CTBlocks.ALLURITE_BLOCK.get(), CTItems.ALLURITE_SHARD.get(), 1);
//        twoXtwo(consumer, CTBlocks.LUMIERE_BLOCK.get(), CTItems.LUMIERE_SHARD.get(), 1);
//        twoXtwo(consumer, CTBlocks.SMOOTH_AMETHYST.get(), Blocks.AMETHYST_BLOCK.asItem(), 4);
//        stairsBlock(consumer, CTBlocks.SMOOTH_AMETHYST_STAIRS.get(), CTBlocks.SMOOTH_AMETHYST.get().asItem());
//        slabBlock(consumer, CTBlocks.SMOOTH_AMETHYST_SLAB.get(), CTBlocks.SMOOTH_AMETHYST.get().asItem());
//        stairsBlock(consumer, CTBlocks.AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK.asItem());
//        slabBlock(consumer, CTBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK.asItem());
//        twoXtwo(consumer, CTBlocks.MYSTERIA_CINDERS.get(), CTBlocks.MYSTERIA_VINES.get().asItem(), 1);
//        threeXthree(consumer, CTBlocks.SILVER_BLOCK.get(), CTItems.SILVER_INGOT.get());
//        threeXthree(consumer, CTBlocks.RAW_SILVER_BLOCK.get(), CTItems.RAW_SILVER.get());
//        oreSmelting(consumer, SILVER_SMELTABLES, CTItems.SILVER_INGOT.get(), 0.7F, 200, "silver_ingot");
//        oreBlasting(consumer, SILVER_SMELTABLES, CTItems.SILVER_INGOT.get(), 0.7F, 100, "silver_ingot");
//        surroundWithFour(consumer, CTBlocks.ALLURITE_BLOCK.get().asItem(), Blocks.REDSTONE_LAMP.asItem(), CTBlocks.ALLURITE_LAMP.get().asItem(), 1);
//        surroundWithFour(consumer, CTBlocks.LUMIERE_BLOCK.get().asItem(), Blocks.REDSTONE_LAMP.asItem(), CTBlocks.LUMIERE_LAMP.get().asItem(), 1);
//        stonecutterResultFromBase(consumer, CTBlocks.SMOOTH_AMETHYST.get(), Blocks.AMETHYST_BLOCK);
//        stonecutterResultFromBase(consumer, CTBlocks.SMOOTH_AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);
//        stonecutterResultFromBase(consumer, CTBlocks.SMOOTH_AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);
//        stonecutterResultFromBase(consumer, CTBlocks.SMOOTH_AMETHYST_SLAB.get(), CTBlocks.SMOOTH_AMETHYST.get(), 2);
//        stonecutterResultFromBase(consumer, CTBlocks.SMOOTH_AMETHYST_STAIRS.get(), CTBlocks.SMOOTH_AMETHYST.get());
//        stonecutterResultFromBase(consumer, CTBlocks.CHISELED_AMETHYST.get(), Blocks.AMETHYST_BLOCK);
//        stonecutterResultFromBase(consumer, CTBlocks.CHISELED_AMETHYST.get(), CTBlocks.SMOOTH_AMETHYST.get());
    }

    private static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient) {
        stonecutterResultFromBase(consumer, result, ingredient, 1);
    }

    private static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), result, count).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getConversionRecipeName(result, ingredient) + "_stonecutting");
    }

    private static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        return getItemName(result) + "_from_" + getItemName(ingredient);
    }

    private void surroundWithFour(Consumer<FinishedRecipe> consumer, Item surroundings, Item midItem, Item result, int count) {
        ShapedRecipeBuilder.shaped(result, count)
                .define('#', surroundings)
                .define('*', midItem)
                .pattern(" # ")
                .pattern("#*#")
                .pattern(" # ")
                .unlockedBy("has_" + surroundings.getRegistryName().getPath(), has(surroundings))
                .unlockedBy("has_" + midItem.getRegistryName().getPath(), has(midItem))
                .save(consumer);
    }

    private void shaplessOne(Consumer<FinishedRecipe> consumer, Item result, Item item, int count) {
        ShapelessRecipeBuilder.
                shapeless(result, count)
                .requires(item)
                .unlockedBy("has_" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private void oneXTwo(Consumer<FinishedRecipe> consumer, ItemLike result, Item item, int count) {
        ShapedRecipeBuilder.shaped(result, count)
                .define('#', item)
                .pattern("#")
                .pattern("#")
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

    private void wallBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result, 6)
                .define('#', item)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private static void oreSmelting(Consumer<FinishedRecipe> p_176592_, List<ItemLike> p_176593_, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(p_176592_, RecipeSerializer.SMELTING_RECIPE, p_176593_, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    private static void oreBlasting(Consumer<FinishedRecipe> p_176626_, List<ItemLike> p_176627_, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(p_176626_, RecipeSerializer.BLASTING_RECIPE, p_176627_, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    private static void oreCooking(Consumer<FinishedRecipe> p_176534_, SimpleCookingSerializer<?> p_176535_, List<ItemLike> itemLike, ItemLike p_176537_, float p_176538_, int p_176539_, String p_176540_, String p_176541_) {
        for(ItemLike itemlike : itemLike) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), p_176537_, p_176538_, p_176539_, p_176535_).group(p_176540_).unlockedBy(getHasName(itemlike), has(itemlike)).save(p_176534_, getItemName(p_176537_) + p_176541_ + "_" + getItemName(itemlike));
        }
    }

    private static String getHasName(ItemLike p_176603_) {
        return "has_" + getItemName(p_176603_);
    }

    private static String getItemName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }
}
