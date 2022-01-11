package net.orcinus.cavesandtrenches.mixin;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.cavesandtrenches.api.IBanner;
import net.orcinus.cavesandtrenches.blocks.LumenBlossomBlock;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBanner {
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.ITEM_STACK);
    private int lumenTicks;

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    public void defineSynchedData(CallbackInfo ci) {
        ((LivingEntity)(Object)this).getEntityData().define(BANNER_STACK, ItemStack.EMPTY);
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("LumenTicks", this.lumenTicks);
        tag.put("BannerStack", ((LivingEntity)(Object)this).getEntityData().get(BANNER_STACK).save(new CompoundTag()));
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    public void readAdditionalSavaData(CompoundTag tag, CallbackInfo ci) {
        this.lumenTicks = tag.getInt("LumenTicks");
        this.setBanner(ItemStack.of(tag.getCompound("BannerStack")));
    }

    @Inject(at = @At("HEAD"), method = "aiStep")
    public void aiStep(CallbackInfo ci) {
        LivingEntity $this = LivingEntity.class.cast(this);
        if (!this.getBanner().isEmpty() && !$this.getItemBySlot(EquipmentSlot.HEAD).is(CTItems.STERLING_HELMET.get())) {
            ItemStack copy = this.getBanner();
            $this.spawnAtLocation(copy);
            this.setBanner(ItemStack.EMPTY);
        }
        for (int i = 0; i < 11; i++) {
            for (Direction direction : Direction.values()) {
                if ($this.level.getBlockState($this.blockPosition().relative(direction.getOpposite(), i)) == CTBlocks.LUMEN_BLOSSOM.get().defaultBlockState().setValue(LumenBlossomBlock.FACING, direction)) {
                    if ($this.isSpectator()) return;
                    this.lumenTicks = 300;
                }
            }
        }
        if (this.lumenTicks > 0) {
            this.lumenTicks--;
            int i = UniformInt.of(5, 7).sample($this.getRandom());
            for(int j = 0; j < i; ++j) {
                $this.level.addParticle(ParticleTypes.GLOW, $this.getRandomX(0.6D), $this.getRandomY(), $this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
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
