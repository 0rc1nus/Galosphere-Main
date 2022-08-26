package net.orcinus.galosphere.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.orcinus.galosphere.Galosphere;

public class GHorseArmorItem extends HorseArmorItem {

    public GHorseArmorItem(int protection, String name) {
        super(protection, new ResourceLocation(Galosphere.MODID, "textures/entity/horse/armor/horse_armor_" + name + ".png"), new Item.Properties().stacksTo(1).tab(Galosphere.GALOSPHERE));
    }

}
