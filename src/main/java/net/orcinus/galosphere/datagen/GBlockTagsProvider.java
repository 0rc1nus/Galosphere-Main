package net.orcinus.galosphere.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.compat.init.ForgeBlockTags;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GBlockTagsProvider extends BlockTagsProvider {

    public GBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(GBlocks.SILVER_ORE.get(), GBlocks.SILVER_BLOCK.get(), GBlocks.DEEPSLATE_SILVER_ORE.get(), GBlocks.AMETHYST_SLAB.get(), GBlocks.AMETHYST_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get(), GBlocks.ALLURITE_SLAB.get(), GBlocks.ALLURITE_STAIRS.get(), GBlocks.LUMIERE_BLOCK.get(), GBlocks.LUMIERE_SLAB.get(), GBlocks.LUMIERE_STAIRS.get(), GBlocks.SMOOTH_AMETHYST.get(), GBlocks.SMOOTH_AMETHYST_SLAB.get(), GBlocks.SMOOTH_AMETHYST_STAIRS.get(), GBlocks.AMETHYST_BRICKS.get(), GBlocks.AMETHYST_BRICK_SLAB.get(), GBlocks.AMETHYST_BRICK_STAIRS.get(), GBlocks.CHISELED_AMETHYST.get(), GBlocks.SMOOTH_ALLURITE.get(), GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.ALLURITE_BRICKS.get(), GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.CHISELED_ALLURITE.get(), GBlocks.SMOOTH_LUMIERE.get(), GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.LUMIERE_BRICKS.get(), GBlocks.LUMIERE_BRICK_SLAB.get(), GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.CHISELED_LUMIERE.get(), GBlocks.MONSTROMETER.get(), GBlocks.WARPED_ANCHOR.get(), GBlocks.ALLURITE_CLUSTER.get(), GBlocks.LUMIERE_CLUSTER.get(), GBlocks.RAW_SILVER_BLOCK.get(), GBlocks.COMBUSTION_TABLE.get(), GBlocks.CHARGED_LUMIERE_BLOCK.get(), GBlocks.AMETHYST_LAMP.get(), GBlocks.ALLURITE_LAMP.get(), GBlocks.LUMIERE_LAMP.get(), GBlocks.CHANDELIER.get(), GBlocks.SILVER_TILES.get(), GBlocks.SILVER_TILES_SLAB.get(), GBlocks.SILVER_TILES_STAIRS.get(), GBlocks.SILVER_PANEL.get(), GBlocks.SILVER_PANEL_SLAB.get(), GBlocks.SILVER_PANEL_STAIRS.get(), GBlocks.SILVER_LATTICE.get(), GBlocks.GLOW_BERRIES_SILVER_LATTICE.get(), GBlocks.GLINTED_ALLURITE_CLUSTER.get(), GBlocks.GLINTED_LUMIERE_CLUSTER.get(), GBlocks.GLINTED_AMETHYST_CLUSTER.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(GBlocks.LUMIERE_COMPOSTER.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(GBlocks.LICHEN_MOSS.get());
        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(GBlocks.SILVER_BLOCK.get());
        this.tag(BlockTags.CRYSTAL_SOUND_BLOCKS).add(GBlocks.AMETHYST_SLAB.get(), GBlocks.AMETHYST_STAIRS.get(), GBlocks.SMOOTH_AMETHYST.get(), GBlocks.SMOOTH_AMETHYST_SLAB.get(), GBlocks.SMOOTH_AMETHYST_STAIRS.get(), GBlocks.SMOOTH_ALLURITE.get(), GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.SMOOTH_LUMIERE.get(), GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.AMETHYST_BRICKS.get(), GBlocks.AMETHYST_BRICK_SLAB.get(), GBlocks.AMETHYST_BRICK_STAIRS.get(), GBlocks.ALLURITE_BRICKS.get(), GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.LUMIERE_BRICKS.get(), GBlocks.LUMIERE_BRICK_SLAB.get(), GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get(), GBlocks.LUMIERE_BLOCK.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(GBlocks.SILVER_ORE.get(), GBlocks.DEEPSLATE_SILVER_ORE.get(), GBlocks.RAW_SILVER_BLOCK.get(), GBlocks.SILVER_BLOCK.get(), GBlocks.MONSTROMETER.get(), GBlocks.WARPED_ANCHOR.get(), GBlocks.COMBUSTION_TABLE.get(), GBlocks.SILVER_PANEL.get(), GBlocks.SILVER_PANEL_STAIRS.get(), GBlocks.SILVER_PANEL_SLAB.get(), GBlocks.SILVER_TILES.get(), GBlocks.SILVER_TILES_STAIRS.get(), GBlocks.SILVER_TILES_SLAB.get(), GBlocks.SILVER_LATTICE.get());
        GBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(StairBlock.class::isInstance).forEach(block -> this.tag(BlockTags.STAIRS).add(block));
        GBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(SlabBlock.class::isInstance).forEach(block -> this.tag(BlockTags.SLABS).add(block));
        this.tag(BlockTags.DIRT).add(GBlocks.LICHEN_MOSS.get());

        this.tag(GBlockTags.CRYSTAL_SPIKES_BLOCKS).add(GBlocks.ALLURITE_BLOCK.get(), GBlocks.LUMIERE_BLOCK.get());
        this.tag(GBlockTags.SPARKLES_SPAWNABLE_ON).add(Blocks.CALCITE).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(GBlockTags.CRYSTAL_SPIKES_BLOCKS);
        this.tag(GBlockTags.SPECTRES_SPAWNABLE_ON).add(GBlocks.LICHEN_MOSS.get());
        this.tag(GBlockTags.OBFUSCATES_SOUND_WAVES).add(GBlocks.ALLURITE_BLOCK.get()).add(GBlocks.GLINTED_ALLURITE_CLUSTER.get()).add(GBlocks.ALLURITE_CLUSTER.get()).add(GBlocks.SMOOTH_ALLURITE.get()).add(GBlocks.ALLURITE_BRICKS.get()).add(GBlocks.ALLURITE_LAMP.get());
        this.tag(GBlockTags.GRAVEL_MAY_REPLACE).add(Blocks.CLAY).add(Blocks.SAND).addTag(BlockTags.BASE_STONE_OVERWORLD);

        this.tag(ForgeBlockTags.SILVER_ORE).add(GBlocks.SILVER_ORE.get(), GBlocks.DEEPSLATE_SILVER_ORE.get());
        this.tag(ForgeBlockTags.SILVER_STORAGE_BLOCKS).add(GBlocks.SILVER_BLOCK.get());
        this.tag(ForgeBlockTags.STORAGE_BLOCKS_RAW_SILVER).add(GBlocks.RAW_SILVER_BLOCK.get());
        this.tag(Tags.Blocks.ORES).addTag(ForgeBlockTags.SILVER_ORE);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ForgeBlockTags.SILVER_STORAGE_BLOCKS).addTag(ForgeBlockTags.STORAGE_BLOCKS_RAW_SILVER);
    }
}
