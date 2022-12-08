package net.orcinus.galosphere.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.orcinus.galosphere.Galosphere;

public class GItemTags {

    public static final TagKey<Item> SPARKLE_TEMPT_ITEMS = bind("sparkle_tempt_items");
    public static final TagKey<Item> NON_SINKABLES_HORSE_ARMORS = bind("non_sinkable_horse_armors");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, Galosphere.id(name));
    }

}
