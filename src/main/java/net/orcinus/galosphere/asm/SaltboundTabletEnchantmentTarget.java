package net.orcinus.galosphere.asm;

import net.minecraft.world.item.Item;
import net.orcinus.galosphere.items.SaltboundTabletItem;
import net.orcinus.galosphere.mixin.EnchantmentCategoryMixin;

public class SaltboundTabletEnchantmentTarget extends EnchantmentCategoryMixin {
    @Override
    public boolean canEnchant(Item item) {
        return item instanceof SaltboundTabletItem;
    }
}
