package net.orcinus.galosphere.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.orcinus.galosphere.init.GEnchantments;

public class SaltboundEnchantment extends Enchantment {

    public SaltboundEnchantment() {
        super(Rarity.VERY_RARE, GEnchantments.SALTBOUND_TABLET, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int i) {
        return i * 25;
    }

    @Override
    public int getMaxCost(int i) {
        return this.getMinCost(i) + 50;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}