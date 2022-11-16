package net.orcinus.galosphere.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;

public interface GoldenBreath {

    void setGoldenAirSupply(float goldenAirSupply);

    float getGoldenAirSupply();

    default float getMaxGoldenAirSupply() {
        return 300;
    }

    default int decreaseGoldenAirSupply(LivingEntity livingEntity, int i) {
        int j = EnchantmentHelper.getRespiration(livingEntity);
        int reductionValue = livingEntity.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) ? 1 : 4;
        if (j > 0 && livingEntity.getRandom().nextInt(j + 1) > 0) {
            return i;
        }
        return Math.max(i - reductionValue, 0);
    }

}
