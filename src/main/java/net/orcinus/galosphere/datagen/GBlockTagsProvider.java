package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBlocks;
import org.jetbrains.annotations.Nullable;

public class GBlockTagsProvider extends BlockTagsProvider {

    public GBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(GBlocks.SILVER_ORE.get(), GBlocks.SILVER_BLOCK.get(), GBlocks.DEEPSLATE_SILVER_ORE.get(), GBlocks.AMETHYST_SLAB.get(), GBlocks.AMETHYST_STAIRS.get(), GBlocks.ALLURITE_BLOCK.get(), GBlocks.ALLURITE_SLAB.get(), GBlocks.ALLURITE_STAIRS.get(), GBlocks.LUMIERE_BLOCK.get(), GBlocks.LUMIERE_SLAB.get(), GBlocks.LUMIERE_STAIRS.get(), GBlocks.SMOOTH_AMETHYST.get(), GBlocks.SMOOTH_AMETHYST_SLAB.get(), GBlocks.SMOOTH_AMETHYST_STAIRS.get(), GBlocks.AMETHYST_BRICKS.get(), GBlocks.CHISELED_AMETHYST.get(), GBlocks.SMOOTH_ALLURITE.get(), GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.ALLURITE_BRICKS.get(), GBlocks.CHISELED_ALLURITE.get(), GBlocks.SMOOTH_LUMIERE.get(), GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.LUMIERE_BRICKS.get(), GBlocks.CHISELED_LUMIERE.get(), GBlocks.AURA_RINGER.get(), GBlocks.WARPED_ANCHOR.get(), GBlocks.ALLURITE_CLUSTER.get(), GBlocks.LUMIERE_CLUSTER.get(), GBlocks.RAW_SILVER_BLOCK.get(), GBlocks.COMBUSTION_TABLE.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(GBlocks.LUMIERE_COMPOSTER.get());
    }
}
