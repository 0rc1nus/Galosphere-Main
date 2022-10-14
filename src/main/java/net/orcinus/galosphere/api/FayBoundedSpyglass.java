package net.orcinus.galosphere.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GItems;

public interface FayBoundedSpyglass {

    static boolean canUseFayBoundedSpyglass(ItemStack stack, LivingEntity livingEntity) {
        return stack.is(GItems.SPECTRE_BOUNDED_SPYGLASS) && FayBoundedSpyglass.isFayBoundedSpyglass(stack) && !(livingEntity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6);
    }

    static boolean isFayBoundedSpyglass(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && (compoundtag.contains("FayBoundId") && compoundtag.contains("FayBoundUUID"));
    }

    static void addFayBoundedTags(SpectreEntity fay, CompoundTag compoundTag) {
        compoundTag.putInt("FayBoundId", fay.getId());
        compoundTag.putUUID("FayBoundUUID", fay.getUUID());
    }

    boolean isUsingFayBoundedSpyglass();

    void setUsingFayBoundedSpyglass(boolean usingFayBoundedSpyglass);

}
