package net.orcinus.galosphere.compat.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ForgeBlockTags {

    public static final TagKey<Block> SILVER_ORE = bind("ores/silver");
    public static final TagKey<Block> SILVER_STORAGE_BLOCKS = bind("storage_blocks/silver");
    public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = bind("storage_blocks/raw_silver");

    private static TagKey<Block> bind(String path) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("forge", path));
    }

}
