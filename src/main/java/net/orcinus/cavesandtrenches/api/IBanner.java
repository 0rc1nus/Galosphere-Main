package net.orcinus.cavesandtrenches.api;

import net.minecraft.world.item.ItemStack;

public interface IBanner {

    void setBanner(ItemStack stack);

    ItemStack getBanner();

}
