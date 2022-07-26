package net.orcinus.galosphere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.api.IBanner;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.items.SterlingArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBanner {
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.ITEM_STACK);

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    public void G$defineSynchedData(CallbackInfo ci) {
        SynchedEntityData data = ((LivingEntity) (Object) this).getEntityData();
        data.define(BANNER_STACK, ItemStack.EMPTY);
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    public void G$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("BannerStack", ((LivingEntity)(Object)this).getEntityData().get(BANNER_STACK).save(new CompoundTag()));
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    public void G$readAdditionalSavaData(CompoundTag tag, CallbackInfo ci) {
        this.setBanner(ItemStack.of(tag.getCompound("BannerStack")));
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void G$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof IBanner bannerEntity) {
            if (!bannerEntity.getBanner().isEmpty()) {
                if (entity instanceof Horse horse) {
                    if (!((IBanner)horse).getBanner().isEmpty() && !horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                        ItemStack copy = ((IBanner) horse).getBanner();
                        horse.spawnAtLocation(copy);
                        ((IBanner) horse).setBanner(ItemStack.EMPTY);
                    }
                } else {
                    if (!entity.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET)) {
                        ItemStack copy = bannerEntity.getBanner();
                        entity.spawnAtLocation(copy);
                        bannerEntity.setBanner(ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "die")
    private void G$die(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity $this = (LivingEntity)(Object)this;
        if ($this instanceof Horse horse) {
            if (!((IBanner)horse).getBanner().isEmpty() && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                ItemStack copy = ((IBanner) horse).getBanner();
                horse.spawnAtLocation(copy);
                ((IBanner) horse).setBanner(ItemStack.EMPTY);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getDamageAfterArmorAbsorb", cancellable = true)
    private void G$getDamageAfterArmorAbsorb(DamageSource damageSource, float f, CallbackInfoReturnable<Float> cir) {
        LivingEntity $this = (LivingEntity) (Object) this;
        if (!damageSource.isBypassArmor()) {
            ((LivingEntityAccessor) this).callHurtArmor(damageSource, f);
            boolean flag = damageSource.isExplosion();
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (flag) {
                    Item item = $this.getItemBySlot(slot).getItem();
                    float reductionAmount = 0.0F;
                    if ($this instanceof Horse horse) {
                        Item horseItem = horse.getArmor().getItem();
                        if (horseItem == GItems.STERLING_HORSE_ARMOR) {
                            float damageReduction = 3.0F;
                            reductionAmount = f - damageReduction;
                        }
                    }
                    if (item instanceof SterlingArmorItem sterlingArmorItem) {
                        float damageReduction = sterlingArmorItem.getExplosionResistance(slot);
                        reductionAmount = f - damageReduction;
                    }
                    if (item instanceof SterlingArmorItem || ($this instanceof Horse horse && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR))) {
                        cir.setReturnValue(reductionAmount / 3);
                    }
                }
            }
        }
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
