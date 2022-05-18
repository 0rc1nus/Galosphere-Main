package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.orcinus.galosphere.Galosphere;

public class GItemTags {

    public static final TagKey<Item> SPARKLE_TEMPT_ITEMS = bind("sparkle_tempt_items");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Galosphere.MODID, name));
    }

}
