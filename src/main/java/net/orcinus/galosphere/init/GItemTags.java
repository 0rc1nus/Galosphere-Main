package net.orcinus.galosphere.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.orcinus.galosphere.Galosphere;

public class GItemTags {

    public static final TagKey<Item> SPARKLE_TEMPT_ITEMS = bind("sparkle_tempt_items");
    public static final TagKey<Item> SPECTRE_TEMPT_ITEMS = bind("spectre_tempt_items");
    public static final TagKey<Item> NON_SINKABLES_HORSE_ARMORS = bind("non_sinkable_horse_armors");
    public static final TagKey<Item> BOMB_DURATION_MODIFIERS = bind("bomb_duration_modifiers");
    public static final TagKey<Item> BOMB_EXPLOSION_MODIFIERS = bind("bomb_explosion_modifiers");
    public static final TagKey<Item> BOMB_BOUNCY_MODIFIERS = bind("bomb_bouncy_modifiers");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, Galosphere.id(name));
    }

}
