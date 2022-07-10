package net.orcinus.galosphere.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.orcinus.galosphere.Galosphere;

public class CTHorseArmorItem extends HorseArmorItem {

    public CTHorseArmorItem(int protection, String name) {
        super(protection, new ResourceLocation(Galosphere.MODID, "textures/entity/horse/armor/horse_armor_" + name + ".png").toString(), new Item.Properties().stacksTo(1).tab(Galosphere.GALOSPHERE));
    }

}
