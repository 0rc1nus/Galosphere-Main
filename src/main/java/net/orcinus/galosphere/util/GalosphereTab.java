package net.orcinus.galosphere.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.init.GItems;

public class GalosphereTab extends CreativeModeTab {

    public GalosphereTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return GItems.ICON_ITEM.get().getDefaultInstance();
    }

}
