package net.orcinus.cavesandtrenches.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.orcinus.cavesandtrenches.init.CTEntityTypes;
import net.orcinus.cavesandtrenches.init.CTItems;

public class SilverBombEntity extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(SilverBombEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LAST_DURATION = SynchedEntityData.defineId(SilverBombEntity.class, EntityDataSerializers.INT);
    private int duration;
    private int explosion;
    private int bouncy;

    public SilverBombEntity(EntityType<? extends SilverBombEntity> entity, Level world) {
        super(entity, world);
    }

    public SilverBombEntity(Level world, LivingEntity entity, ItemStack stack) {
        super(CTEntityTypes.SIVLER_BOMB.get(), entity, world);
        if (!stack.isEmpty() && stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                this.explosion = tag.getInt("Explosion");
                this.duration = tag.getInt("Duration");
                this.bouncy = tag.getInt("Bouncy");
            }
        }
        this.entityData.set(LAST_DURATION, this.duration * 20);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TIME, 40);
        this.entityData.define(LAST_DURATION, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setTime(tag.getInt("Time"));
        this.setLastDuration(tag.getInt("LastDuration"));
        this.duration = tag.getInt("Duration");
        this.explosion = tag.getInt("Explosion");
        this.bouncy = tag.getInt("Bouncy");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("LastDuration", this.getLastDuration());
        tag.putInt("Time", this.getTime());
        tag.putInt("Duration", this.duration);
        tag.putInt("Explosion", this.explosion);
        tag.putInt("Bouncy", this.bouncy);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isRemoved()) {
            int i = this.getTime();
            int k = -this.getLastDuration();
            if (i > k) {
                this.setTime(i - 1);
            }
            if (!this.isInWater()) {
                if (i == k) {
                    if (!this.level.isClientSide()) {
                        this.level.explode(this, null, new ExplosionDamageCalculator() {
                            @Override
                            public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float p_46098_) {
                                return world.getBlockState(pos).getBlock().defaultDestroyTime() < 3.0D;
                            }
                        }, this.getX(), this.getY(), this.getZ(), 2.0F + this.explosion, false, Explosion.BlockInteraction.BREAK);
                    }
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    public void setTime(int time) {
        this.entityData.set(TIME, time);
    }

    public int getTime() {
        return this.entityData.get(TIME);
    }

    public void setLastDuration(int lastDuration) {
        this.entityData.set(LAST_DURATION, lastDuration);
    }

    public int getLastDuration() {
        return this.entityData.get(LAST_DURATION);
    }

    @Override
    protected Item getDefaultItem() {
        return CTItems.SILVER_BOMB.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemStack itemstack = this.getItemRaw();
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle((itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack)), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        Vec3 vec3 = this.getDeltaMovement();
        double booster = 0.3D + (bouncy / 10.0F);
        double x = vec3.x < 0.5D ? -vec3.x * booster : 0.0D;
        double y = vec3.y < 0.5D ? -vec3.y * booster : 0.0D;
        double z = vec3.z < 0.5D ? -vec3.z * booster : 0.0D;
        this.setDeltaMovement(x, y, z);
        if (!this.level.isClientSide() && this.isInWater()) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}
