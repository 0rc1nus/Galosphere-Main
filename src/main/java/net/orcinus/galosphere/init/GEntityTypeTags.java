package net.orcinus.galosphere.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.orcinus.galosphere.Galosphere;

public class GEntityTypeTags {

    public static final TagKey<EntityType<?>> STERLING_IMMUNE_ENTITY_TYPES = create("sterling_immune_entity_types");
    public static final TagKey<EntityType<?>> BERSERKER_INVALID_TARGETS = create("berserker_invalid_targets");
    public static final TagKey<EntityType<?>> PRESERVED_INVALID_TARGETS = create("preserved_invalid_targets");

    private static TagKey<EntityType<?>> create(String string) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Galosphere.MODID, string));
    }

}
