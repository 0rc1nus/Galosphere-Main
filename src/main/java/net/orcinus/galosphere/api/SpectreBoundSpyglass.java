package net.orcinus.galosphere.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GItems;

public interface SpectreBoundSpyglass {

    static boolean canUseSpectreBoundedSpyglass(ItemStack stack) {
        return stack.is(GItems.SPECTRE_BOUND_SPYGLASS.get()) && SpectreBoundSpyglass.isSpectreBoundedSpyglass(stack);
    }

    static boolean isSpectreBoundedSpyglass(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && (compoundtag.contains("SpectreBoundId") && compoundtag.contains("SpectreBoundUUID"));
    }

    static void addSpectreBoundedTags(Spectre fay, CompoundTag compoundTag) {
        compoundTag.putInt("SpectreBoundId", fay.getId());
        compoundTag.putUUID("SpectreBoundUUID", fay.getUUID());
    }

    boolean isUsingSpectreBoundedSpyglass();

    void setUsingSpectreBoundedSpyglass(boolean usingSpectreBoundedSpyglass);

}
