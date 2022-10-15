package net.orcinus.galosphere.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GItems;

public interface SpectreBoundedSpyglass {

    static boolean canUseSpectreBoundedSpyglass(ItemStack stack, LivingEntity livingEntity) {
        return stack.is(GItems.SPECTRE_BOUND_SPYGLASS.get()) && SpectreBoundedSpyglass.isSpectreBoundedSpyglass(stack) && !(livingEntity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6);
    }

    static boolean isSpectreBoundedSpyglass(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && (compoundtag.contains("SpectreBoundId") && compoundtag.contains("SpectreBoundUUID"));
    }

    static void addSpectreBoundedTags(SpectreEntity fay, CompoundTag compoundTag) {
        compoundTag.putInt("SpectreBoundId", fay.getId());
        compoundTag.putUUID("SpectreBoundUUID", fay.getUUID());
    }

    boolean isUsingSpectreBoundedSpyglass();

    void setUsingSpectreBoundedSpyglass(boolean usingSpectreBoundedSpyglass);

}
