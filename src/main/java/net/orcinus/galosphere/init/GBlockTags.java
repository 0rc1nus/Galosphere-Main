package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.orcinus.galosphere.Galosphere;

public class GBlockTags {

    public static final TagKey<Block> CRYSTAL_SPIKES_BLOCKS = create("crystal_spikes_blocks");
    public static final TagKey<Block> SPARKLES_SPAWNABLE_ON = create("sparkles_spawn_on");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Galosphere.MODID, id));
    }

}
