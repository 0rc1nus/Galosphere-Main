package net.orcinus.galosphere.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GItems;

public interface SpectreBoundSpyglass {

    static boolean canUseSpectreBoundSpyglass(ItemStack stack) {
        return stack.is(GItems.SPECTRE_BOUND_SPYGLASS) && SpectreBoundSpyglass.isSpectreBoundSpyglass(stack);
    }

    static boolean isSpectreBoundSpyglass(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && (compoundtag.contains("SpectreBoundId") && compoundtag.contains("SpectreBoundUUID"));
    }

    static void addSpectreBoundedTags(SpectreEntity spectre, CompoundTag compoundTag) {
        compoundTag.putInt("SpectreBoundId", spectre.getId());
        compoundTag.putUUID("SpectreBoundUUID", spectre.getUUID());
    }

    boolean isUsingSpectreBoundedSpyglass();

    void setUsingSpectreBoundedSpyglass(boolean usingSpectreBoundedSpyglass);

}