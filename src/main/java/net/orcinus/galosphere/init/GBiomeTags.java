package net.orcinus.galosphere.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.orcinus.galosphere.Galosphere;

public class GBiomeTags {

    public static final TagKey<Biome> HAS_PINK_SALT_SHRINE = TagKey.create(Registries.BIOME, Galosphere.id("has_structure/pink_salt_shrine"));

}
