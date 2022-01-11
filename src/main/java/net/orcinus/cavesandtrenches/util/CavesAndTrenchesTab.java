package net.orcinus.cavesandtrenches.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.orcinus.cavesandtrenches.init.CTItems;

public class CavesAndTrenchesTab extends CreativeModeTab {

    public CavesAndTrenchesTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return CTItems.SILVER_BOMB.get().getDefaultInstance();
    }
}
