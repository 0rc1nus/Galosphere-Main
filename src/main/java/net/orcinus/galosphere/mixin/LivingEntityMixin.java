package net.orcinus.galosphere.mixin;

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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GEntityTypeTags;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.items.SterlingArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements BannerAttachable, GoldenBreath, SpectreBoundSpyglass {
    @Shadow protected ItemStack useItem;
    @Unique
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.ITEM_STACK );
    @Unique
    private static final EntityDataAccessor<Float> GOLDEN_AIR_SUPPLY = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);
    @Unique
    private static final EntityDataAccessor<Boolean> USING_SPECTRE_BOUNDED_SPYGLASS = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private boolean preserved;

    @Inject(at = @At("TAIL"), method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true)
    private void G$canAttack(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity.hasEffect(GMobEffects.HARMONY) && ((LivingEntity)(Object)this).getLastDamageSource() == null) {
            cir.setReturnValue(false);
        }
    }

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
        tag.putBoolean("Preserved", this.preserved);
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    public void G$readAdditionalSavaData(CompoundTag tag, CallbackInfo ci) {
        this.setBanner(ItemStack.of(tag.getCompound("BannerStack")));
        this.setGoldenAirSupply(tag.getFloat("GoldenAirSupply"));
        this.setUsingSpectreBoundedSpyglass(tag.getBoolean("UsingSpectreBoundedSpyglass"));
        if (this.preserved) {
            this.preserved = tag.getBoolean("Preserved");
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void G$tick(CallbackInfo ci) {
        LivingEntity $this = (LivingEntity) (Object) this;
        if (SpectreBoundSpyglass.canUseSpectreBoundSpyglass(this.useItem) && this.useItem.getTag() != null) {
            if (!$this.level().isClientSide) {
                Entity spectreBound = ((ServerLevel)$this.level()).getEntity(this.useItem.getTag().getUUID("SpectreBoundUUID"));
                Optional.ofNullable(spectreBound).filter(Spectre.class::isInstance).map(Spectre.class::cast).filter(Spectre::isAlive).ifPresent(spectre -> {
                    if ($this instanceof Player player && spectre.getManipulatorUUID() != player.getUUID()) {
                        boolean withinDistance = Math.sqrt(Math.pow((player.getX() - spectre.getX()), 2) + Math.pow((player.getZ() - spectre.getZ()), 2)) < 110;
                        if (withinDistance) {
                            spectre.setCamera(player);
                        }
                    }
                });
            }
        }
        if ($this instanceof BannerAttachable bannerEntity) {
            if (!bannerEntity.getBanner().isEmpty()) {
                if ($this instanceof Horse horse) {
                    if (!((BannerAttachable)horse).getBanner().isEmpty() && !horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                        ItemStack copy = ((BannerAttachable) horse).getBanner();
                        horse.spawnAtLocation(copy);
                        ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
                    }
                } else {
                    if (!$this.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET)) {
                        ItemStack copy = bannerEntity.getBanner();
                        $this.spawnAtLocation(copy);
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
        LivingEntity $this = (LivingEntity) (Object) this;
        if ($this instanceof Horse horse && horse instanceof BannerAttachable bannerAttachable) {
            if (!bannerAttachable.getBanner().isEmpty() && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                ItemStack copy = bannerAttachable.getBanner();
                horse.spawnAtLocation(copy);
                bannerAttachable.setBanner(ItemStack.EMPTY);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "isInWall", cancellable = true)
    private void G$isInWall(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity $this = (LivingEntity) (Object) this;
        if ($this != null && $this.hasEffect(GMobEffects.ASTRAL)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterAbsorb(FFF)F", shift = At.Shift.AFTER), method = "getDamageAfterArmorAbsorb", cancellable = true)
    private void G$getDamageAfterArmorAbsorb(DamageSource damageSource, float f, CallbackInfoReturnable<Float> cir) {
        LivingEntity $this = (LivingEntity) (Object) this;
        boolean flag = damageSource.getEntity() instanceof Mob mob && (mob.getMobType() == MobType.ILLAGER || mob.getType().is(GEntityTypeTags.STERLING_IMMUNE_ENTITY_TYPES));
        if (flag) {
            if ($this instanceof Horse horse && horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                cir.setReturnValue(f - 4.0F);
            }
            float illagerReduction = 0.0F;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if ($this.getItemBySlot(equipmentSlot).getItem() instanceof SterlingArmorItem sterlingArmorItem && equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                    illagerReduction+=sterlingArmorItem.getInsurgentResistance(equipmentSlot);
                }
            }
            if (illagerReduction > 0) {
                float value = 4.0F * (f / illagerReduction);
                cir.setReturnValue(value);
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
