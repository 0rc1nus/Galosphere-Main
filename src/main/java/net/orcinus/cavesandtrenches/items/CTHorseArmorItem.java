package net.orcinus.cavesandtrenches.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

public class CTHorseArmorItem extends HorseArmorItem {

    public CTHorseArmorItem(int protection, String name) {
        super(protection, new ResourceLocation(CavesAndTrenches.MODID, "textures/entity/horse/armor/horse_armor_" + name + ".png"), new Item.Properties().stacksTo(1).tab(CavesAndTrenches.CAVESANDTRENCHES));
    }

}
