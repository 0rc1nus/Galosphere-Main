package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.util.CompatUtil;

public class SilverBomb extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(SilverBomb.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LAST_DURATION = SynchedEntityData.defineId(SilverBomb.class, EntityDataSerializers.INT);
    private int duration;
    private int explosion;
    private int bouncy;

    public SilverBomb(EntityType<? extends SilverBomb> entity, Level world) {
        super(entity, world);
    }

    public SilverBomb(Level world, LivingEntity entity, ItemStack stack) {
        super(GEntityTypes.SIVLER_BOMB, entity, world);
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
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected Item getDefaultItem() {
        return GItems.SILVER_BOMB;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isRemoved()) {
            if (this.level.isClientSide() && !this.isInWater()) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.3D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
            int i = this.getTime();
            int k = -this.getLastDuration();
            if (i > k) {
                this.setTime(i - 1);
            }
            if (!this.isInWater()) {
                if (i == k) {
                    if (!this.level.isClientSide()) {
                        this.bombExplode();
                    }
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        CompatUtil compatUtil = new CompatUtil();
        if (!this.level.isClientSide()) {
            this.bombExplode();
        }
    }

    private void bombExplode() {
        boolean flag = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        this.level.explode(this, null, new ExplosionDamageCalculator() {
            @Override
            public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float p_46098_) {
                return world.getBlockState(pos).getBlock().defaultDestroyTime() < 3.0D;
            }
        }, this.getX(), this.getY(), this.getZ(), 2.0F + this.explosion, false, flag ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemStack itemstack = this.getItemRaw();
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle((itemstack.isEmpty() ? GParticleTypes.SILVER_BOMB : new ItemParticleOption(ParticleTypes.ITEM, itemstack)), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        Vec3 deltaMovement = this.getDeltaMovement();
        Vec3 vec3 = deltaMovement.subtract(deltaMovement.x / 5, 0.0D, deltaMovement.z / 5);
        Direction direction = hit.getDirection();
        double booster = 0.3D + (bouncy / 10.0F);
        if (direction.getAxis() == Direction.Axis.Y) {
            this.setDeltaMovement(vec3.x, vec3.y < 0.0D ? -vec3.y * booster : 0.0D, vec3.z);
        }
        if (direction.getAxis() == Direction.Axis.X) {
            this.setDeltaMovement(vec3.x < 0.65D ? -vec3.x * booster * Mth.sin(Mth.PI / 2): 0.0D, vec3.y, vec3.z);
        }
        if (direction.getAxis() == Direction.Axis.Z) {
            this.setDeltaMovement(vec3.x, vec3.y, vec3.z < 0.65D ? -vec3.z * booster * Mth.sin(3 * Mth.PI / 4) : 0.0D);
        }
        if (!this.level.isClientSide() && this.isInWater()) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

}
