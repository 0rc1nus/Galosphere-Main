package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.orcinus.galosphere.Galosphere;

public class GEntityTypeTags {

    public static final TagKey<EntityType<?>> STERLING_IMMUNE_ENTITY_TYPES = create("sterling_immune_entity_types");

    private static TagKey<EntityType<?>> create(String string) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Galosphere.MODID, string));
    }

}
