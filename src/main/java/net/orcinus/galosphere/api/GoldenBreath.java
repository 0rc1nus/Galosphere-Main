package net.orcinus.galosphere.api;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public interface GoldenBreath {

    void setGoldenAirSupply(float goldenAirSupply);

    float getGoldenAirSupply();

    default float getMaxGoldenAirSupply() {
        return 300;
    }

    default int decreaseGoldenAirSupply(LivingEntity livingEntity, int i) {
        int j = EnchantmentHelper.getRespiration(livingEntity);
        int reductionValue = livingEntity.isEyeInFluid(FluidTags.WATER) ? 1 : 4;
        if (j > 0 && livingEntity.getRandom().nextInt(j + 1) > 0) {
            return i;
        }
        return Math.max(i - reductionValue, 0);
    }

}
