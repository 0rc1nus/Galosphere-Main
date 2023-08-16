package net.orcinus.galosphere.compat.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CItemTags {

    public static final TagKey<Item> SILVER_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation("c", "silver_blocks"));
    public static final TagKey<Item> SILVER_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation("c", "silver_ingots"));
    public static final TagKey<Item> SILVER_NUGGETS = TagKey.create(Registries.ITEM, new ResourceLocation("c", "silver_nuggets"));
    public static final TagKey<Item> SILVER_ORES = TagKey.create(Registries.ITEM, new ResourceLocation("c", "silver_ores"));

}
