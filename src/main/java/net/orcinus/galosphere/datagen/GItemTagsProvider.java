package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.compat.init.ForgeBlockTags;
import net.orcinus.galosphere.compat.init.ForgeItemTags;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;
import org.jetbrains.annotations.Nullable;

public class GItemTagsProvider extends ItemTagsProvider {

    public GItemTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, new GBlockTagsProvider(dataGenerator, existingFileHelper), Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        GItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(BlockItem.class::isInstance).map(BlockItem.class::cast).map(BlockItem::getBlock).filter(StairBlock.class::isInstance).forEach(block -> this.tag(ItemTags.STAIRS).add(block.asItem()));
        GItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(BlockItem.class::isInstance).map(BlockItem.class::cast).map(BlockItem::getBlock).filter(SlabBlock.class::isInstance).forEach(block -> this.tag(ItemTags.SLABS).add(block.asItem()));
        this.tag(GItemTags.SPARKLE_TEMPT_ITEMS).add(Items.GLOW_LICHEN);
        this.tag(GItemTags.NON_SINKABLES_HORSE_ARMORS).add(GItems.STERLING_HORSE_ARMOR.get(), Items.LEATHER_HORSE_ARMOR);
        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(GItems.STERLING_HELMET.get(), GItems.STERLING_CHESTPLATE.get(), GItems.STERLING_LEGGINGS.get(), GItems.STERLING_BOOTS.get(), GItems.STERLING_HORSE_ARMOR.get());
        this.tag(ForgeItemTags.SILVER_INGOT).add(GItems.SILVER_INGOT.get());
        this.tag(ForgeItemTags.SILVER_NUGGETS).add(GItems.SILVER_NUGGET.get());
        this.tag(ForgeItemTags.SILVER_ORES).add(GBlocks.SILVER_ORE.get().asItem()).add(GBlocks.DEEPSLATE_SILVER_ORE.get().asItem());
        this.tag(Tags.Items.INGOTS).addTag(ForgeItemTags.SILVER_INGOT);
        this.tag(Tags.Items.NUGGETS).addTag(ForgeItemTags.SILVER_NUGGETS);
        this.tag(Tags.Items.ORES).addTag(ForgeItemTags.SILVER_ORES);
        this.tag(ForgeItemTags.SILVER_STORAGE_BLOCKS).add(GBlocks.SILVER_BLOCK.get().asItem());
        this.tag(Tags.Items.STORAGE_BLOCKS).addTag(ForgeItemTags.SILVER_STORAGE_BLOCKS).addTag(ForgeItemTags.STORAGE_BLOCKS_RAW_SILVER);
        this.tag(ForgeItemTags.RAW_MATERIALS_SILVER).add(GItems.RAW_SILVER.get());
        this.tag(ForgeItemTags.STORAGE_BLOCKS_RAW_SILVER).add(GBlocks.RAW_SILVER_BLOCK.get().asItem());
        this.tag(Tags.Items.RAW_MATERIALS).addTag(ForgeItemTags.RAW_MATERIALS_SILVER);
    }
}
