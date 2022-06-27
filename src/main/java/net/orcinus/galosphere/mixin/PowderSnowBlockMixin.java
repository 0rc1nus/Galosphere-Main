package net.orcinus.galosphere.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @Inject(at = @At("RETURN"), method = "canEntityWalkOnPowderSnow", cancellable = true)
    private static void G$canEntityWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(GItems.STERLING_BOOTS.get())) {
                cir.setReturnValue(true);
            }
            if (livingEntity instanceof Horse horse) {
                if (horse.getItemBySlot(EquipmentSlot.CHEST).is(GItemTags.NON_SINKABLES_HORSE_ARMORS)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
