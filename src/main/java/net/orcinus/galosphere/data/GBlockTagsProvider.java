package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;

import java.util.concurrent.CompletableFuture;

public class GBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public GBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        GBlocks.BLOCKS.values().stream().filter(StairBlock.class::isInstance).forEach(block -> {
            this.getOrCreateTagBuilder(BlockTags.STAIRS).add(block);
        });
        GBlocks.BLOCKS.values().stream().filter(SlabBlock.class::isInstance).forEach(block -> {
            this.getOrCreateTagBuilder(BlockTags.SLABS).add(block);
        });
        GBlocks.BLOCKS.values().stream().filter(WallBlock.class::isInstance).forEach(block -> {
            this.getOrCreateTagBuilder(BlockTags.WALLS).add(block);
        });
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(GBlocks.SILVER_ORE, GBlocks.SILVER_BLOCK, GBlocks.DEEPSLATE_SILVER_ORE, GBlocks.AMETHYST_SLAB, GBlocks.AMETHYST_STAIRS, GBlocks.ALLURITE_BLOCK, GBlocks.ALLURITE_SLAB, GBlocks.ALLURITE_STAIRS, GBlocks.LUMIERE_BLOCK, GBlocks.LUMIERE_SLAB, GBlocks.LUMIERE_STAIRS, GBlocks.SMOOTH_AMETHYST, GBlocks.SMOOTH_AMETHYST_SLAB, GBlocks.SMOOTH_AMETHYST_STAIRS, GBlocks.AMETHYST_BRICKS, GBlocks.AMETHYST_BRICK_SLAB, GBlocks.AMETHYST_BRICK_STAIRS, GBlocks.CHISELED_AMETHYST, GBlocks.SMOOTH_ALLURITE, GBlocks.SMOOTH_ALLURITE_SLAB, GBlocks.SMOOTH_ALLURITE_STAIRS, GBlocks.ALLURITE_BRICKS, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.CHISELED_ALLURITE, GBlocks.SMOOTH_LUMIERE, GBlocks.SMOOTH_LUMIERE_SLAB, GBlocks.SMOOTH_LUMIERE_STAIRS, GBlocks.LUMIERE_BRICKS, GBlocks.LUMIERE_BRICK_SLAB, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.CHISELED_LUMIERE, GBlocks.MONSTROMETER, GBlocks.WARPED_ANCHOR, GBlocks.ALLURITE_CLUSTER, GBlocks.LUMIERE_CLUSTER, GBlocks.RAW_SILVER_BLOCK, GBlocks.COMBUSTION_TABLE, GBlocks.CHARGED_LUMIERE_BLOCK, GBlocks.AMETHYST_LAMP, GBlocks.ALLURITE_LAMP, GBlocks.LUMIERE_LAMP, GBlocks.CHANDELIER, GBlocks.SILVER_TILES, GBlocks.SILVER_TILES_SLAB, GBlocks.SILVER_TILES_STAIRS, GBlocks.SILVER_PANEL, GBlocks.SILVER_PANEL_SLAB, GBlocks.SILVER_PANEL_STAIRS, GBlocks.SILVER_LATTICE, GBlocks.GLOW_BERRIES_SILVER_LATTICE, GBlocks.GLINTED_ALLURITE_CLUSTER, GBlocks.GLINTED_LUMIERE_CLUSTER, GBlocks.GLINTED_AMETHYST_CLUSTER, GBlocks.PINK_SALT, GBlocks.ROSE_PINK_SALT, GBlocks.PASTEL_PINK_SALT, GBlocks.PINK_SALT_STAIRS, GBlocks.ROSE_PINK_SALT_STAIRS, GBlocks.PASTEL_PINK_SALT_STAIRS, GBlocks.PINK_SALT_SLAB, GBlocks.ROSE_PINK_SALT_SLAB, GBlocks.PASTEL_PINK_SALT_SLAB, GBlocks.PINK_SALT_WALL, GBlocks.ROSE_PINK_SALT_WALL, GBlocks.PASTEL_PINK_SALT_WALL,
                GBlocks.POLISHED_PINK_SALT, GBlocks.POLISHED_ROSE_PINK_SALT, GBlocks.POLISHED_PASTEL_PINK_SALT, GBlocks.POLISHED_PINK_SALT_STAIRS, GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS, GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS, GBlocks.POLISHED_PINK_SALT_SLAB, GBlocks.POLISHED_ROSE_PINK_SALT_SLAB, GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB, GBlocks.POLISHED_PINK_SALT_WALL, GBlocks.POLISHED_ROSE_PINK_SALT_WALL, GBlocks.POLISHED_PASTEL_PINK_SALT_WALL,
                GBlocks.PINK_SALT_BRICKS, GBlocks.ROSE_PINK_SALT_BRICKS, GBlocks.PASTEL_PINK_SALT_BRICKS, GBlocks.PINK_SALT_BRICK_STAIRS, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS, GBlocks.PINK_SALT_BRICK_SLAB, GBlocks.ROSE_PINK_SALT_BRICK_SLAB, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB, GBlocks.PINK_SALT_BRICK_WALL, GBlocks.ROSE_PINK_SALT_BRICK_WALL, GBlocks.PASTEL_PINK_SALT_BRICK_WALL,
                GBlocks.PINK_SALT_CLUSTER, GBlocks.PINK_SALT_STRAW, GBlocks.PINK_SALT_LAMP, GBlocks.POTPOURRI);
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(GBlocks.LUMIERE_COMPOSTER, GBlocks.SALINE_COMPOSTER);
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE).add(GBlocks.LICHEN_MOSS);
        this.getOrCreateTagBuilder(BlockTags.CRYSTAL_SOUND_BLOCKS).add(GBlocks.AMETHYST_SLAB, GBlocks.AMETHYST_STAIRS, GBlocks.SMOOTH_AMETHYST, GBlocks.SMOOTH_AMETHYST_SLAB, GBlocks.SMOOTH_AMETHYST_STAIRS, GBlocks.SMOOTH_ALLURITE, GBlocks.SMOOTH_ALLURITE_SLAB, GBlocks.SMOOTH_ALLURITE_STAIRS, GBlocks.SMOOTH_LUMIERE, GBlocks.SMOOTH_LUMIERE_SLAB, GBlocks.SMOOTH_LUMIERE_STAIRS, GBlocks.AMETHYST_BRICKS, GBlocks.AMETHYST_BRICK_SLAB, GBlocks.AMETHYST_BRICK_STAIRS, GBlocks.ALLURITE_BRICKS, GBlocks.ALLURITE_BRICK_SLAB, GBlocks.ALLURITE_BRICK_STAIRS, GBlocks.LUMIERE_BRICKS, GBlocks.LUMIERE_BRICK_SLAB, GBlocks.LUMIERE_BRICK_STAIRS, GBlocks.ALLURITE_BLOCK, GBlocks.LUMIERE_BLOCK);
        this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(GBlocks.SILVER_ORE, GBlocks.DEEPSLATE_SILVER_ORE, GBlocks.RAW_SILVER_BLOCK, GBlocks.SILVER_BLOCK, GBlocks.MONSTROMETER, GBlocks.WARPED_ANCHOR, GBlocks.COMBUSTION_TABLE, GBlocks.SILVER_PANEL, GBlocks.SILVER_PANEL_STAIRS, GBlocks.SILVER_PANEL_SLAB, GBlocks.SILVER_TILES, GBlocks.SILVER_TILES_STAIRS, GBlocks.SILVER_TILES_SLAB, GBlocks.SILVER_LATTICE);
        this.getOrCreateTagBuilder(BlockTags.REPLACEABLE).add(GBlocks.LICHEN_ROOTS);
        this.getOrCreateTagBuilder(BlockTags.DIRT).add(GBlocks.LICHEN_MOSS);
        this.getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(GBlocks.SILVER_BLOCK);
        this.getOrCreateTagBuilder(GBlockTags.GRAVEL_MAY_REPLACE).addOptionalTag(BlockTags.BASE_STONE_OVERWORLD).add(Blocks.CLAY, Blocks.SAND);
        this.getOrCreateTagBuilder(GBlockTags.CRYSTAL_SPIKES_BLOCKS).add(GBlocks.ALLURITE_BLOCK, GBlocks.LUMIERE_BLOCK);
        this.getOrCreateTagBuilder(GBlockTags.SPARKLES_SPAWNABLE_ON).addOptionalTag(BlockTags.BASE_STONE_OVERWORLD).addOptionalTag(GBlockTags.CRYSTAL_SPIKES_BLOCKS).add(Blocks.CALCITE);
        this.getOrCreateTagBuilder(GBlockTags.SPECTRES_SPAWNABLE_ON).addOptionalTag(BlockTags.BASE_STONE_OVERWORLD).add(GBlocks.LICHEN_MOSS);
        this.getOrCreateTagBuilder(GBlockTags.OBFUSCATES_SOUND_WAVES).add(GBlocks.ALLURITE_BLOCK, GBlocks.ALLURITE_CLUSTER, GBlocks.GLINTED_ALLURITE_CLUSTER, GBlocks.SMOOTH_ALLURITE, GBlocks.ALLURITE_BRICKS, GBlocks.ALLURITE_LAMP);
        this.getOrCreateTagBuilder(GBlockTags.PINK_SALT_BLOCKS).add(GBlocks.PINK_SALT, GBlocks.ROSE_PINK_SALT, GBlocks.PASTEL_PINK_SALT);
        this.getOrCreateTagBuilder(GBlockTags.PINK_SALT_HEATED_BLOCKS).add(Blocks.SOUL_FIRE, Blocks.SOUL_CAMPFIRE);
        this.getOrCreateTagBuilder(GBlockTags.OASIS_REPLACE).addOptionalTag(GBlockTags.PINK_SALT_BLOCKS).addOptionalTag(BlockTags.BASE_STONE_OVERWORLD).addOptionalTag(BlockTags.FLOWERS).addOptionalTag(BlockTags.DIRT).addOptionalTag(ConventionalBlockTags.ORES);
        this.getOrCreateTagBuilder(GBlockTags.OMIT_ASTRAL).add(Blocks.BEDROCK);
        this.getOrCreateTagBuilder(GBlockTags.OASIS_GENERATE_ON).addOptionalTag(GBlockTags.PINK_SALT_BLOCKS).addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);
    }
}
