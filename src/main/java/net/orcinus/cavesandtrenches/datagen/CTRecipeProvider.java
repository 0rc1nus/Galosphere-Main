package net.orcinus.cavesandtrenches.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTItems;

import java.util.function.Consumer;

public class CTRecipeProvider extends RecipeProvider {

    public CTRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        shaplessOne(consumer, CTItems.SILVER_INGOT.get(), CTBlocks.SILVER_BLOCK.get().asItem());
        twoXtwo(consumer, CTBlocks.ALLURITE_BLOCK.get(), CTItems.ALLURITE_SHARD.get());
        twoXtwo(consumer, CTBlocks.LUMIERE_BLOCK.get(), CTItems.LUMIERE_SHARD.get());
        twoXtwo(consumer, CTBlocks.POLISHED_AMETHYST.get(), Blocks.AMETHYST_BLOCK.asItem());
        stairsBlock(consumer, CTBlocks.POLISHED_AMETHYST_STAIRS.get(), CTBlocks.POLISHED_AMETHYST.get().asItem());
        stairsBlock(consumer, CTBlocks.AMETHYST_BRICKS_STAIRS.get(), CTBlocks.AMETHYST_BRICKS.get().asItem());
        slabBlock(consumer, CTBlocks.POLISHED_AMETHYST_SLAB.get(), CTBlocks.POLISHED_AMETHYST.get().asItem());
        slabBlock(consumer, CTBlocks.AMETHYST_BRICKS_SLAB.get(), CTBlocks.AMETHYST_BRICKS.get().asItem());
        twoXtwo(consumer, CTBlocks.AMETHYST_BRICKS.get(), CTBlocks.POLISHED_AMETHYST.get().asItem());
        threeXthree(consumer, CTBlocks.SILVER_BLOCK.get(), CTItems.SILVER_INGOT.get());
        threeXthree(consumer, CTBlocks.RAW_SILVER_BLOCK.get(), CTItems.RAW_SILVER.get());
    }

    private void shaplessOne(Consumer<FinishedRecipe> consumer, Item result, Item item) {
        ShapelessRecipeBuilder.
                shapeless(result)
                .requires(item)
                .unlockedBy("has_" + item.getRegistryName().getPath(), has(item)).save(consumer);
    }

    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result)
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
}
