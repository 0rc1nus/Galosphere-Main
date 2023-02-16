package net.orcinus.galosphere.compat.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeItemTags {

    public static final TagKey<Item> SILVER_INGOT = bind("ingots/silver");
    public static final TagKey<Item> SILVER_NUGGETS = bind("nuggets/silver");
    public static final TagKey<Item> SILVER_ORES = bind("ores/silver");
    public static final TagKey<Item> SILVER_STORAGE_BLOCKS = bind("storage_blocks/silver");
    public static final TagKey<Item> SILVER_RAW_ORES = bind("raw_ores/silver");

    public static final TagKey<Item> INGOTS = bind("ingots");
    public static final TagKey<Item> NUGGETS = bind("nuggets");
    public static final TagKey<Item> ORES = bind("ores");
    public static final TagKey<Item> STORAGE_BLOCKS = bind("storage_blocks");
    public static final TagKey<Item> RAW_ORES = bind("raw_ores");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = bind("storage_blocks/raw_silver");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }

}
