package net.orcinus.galosphere.mixin;

import java.util.Optional;

import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.items.SterlingArmorItem;
import net.orcinus.galosphere.mixin.access.LivingEntityAccessor;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements BannerAttachable, GoldenBreath, SpectreBoundSpyglass {
    @Shadow protected ItemStack useItem;
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.ITEM_STACK );
    private static final EntityDataAccessor<Float> GOLDEN_AIR_SUPPLY = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> USING_SPECTRE_BOUNDED_SPYGLASS = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    public void G$defineSynchedData(CallbackInfo ci) {
        SynchedEntityData data = ((LivingEntity) (Object) this).getEntityData();
        data.define(BANNER_STACK, ItemStack.EMPTY);
        data.define(GOLDEN_AIR_SUPPLY, 0.0F);
        data.define(USING_SPECTRE_BOUNDED_SPYGLASS, false);
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    public void G$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("BannerStack", ((LivingEntity)(Object)this).getEntityData().get(BANNER_STACK).save(new CompoundTag()));
        tag.putFloat("GoldenAirSupply", this.getGoldenAirSupply());
        tag.putBoolean("UsingSpectreBoundedSpyglass", this.isUsingSpectreBoundedSpyglass());
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    public void G$readAdditionalSavaData(CompoundTag tag, CallbackInfo ci) {
        this.setBanner(ItemStack.of(tag.getCompound("BannerStack")));
        this.setGoldenAirSupply(tag.getFloat("GoldenAirSupply"));
        this.setUsingSpectreBoundedSpyglass(tag.getBoolean("UsingSpectreBoundedSpyglass"));
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void G$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (SpectreBoundSpyglass.canUseSpectreBoundSpyglass(this.useItem, entity) && this.useItem.getTag() != null) {
            if (!entity.level.isClientSide) {
                Entity spectreBound = ((ServerLevel)entity.level).getEntity(this.useItem.getTag().getUUID("SpectreBoundUUID"));
                Optional.ofNullable(spectreBound).filter(SpectreEntity.class::isInstance).map(SpectreEntity.class::cast).filter(SpectreEntity::isAlive).ifPresent(spectre -> {
                    if (entity instanceof Player player && spectre.getManipulatorUUID() != player.getUUID()) {
                        boolean withinDistance = Math.sqrt(Math.pow((player.getX() - spectre.getX()), 2) + Math.pow((player.getZ() - spectre.getZ()), 2)) < 110;
                        if (withinDistance) {
                            spectre.setCamera(player);
                        }
                    }
                });
            }
        }
        if (entity instanceof BannerAttachable bannerEntity) {
            if (!bannerEntity.getBanner().isEmpty()) {
                if (entity instanceof Horse horse) {
                    if (!((BannerAttachable)horse).getBanner().isEmpty() && !horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                        ItemStack copy = ((BannerAttachable) horse).getBanner();
                        horse.spawnAtLocation(copy);
                        ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
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

    @Inject(at = @At("HEAD"), method = "decreaseAirSupply", cancellable = true)
    private void G$decreaseAirSupply(int i, CallbackInfoReturnable<Integer> cir) {
        if (this.getGoldenAirSupply() > 0) {
            cir.setReturnValue(i);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAlive()Z", shift = At.Shift.AFTER, ordinal = 0), method = "baseTick")
    private void G$baseTick(CallbackInfo ci) {
        LivingEntity $this = ((LivingEntity)(Object)this);
        if (this.getGoldenAirSupply() > 0) {
            this.setGoldenAirSupply(this.decreaseGoldenAirSupply($this, (int) this.getGoldenAirSupply()));
        }
    }

    protected int decreaseGoldenAirSupply(LivingEntity livingEntity, int i) {
        int j = EnchantmentHelper.getRespiration(livingEntity);
        int reductionValue = livingEntity.isEyeInFluid(FluidTags.WATER) ? 1 : 4;
        if (j > 0 && livingEntity.getRandom().nextInt(j + 1) > 0) {
            return i;
        }
        return Math.max(i - reductionValue, 0);
    }

    @Inject(at = @At("HEAD"), method = "die")
    private void G$die(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity $this = (LivingEntity)(Object)this;
        if ($this instanceof Horse horse) {
            if (!((BannerAttachable)horse).getBanner().isEmpty() && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                ItemStack copy = ((BannerAttachable) horse).getBanner();
                horse.spawnAtLocation(copy);
                ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
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
                            float damageReduction = 4.0F;
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

    @Override
    public void setGoldenAirSupply(float goldenAirSupply) {
        ((LivingEntity)(Object)this).getEntityData().set(GOLDEN_AIR_SUPPLY, goldenAirSupply);
    }

    @Override
    public float getGoldenAirSupply() {
        return ((LivingEntity)(Object)this).getEntityData().get(GOLDEN_AIR_SUPPLY);
    }

    @Override
    public boolean isUsingSpectreBoundedSpyglass() {
        return ((LivingEntity)(Object)this).getEntityData().get(USING_SPECTRE_BOUNDED_SPYGLASS);
    }

    @Override
    public void setUsingSpectreBoundedSpyglass(boolean usingSpectreBoundedSpyglass) {
        ((LivingEntity)(Object)this).getEntityData().set(USING_SPECTRE_BOUNDED_SPYGLASS, usingSpectreBoundedSpyglass);
    }
}
