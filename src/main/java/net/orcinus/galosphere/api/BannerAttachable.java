package net.orcinus.galosphere.api;

import net.minecraft.world.item.ItemStack;

public interface BannerAttachable {

    void setBanner(ItemStack stack);

    ItemStack getBanner();

}
