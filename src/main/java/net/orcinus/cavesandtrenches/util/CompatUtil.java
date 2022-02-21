package net.orcinus.cavesandtrenches.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatUtil {

    public CompatUtil() {
    }

    public boolean isModInstalled(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public boolean matchesCompatItem(Item item, String modid, String name) {
        return item == ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, name));
    }

}
