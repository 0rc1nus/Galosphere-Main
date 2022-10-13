package net.orcinus.galosphere.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.entities.FayEntity;

public interface BottlePickable {

    boolean fromBottle();

    void setFromBottle(boolean fromBottle);

    static void saveDefaultDataFromBottleTag(FayEntity fay, ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        if (fay.hasCustomName()) {
            stack.setHoverName(fay.getCustomName());
        }
        if (fay.isNoAi()) {
            compoundTag.putBoolean("NoAI", fay.isNoAi());
        }
        if (fay.isSilent()) {
            compoundTag.putBoolean("Silent", fay.isSilent());
        }
        if (fay.isNoGravity()) {
            compoundTag.putBoolean("NoGravity", fay.isNoGravity());
        }
        if (fay.hasGlowingTag()) {
            compoundTag.putBoolean("Glowing", fay.hasGlowingTag());
        }
        if (fay.isInvulnerable()) {
            compoundTag.putBoolean("Invulnerable", fay.isInvulnerable());
        }
        if (fay.canBeManipulated()) {
            compoundTag.putBoolean("CanBeManipulated", fay.canBeManipulated());
        }
        compoundTag.putFloat("Health", fay.getHealth());
    }

    static void loadDefaultDataFromBottleTag(FayEntity fay, CompoundTag compoundTag) {
        if (compoundTag.contains("CanBeManipulated")) {
            fay.setCanBeManipulated(compoundTag.getBoolean("CanBeManipulated"));
        }
        if (compoundTag.contains("NoAI")) {
            fay.setNoAi(compoundTag.getBoolean("NoAI"));
        }
        if (compoundTag.contains("Silent")) {
            fay.setSilent(compoundTag.getBoolean("Silent"));
        }
        if (compoundTag.contains("NoGravity")) {
            fay.setNoGravity(compoundTag.getBoolean("NoGravity"));
        }
        if (compoundTag.contains("Glowing")) {
            fay.setGlowingTag(compoundTag.getBoolean("Glowing"));
        }
        if (compoundTag.contains("Invulnerable")) {
            fay.setInvulnerable(compoundTag.getBoolean("Invulnerable"));
        }
        if (compoundTag.contains("Health", 99)) {
            fay.setHealth(compoundTag.getFloat("Health"));
        }
    }

}
