package net.orcinus.galosphere.data;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
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
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.compat.init.CItemTags;
import net.orcinus.galosphere.init.GBlockFamilies;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GRecipeProvider extends FabricRecipeProvider {
    private static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(GBlocks.SILVER_ORE.asItem(), GBlocks.DEEPSLATE_SILVER_ORE.asItem(), GItems.RAW_SILVER);

    public GRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        GBlockFamilies.getAllFamilies().forEach((blockFamily) -> {
            generateRecipes(consumer, blockFamily);
        });
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER).requires(Items.COAL).requires(GItems.PINK_SALT_SHARD).unlockedBy("has_pink_salt_shard", has(GItems.PINK_SALT_SHARD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GBlocks.STRANDED_MEMBRANE_BLOCK)
                        .define('#', GItems.ALLURITE_SHARD)
                        .define('C', GBlocks.CURED_MEMBRANE_BLOCK)
                        .pattern(" # ")
                        .pattern("#C#")
                        .pattern(" # ")
                        .unlockedBy("has_cured_membrane_block", has(GBlocks.CURED_MEMBRANE_BLOCK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GBlocks.SHADOW_FRAME)
                .define('S', GItems.SILVER_INGOT)
                .define('#', GItems.CURED_MEMBRANE)
                .pattern("S#S")
                .pattern("#S#")
                .pattern("S#S")
                .unlockedBy("has_cured_membrane", has(GItems.CURED_MEMBRANE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GBlocks.PINK_SALT_LAMP).define('#', GBlocks.PINK_SALT_CLUSTER).define('S', GItems.SILVER_INGOT).pattern("#").pattern("S").unlockedBy("has_pink_salt_cluster", has(GBlocks.PINK_SALT_CLUSTER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, GItems.SALTED_JERKY).define('S', GItems.PINK_SALT_SHARD).define('#', Items.ROTTEN_FLESH).pattern(" S ").pattern("S#S").pattern(" S ").unlockedBy("has_pink_salt_shard", has(GItems.PINK_SALT_SHARD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GItems.CURED_MEMBRANE).define('S', GItems.PINK_SALT_SHARD).define('#', Items.PHANTOM_MEMBRANE).pattern(" S ").pattern("S#S").pattern(" S ").unlockedBy("has_pink_salt_shard", has(GItems.PINK_SALT_SHARD)).save(consumer);
        twoXtwo(consumer, GBlocks.CURED_MEMBRANE_BLOCK, GItems.CURED_MEMBRANE);
        twoXtwo(consumer, GBlocks.PINK_SALT_BRICKS, GBlocks.POLISHED_PINK_SALT.asItem(), 4);
        twoXtwo(consumer, GBlocks.ROSE_PINK_SALT_BRICKS, GBlocks.POLISHED_ROSE_PINK_SALT.asItem(), 4);
        twoXtwo(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS, GBlocks.POLISHED_PASTEL_PINK_SALT.asItem(), 4);
        twoXtwo(consumer, GBlocks.ALLURITE_BLOCK, GItems.ALLURITE_SHARD, 1);
        twoXtwo(consumer, GBlocks.LUMIERE_BLOCK, GItems.LUMIERE_SHARD, 1);
        twoXtwo(consumer, GBlocks.SMOOTH_AMETHYST, Items.AMETHYST_BLOCK, 4);
        twoXtwo(consumer, GBlocks.SMOOTH_ALLURITE, GBlocks.ALLURITE_BLOCK.asItem(), 4);
        twoXtwo(consumer, GBlocks.SMOOTH_LUMIERE, GBlocks.LUMIERE_BLOCK.asItem(), 4);
        twoXtwo(consumer, GBlocks.AMETHYST_BRICKS, GBlocks.SMOOTH_AMETHYST.asItem(), 4);
        twoXtwo(consumer, GBlocks.ALLURITE_BRICKS, GBlocks.SMOOTH_ALLURITE.asItem(), 4);
        twoXtwo(consumer, GBlocks.LUMIERE_BRICKS, GBlocks.SMOOTH_LUMIERE.asItem(), 4);
        twoXtwo(consumer, GBlocks.SILVER_PANEL, GBlocks.SILVER_BLOCK.asItem(), 4);
        twoXtwo(consumer, GBlocks.SILVER_TILES, GBlocks.SILVER_PANEL.asItem(), 4);
        threeXthree(consumer, GBlocks.SILVER_BLOCK, GItems.SILVER_INGOT);
        threeXthree(consumer, GBlocks.RAW_SILVER_BLOCK, GItems.RAW_SILVER);
        stairsBlock(consumer, GBlocks.AMETHYST_STAIRS, Items.AMETHYST_BLOCK);
        slabBlock(consumer, GBlocks.AMETHYST_SLAB, Items.AMETHYST_BLOCK);
        stairsBlock(consumer, GBlocks.SILVER_PANEL_STAIRS, GBlocks.SILVER_PANEL.asItem());
        slabBlock(consumer, GBlocks.SILVER_PANEL_SLAB, GBlocks.SILVER_PANEL.asItem());
        stairsBlock(consumer, GBlocks.SILVER_TILES_STAIRS, GBlocks.SILVER_TILES.asItem());
        slabBlock(consumer, GBlocks.SILVER_TILES_SLAB, GBlocks.SILVER_TILES.asItem());
        stairsBlock(consumer, GBlocks.ALLURITE_STAIRS, GBlocks.ALLURITE_BLOCK.asItem());
        slabBlock(consumer, GBlocks.ALLURITE_SLAB, GBlocks.ALLURITE_BLOCK.asItem());
        stairsBlock(consumer, GBlocks.LUMIERE_STAIRS, GBlocks.LUMIERE_BLOCK.asItem());
        slabBlock(consumer, GBlocks.LUMIERE_SLAB, GBlocks.LUMIERE_BLOCK.asItem());
        stairsBlock(consumer, GBlocks.SMOOTH_AMETHYST_STAIRS, GBlocks.SMOOTH_AMETHYST.asItem());
        slabBlock(consumer, GBlocks.SMOOTH_AMETHYST_SLAB, GBlocks.SMOOTH_AMETHYST.asItem());
        stairsBlock(consumer, GBlocks.SMOOTH_ALLURITE_STAIRS, GBlocks.SMOOTH_ALLURITE.asItem());
        slabBlock(consumer, GBlocks.SMOOTH_ALLURITE_SLAB, GBlocks.SMOOTH_ALLURITE.asItem());
        stairsBlock(consumer, GBlocks.SMOOTH_LUMIERE_STAIRS, GBlocks.SMOOTH_LUMIERE.asItem());
        slabBlock(consumer, GBlocks.SMOOTH_LUMIERE_SLAB, GBlocks.SMOOTH_LUMIERE.asItem());
        stairsBlock(consumer, GBlocks.AMETHYST_BRICK_STAIRS, GBlocks.AMETHYST_BRICKS.asItem());
        slabBlock(consumer, GBlocks.AMETHYST_BRICK_SLAB, GBlocks.AMETHYST_BRICKS.asItem());
        stairsBlock(consumer, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.ALLURITE_BRICKS.asItem());
        slabBlock(consumer, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.ALLURITE_BRICKS.asItem());
        stairsBlock(consumer, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.LUMIERE_BRICKS.asItem());
        slabBlock(consumer, GBlocks.LUMIERE_BRICK_SLAB, GBlocks.LUMIERE_BRICKS.asItem());
        chiseled(consumer, GBlocks.AMETHYST_SLAB.asItem(), GBlocks.CHISELED_AMETHYST.asItem());
        chiseled(consumer, GBlocks.ALLURITE_SLAB.asItem(), GBlocks.CHISELED_ALLURITE.asItem());
        chiseled(consumer, GBlocks.LUMIERE_SLAB.asItem(), GBlocks.CHISELED_LUMIERE.asItem());

        nineBlockStorageRecipesWithCustomPacking(consumer, GItems.SILVER_NUGGET, GItems.SILVER_INGOT, "silver_ingot_from_nuggets", "silver_ingot");

        oreSmelting(consumer, SILVER_SMELTABLES, GItems.SILVER_INGOT, 0.7F, 200, "silver_ingot");
        oreBlasting(consumer, SILVER_SMELTABLES, GItems.SILVER_INGOT, 0.7F, 100, "silver_ingot");

        shaplessOne(consumer, GItems.SILVER_INGOT, GBlocks.SILVER_BLOCK.asItem(), 9);
        shaplessOne(consumer, GItems.RAW_SILVER, GBlocks.RAW_SILVER_BLOCK.asItem(), 9);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GItems.SILVER_UPGRADE_SMITHING_TEMPLATE, 2)
                .define('#', ItemTags.PLANKS)
                .define('C', GItems.SILVER_INGOT)
                .define('S', GItems.SILVER_UPGRADE_SMITHING_TEMPLATE)
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###")
                .unlockedBy("has_silver_upgrade_smithing_template", has(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GItems.BAROMETER)
                .define('#', GItems.SILVER_INGOT)
                .define('@', Items.REDSTONE)
                .pattern(" # ")
                .pattern("#@#")
                .pattern(" # ")
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GBlocks.SILVER_LATTICE, 16)
                .define('#', GItems.SILVER_INGOT)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_silver_ingot", has(GItems.SILVER_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GItems.GLOW_FLARE, 4)
                .define('#', Items.GLOW_INK_SAC)
                .define('S', ConventionalItemTags.COPPER_INGOTS)
                .define('F', Items.STICK)
                .pattern(" # ")
                .pattern(" S ")
                .pattern(" F ")
                .unlockedBy("has_glow_ink_sac", has(Items.GLOW_INK_SAC))
                .unlockedBy("has_copper_ingot", has(ConventionalItemTags.COPPER_INGOTS)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GItems.SPECTRE_FLARE)
                .define('#', CItemTags.SILVER_INGOTS)
                .define('S', GItems.BOTTLE_OF_SPECTRE)
                .pattern("S")
                .pattern("#")
                .unlockedBy("has_silver_ingot", has(CItemTags.SILVER_INGOTS))
                .unlockedBy("has_bottle_of_spectre", has(GItems.BOTTLE_OF_SPECTRE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.FOOD, GItems.GOLDEN_LICHEN_CORDYCEPS)
                .define('#', Items.GOLD_NUGGET)
                .define('@', GItems.LICHEN_CORDYCEPS)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .unlockedBy("has_lichen_cordyceps", has(GItems.LICHEN_CORDYCEPS)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.CHANDELIER)
                .define('#', ConventionalItemTags.COPPER_INGOTS)
                .define('@', GItems.BOTTLE_OF_SPECTRE)
                .define('C', ConventionalItemTags.IRON_INGOTS)
                .pattern("@")
                .pattern("C")
                .pattern("#")
                .unlockedBy("has_copper_ingot", has(ConventionalItemTags.COPPER_INGOTS))
                .unlockedBy("has_iron_nugget", has(ConventionalItemTags.IRON_INGOTS))
                .unlockedBy("has_bottle_of_spectre", has(GItems.BOTTLE_OF_SPECTRE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.COMBUSTION_TABLE)
                .define('#', ItemTags.PLANKS)
                .define('@', CItemTags.SILVER_INGOTS)
                .pattern("@@")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_silver_ingot", has(CItemTags.SILVER_INGOTS)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.AMETHYST_LAMP)
                .define('R', Items.AMETHYST_SHARD)
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.ALLURITE_LAMP)
                .define('R', GItems.ALLURITE_SHARD)
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_allurite_shard", has(GItems.ALLURITE_SHARD))
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.LUMIERE_LAMP)
                .define('R', GItems.LUMIERE_SHARD)
                .define('G', Blocks.GLOWSTONE)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_lumiere_shard", has(GItems.LUMIERE_SHARD))
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.COMBAT, GItems.SILVER_BOMB, 2)
                .define('R', CItemTags.SILVER_INGOTS)
                .define('G', Items.GUNPOWDER)
                .pattern(" R ")
                .pattern("RGR")
                .pattern(" R ")
                .unlockedBy("has_gunpowder", has(Items.GUNPOWDER)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.MONSTROMETER)
                .define('S', CItemTags.SILVER_BLOCKS)
                .define('C', GBlocks.LUMIERE_BLOCK)
                .pattern("SSS")
                .pattern("CCC")
                .pattern("SSS")
                .unlockedBy("has_lumiere_block", has(GBlocks.LUMIERE_BLOCK))
                .unlockedBy("has_silver_block", has(CItemTags.SILVER_BLOCKS)).save(consumer);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, GBlocks.WARPED_ANCHOR)
                .define('S', CItemTags.SILVER_BLOCKS)
                .define('C', GBlocks.ALLURITE_BLOCK)
                .pattern("CCC")
                .pattern("SSS")
                .unlockedBy("has_allurite_block", has(GBlocks.ALLURITE_BLOCK))
                .unlockedBy("has_silver_block", has(CItemTags.SILVER_BLOCKS)).save(consumer);

        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_STAIRS, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_SLAB, GBlocks.PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_WALL, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_STAIRS, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_SLAB, GBlocks.PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_WALL, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICKS, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_STAIRS, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_SLAB, GBlocks.PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_WALL, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_PINK_SALT, GBlocks.PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_STAIRS, GBlocks.POLISHED_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_SLAB, GBlocks.POLISHED_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PINK_SALT_WALL, GBlocks.POLISHED_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICKS, GBlocks.POLISHED_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_STAIRS, GBlocks.POLISHED_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_SLAB, GBlocks.POLISHED_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_WALL, GBlocks.POLISHED_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICKS, GBlocks.PINK_SALT_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_STAIRS, GBlocks.PINK_SALT_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_SLAB, GBlocks.PINK_SALT_BRICKS, 2);
        stonecutterResultFromBase(consumer, GBlocks.PINK_SALT_BRICK_WALL, GBlocks.PINK_SALT_BRICKS);

        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_STAIRS, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_SLAB, GBlocks.ROSE_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_WALL, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_SLAB, GBlocks.ROSE_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_WALL, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICKS, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_SLAB, GBlocks.ROSE_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_WALL, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ROSE_PINK_SALT, GBlocks.ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS, GBlocks.POLISHED_ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_SLAB, GBlocks.POLISHED_ROSE_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_ROSE_PINK_SALT_WALL, GBlocks.POLISHED_ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICKS, GBlocks.POLISHED_ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS, GBlocks.POLISHED_ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_SLAB, GBlocks.POLISHED_ROSE_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_WALL, GBlocks.POLISHED_ROSE_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICKS, GBlocks.ROSE_PINK_SALT_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS, GBlocks.ROSE_PINK_SALT_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_SLAB, GBlocks.ROSE_PINK_SALT_BRICKS, 2);
        stonecutterResultFromBase(consumer, GBlocks.ROSE_PINK_SALT_BRICK_WALL, GBlocks.ROSE_PINK_SALT_BRICKS);

        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_STAIRS, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_SLAB, GBlocks.PASTEL_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_WALL, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB, GBlocks.PASTEL_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_WALL, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB, GBlocks.PASTEL_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_WALL, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_PASTEL_PINK_SALT, GBlocks.PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS, GBlocks.POLISHED_PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB, GBlocks.POLISHED_PASTEL_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.POLISHED_PASTEL_PINK_SALT_WALL, GBlocks.POLISHED_PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS, GBlocks.POLISHED_PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS, GBlocks.POLISHED_PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB, GBlocks.POLISHED_PASTEL_PINK_SALT, 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_WALL, GBlocks.POLISHED_PASTEL_PINK_SALT);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICKS, GBlocks.PASTEL_PINK_SALT_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS, GBlocks.PASTEL_PINK_SALT_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB, GBlocks.PASTEL_PINK_SALT_BRICKS, 2);
        stonecutterResultFromBase(consumer, GBlocks.PASTEL_PINK_SALT_BRICK_WALL, GBlocks.PASTEL_PINK_SALT_BRICKS);

        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST, Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE, GBlocks.ALLURITE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE, GBlocks.LUMIERE_BLOCK);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_SLAB, Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_SLAB, GBlocks.ALLURITE_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_SLAB, GBlocks.LUMIERE_BLOCK, 2);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_STAIRS, Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_STAIRS, GBlocks.ALLURITE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_STAIRS, GBlocks.LUMIERE_BLOCK);

        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_SLAB, Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_SLAB, GBlocks.ALLURITE_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_SLAB, GBlocks.LUMIERE_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_SLAB, GBlocks.SMOOTH_AMETHYST, 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_SLAB, GBlocks.SMOOTH_ALLURITE, 2);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_SLAB, GBlocks.SMOOTH_LUMIERE, 2);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_SLAB, Blocks.AMETHYST_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.ALLURITE_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_SLAB, GBlocks.LUMIERE_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_SLAB, GBlocks.SMOOTH_AMETHYST, 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.SMOOTH_ALLURITE, 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_SLAB, GBlocks.SMOOTH_LUMIERE, 2);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_SLAB, GBlocks.AMETHYST_BRICKS, 2);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.ALLURITE_BRICKS, 2);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_SLAB, GBlocks.LUMIERE_BRICKS, 2);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_STAIRS, Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.ALLURITE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.LUMIERE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_STAIRS, GBlocks.SMOOTH_AMETHYST);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.SMOOTH_ALLURITE);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.SMOOTH_LUMIERE);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICK_STAIRS, GBlocks.AMETHYST_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.ALLURITE_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.LUMIERE_BRICKS);

        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_STAIRS, Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_STAIRS, GBlocks.ALLURITE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_STAIRS, GBlocks.LUMIERE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_AMETHYST_STAIRS, GBlocks.SMOOTH_AMETHYST);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_ALLURITE_STAIRS, GBlocks.SMOOTH_ALLURITE);
        stonecutterResultFromBase(consumer, GBlocks.SMOOTH_LUMIERE_STAIRS, GBlocks.SMOOTH_LUMIERE);

        stonecutterResultFromBase(consumer, GBlocks.CHISELED_AMETHYST, Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ALLURITE, GBlocks.ALLURITE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_LUMIERE, GBlocks.LUMIERE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_AMETHYST, GBlocks.SMOOTH_AMETHYST);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ALLURITE, GBlocks.SMOOTH_ALLURITE);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_LUMIERE, GBlocks.SMOOTH_LUMIERE);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_AMETHYST, GBlocks.AMETHYST_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_ALLURITE, GBlocks.ALLURITE_BRICKS);
        stonecutterResultFromBase(consumer, GBlocks.CHISELED_LUMIERE, GBlocks.LUMIERE_BRICKS);

        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICKS, Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICKS, GBlocks.ALLURITE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICKS, GBlocks.LUMIERE_BLOCK);
        stonecutterResultFromBase(consumer, GBlocks.AMETHYST_BRICKS, GBlocks.SMOOTH_AMETHYST);
        stonecutterResultFromBase(consumer, GBlocks.ALLURITE_BRICKS, GBlocks.SMOOTH_ALLURITE);
        stonecutterResultFromBase(consumer, GBlocks.LUMIERE_BRICKS, GBlocks.SMOOTH_LUMIERE);

        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL, GBlocks.SILVER_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_STAIRS, GBlocks.SILVER_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_SLAB, GBlocks.SILVER_BLOCK, 4);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES, GBlocks.SILVER_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_STAIRS, GBlocks.SILVER_BLOCK, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_SLAB, GBlocks.SILVER_BLOCK, 4);

        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_STAIRS, GBlocks.SILVER_PANEL, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_PANEL_SLAB, GBlocks.SILVER_PANEL, 4);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES, GBlocks.SILVER_PANEL, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_STAIRS, GBlocks.SILVER_PANEL, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_SLAB, GBlocks.SILVER_PANEL, 4);

        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_STAIRS, GBlocks.SILVER_TILES, 2);
        stonecutterResultFromBase(consumer, GBlocks.SILVER_TILES_SLAB, GBlocks.SILVER_TILES, 4);

        smithing(consumer, Items.LEATHER_CHESTPLATE, GItems.STERLING_CHESTPLATE, CItemTags.SILVER_INGOTS);
        smithing(consumer, Items.LEATHER_HELMET, GItems.STERLING_HELMET, CItemTags.SILVER_INGOTS);
        smithing(consumer, Items.LEATHER_LEGGINGS, GItems.STERLING_LEGGINGS, CItemTags.SILVER_INGOTS);
        smithing(consumer, Items.LEATHER_BOOTS, GItems.STERLING_BOOTS, CItemTags.SILVER_INGOTS);
        smithing(consumer, Items.LEATHER_HORSE_ARMOR, GItems.STERLING_HORSE_ARMOR, CItemTags.SILVER_INGOTS);

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

    public static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
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
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(armorItem), Ingredient.of(ingotItem), RecipeCategory.COMBAT, result).unlocks("has_silver_ingot", has(Items.NETHERITE_INGOT)).save(consumer, new ResourceLocation(Galosphere.MODID, getItemName(result) + "_smithing"));
    }

    public static String getHasName(ItemLike p_176603_) {
        return "has_" + getItemName(p_176603_);
    }

    public static String getItemName(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }
}
