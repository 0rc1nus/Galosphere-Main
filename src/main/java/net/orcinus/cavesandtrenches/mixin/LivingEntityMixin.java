package net.orcinus.cavesandtrenches.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.cavesandtrenches.api.IBanner;
import net.orcinus.cavesandtrenches.items.SterlingArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBanner {
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.ITEM_STACK);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/world/damagesource/DamageSource;)I"), method = "getDamageAfterMagicAbsorb", cancellable = true)
    public void getDamageAfterMagicAbsorb(DamageSource source, float value, CallbackInfoReturnable<Float> cir) {
        LivingEntity $this = (LivingEntity)(Object)this;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if ($this.getItemBySlot(equipmentSlot).getItem() instanceof SterlingArmorItem sterlingArmorItem) {
                float explosionValue = sterlingArmorItem.getExplosionResistance(equipmentSlot);
                if (explosionValue > 0) {
                    cir.setReturnValue(explosionValue);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    public void defineSynchedData(CallbackInfo ci) {
        ((LivingEntity)(Object)this).getEntityData().define(BANNER_STACK, ItemStack.EMPTY);
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("BannerStack", ((LivingEntity)(Object)this).getEntityData().get(BANNER_STACK).save(new CompoundTag()));
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    public void readAdditionalSavaData(CompoundTag tag, CallbackInfo ci) {
        this.setBanner(ItemStack.of(tag.getCompound("BannerStack")));
    }

    @Override
    public void setBanner(ItemStack stack) {
        ((LivingEntity)(Object)this).getEntityData().set(BANNER_STACK, stack);
    }

    @Override
    public ItemStack getBanner() {
        return ((LivingEntity)(Object)this).getEntityData().get(BANNER_STACK);
    }
}
