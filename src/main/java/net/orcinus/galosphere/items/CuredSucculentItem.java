package net.orcinus.galosphere.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.init.GMobEffects;

import java.util.function.Predicate;

public class CuredSucculentItem extends Item {

    public CuredSucculentItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Predicate<MobEffectInstance> mobEffectInstancePredicate = mobEffectInstance -> mobEffectInstance.getEffect() != GMobEffects.STIMULATION && mobEffectInstance.getAmplifier() < 1;
        if (!livingEntity.hasEffect(GMobEffects.STIMULATION)) {
            livingEntity.getActiveEffects().stream().filter(mobEffectInstancePredicate).forEach(mobEffectInstance -> {
                livingEntity.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getDuration(), mobEffectInstance.getAmplifier() + 1, mobEffectInstance.isAmbient(), mobEffectInstance.isVisible(), mobEffectInstance.showIcon(), null, mobEffectInstance.getFactorData()));
            });
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
