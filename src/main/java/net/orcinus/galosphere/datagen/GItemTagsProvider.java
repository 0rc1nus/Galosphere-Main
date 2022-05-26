package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;
import org.jetbrains.annotations.Nullable;

public class GItemTagsProvider extends ItemTagsProvider {

    public GItemTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, new GBlockTagsProvider(dataGenerator, existingFileHelper), Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(GItemTags.SPARKLE_TEMPT_ITEMS).add(Items.GLOW_LICHEN);
        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(GItems.STERLING_HELMET.get(), GItems.STERLING_CHESTPLATE.get(), GItems.STERLING_LEGGINGS.get(), GItems.STERLING_BOOTS.get(), GItems.STERLING_HORSE_ARMOR.get());
    }
}
