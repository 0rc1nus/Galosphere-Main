package net.orcinus.galosphere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.api.IBanner;
import net.orcinus.galosphere.api.ISoulWince;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBanner, ISoulWince {
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> WINCED = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    public void defineSynchedData(CallbackInfo ci) {
        SynchedEntityData data = ((LivingEntity) (Object) this).getEntityData();
        data.define(BANNER_STACK, ItemStack.EMPTY);
        data.define(WINCED, false);
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("BannerStack", ((LivingEntity)(Object)this).getEntityData().get(BANNER_STACK).save(new CompoundTag()));
        tag.putBoolean("Winced", this.isWinced());
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    public void readAdditionalSavaData(CompoundTag tag, CallbackInfo ci) {
        this.setBanner(ItemStack.of(tag.getCompound("BannerStack")));
        this.setWinced(tag.getBoolean("Winced"));
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
    public boolean isWinced() {
        return ((LivingEntity)(Object)this).getEntityData().get(WINCED);
    }

    @Override
    public void setWinced(boolean winced) {
        ((LivingEntity)(Object)this).getEntityData().set(WINCED, winced);
    }
}
