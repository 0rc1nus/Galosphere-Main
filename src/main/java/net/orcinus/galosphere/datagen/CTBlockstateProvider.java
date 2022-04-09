package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GBlocks;

public class CTBlockstateProvider extends BlockStateProvider {

    public CTBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Galosphere.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.stairsBlock((StairBlock) GBlocks.ALLURITE_STAIRS.get(), new ResourceLocation(Galosphere.MODID, "block/allurite_block"));
        this.stairsBlock((StairBlock) GBlocks.LUMIERE_STAIRS.get(), new ResourceLocation(Galosphere.MODID, "block/lumiere_block"));
        this.slabBlock((SlabBlock) GBlocks.ALLURITE_SLAB.get(), new ResourceLocation(Galosphere.MODID, "block/allurite_block"), new ResourceLocation(Galosphere.MODID, "block/allurite_block"));
        this.slabBlock((SlabBlock) GBlocks.LUMIERE_SLAB.get(), new ResourceLocation(Galosphere.MODID, "block/lumiere_block"), new ResourceLocation(Galosphere.MODID, "block/lumiere_block"));
//        this.stairsBlock((StairBlock) CTBlocks.AMETHYST_STAIRS.get(), mcLoc("block/amethyst_block"));
//        this.slabBlock((SlabBlock) CTBlocks.AMETHYST_SLAB.get(), mcLoc("block/amethyst_block"), mcLoc("block/amethyst_block"));
//        this.simpleBlock(CTBlocks.DEEPSLATE_SILVER_ORE.get());
//        this.simpleBlock(CTBlocks.GLOW_LICHEN_BLOCK.get());
//        this.simpleBlock(CTBlocks.LUMIERE_BLOCK.get());
//        this.simpleBlock(CTBlocks.ALLURITE_BLOCK.get());
//        this.simpleBlock(CTBlocks.SILVER_ORE.get());
//        this.simpleBlock(CTBlocks.RAW_SILVER_BLOCK.get());
//        this.simpleBlock(CTBlocks.POLISHED_AMETHYST.get());
//        this.stairsBlock((StairBlock) CTBlocks.POLISHED_AMETHYST_STAIRS.get(), new ResourceLocation(CavesAndTrenches.MODID, "block/polished_amethyst"));
//        this.slabBlock((SlabBlock) CTBlocks.POLISHED_AMETHYST_SLAB.get(), new ResourceLocation(CavesAndTrenches.MODID, "block/polished_amethyst"), new ResourceLocation(CavesAndTrenches.MODID, "block/polished_amethyst"));
//        this.simpleBlock(CTBlocks.AMETHYST_BRICKS.get());
//        this.stairsBlock((StairBlock) CTBlocks.AMETHYST_BRICKS_STAIRS.get(), new ResourceLocation(CavesAndTrenches.MODID, "block/amethyst_bricks"));
//        this.slabBlock((SlabBlock) CTBlocks.AMETHYST_BRICKS_SLAB.get(), new ResourceLocation(CavesAndTrenches.MODID, "block/amethyst_bricks"), new ResourceLocation(CavesAndTrenches.MODID, "block/polished_amethyst"));
//        this.simpleBlock(CTBlocks.LUMIERE_LAMP.get());
//        this.simpleBlock(CTBlocks.CHISELED_AMETHYST.get());
    }
}
