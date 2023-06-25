package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.orcinus.galosphere.Galosphere;

public class GBlockTags {

    public static final TagKey<Block> GRAVEL_MAY_REPLACE = create("gravel_may_replace");
    public static final TagKey<Block> CRYSTAL_SPIKES_BLOCKS = create("crystal_spikes_blocks");
    public static final TagKey<Block> SPARKLES_SPAWNABLE_ON = create("sparkles_spawn_on");
    public static final TagKey<Block> SPECTRES_SPAWNABLE_ON = create("spectres_spawn_on");
    public static final TagKey<Block> OBFUSCATES_SOUND_WAVES = create("obfuscates_sound_waves");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(Galosphere.MODID, id));
    }

}
