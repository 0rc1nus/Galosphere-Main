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
    public static final TagKey<Item> RAW_MATERIALS_SILVER = bind("raw_materials/silver");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = bind("storage_blocks/raw_silver");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }

}
