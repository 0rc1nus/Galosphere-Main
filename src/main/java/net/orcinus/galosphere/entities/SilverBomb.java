package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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
    private boolean shrapnel;
    private int duration;
    private int explosion;
    private int bouncy;

    public SilverBomb(EntityType<? extends SilverBomb> entity, Level world) {
        super(entity, world);
    }

    public SilverBomb(Level world, LivingEntity entity, ItemStack stack) {
        super(GEntityTypes.SIVLER_BOMB.get(), entity, world);
        if (!stack.isEmpty() && stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                this.explosion = tag.getInt("Explosion");
                this.duration = tag.getInt("Duration");
                this.bouncy = tag.getInt("Bouncy");
                this.shrapnel = tag.getBoolean("Shrapnel");
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
        this.shrapnel = tag.getBoolean("Shrapnel");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("LastDuration", this.getLastDuration());
        tag.putInt("Time", this.getTime());
        tag.putInt("Duration", this.duration);
        tag.putInt("Explosion", this.explosion);
        tag.putInt("Bouncy", this.bouncy);
        tag.putBoolean("Sharpnel", this.shrapnel);
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
        return GItems.SILVER_BOMB.get();
    }

    @Override
    public void tick() {
        super.tick();
        CompatUtil compatUtil = new CompatUtil();
        if (!this.isRemoved()) {
            if (this.level().isClientSide() && !this.isInWater()) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.3D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
            int i = this.getTime();
            int k = -this.getLastDuration();
            if (i > k) {
                this.setTime(i - 1);
            }
            if (!this.isInWater()) {
                if (i == k) {
                    if (!this.level().isClientSide()) {
                        bombExplode(compatUtil);
                    }
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        CompatUtil compatUtil = new CompatUtil();
        if (!this.level().isClientSide()) {
            this.bombExplode(compatUtil);
        }
    }

    private void bombExplode(CompatUtil compatUtil) {
        if (this.shrapnel && compatUtil.isModInstalled("oreganized")) {
            this.shrapnelExplode(compatUtil);
        } else {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
            this.level().explode(this, null, new ExplosionDamageCalculator() {
                @Override
                public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float p_46098_) {
                    return world.getBlockState(pos).getBlock().defaultDestroyTime() < 3.0D;
                }
            }, this.getX(), this.getY(), this.getZ(), 2.0F + this.explosion, false, flag ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    public void shrapnelExplode(CompatUtil compatUtil) {
        String modid = "oreganized";
        SimpleParticleType LEAD_SHRAPNEL = compatUtil.getCompatParticle(modid, "lead_shrapnel");
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 6.0F, Level.ExplosionInteraction.NONE);

        //Source: https://github.com/GL33P-0R4NG3/oreganized/blob/1.0.0-release-1.18.x/src/main/java/me/gleep/oreganized/entities/PrimedShrapnelBomb.java

        if (!this.level().isClientSide()) ((ServerLevel) this.level()).sendParticles(LEAD_SHRAPNEL, this.getX(), this.getY(0.0625D),                 this.getZ(), 100, 0.0D, 0.0D, 0.0D, 5);
            int primedShrapnelBombRadius = 30;
            int radius = (primedShrapnelBombRadius / 4) + explosion;
        for (Entity entity : this.level().getEntities(this, new AABB(this.getX() - radius, this.getY() - 4, this.getZ() - radius, this.getX() + radius, this.getY() + 4, this.getZ() + radius))) {
            int random = (int) (Math.random() * 100);
            boolean shouldPoison = false;
            if (entity.distanceToSqr(this) <= 4 * 4) {
                shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 8 * 8) {
                if (random < 60) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 15 * 15) {
                if (random < radius) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= radius * radius) {
                if (random < 5) shouldPoison = true;
            }
            if (shouldPoison) {
                if (entity instanceof LivingEntity livingEntity) {
                    MobEffect STUNNED = compatUtil.getCompatEffect(modid, "stunned");
                    livingEntity.hurt(this.level().damageSources().magic(), 2);
                    livingEntity.addEffect(new MobEffectInstance(STUNNED, 40 * 20));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 260));
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemStack itemstack = this.getItemRaw();
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle((itemstack.isEmpty() ? GParticleTypes.SILVER_BOMB.get() : new ItemParticleOption(ParticleTypes.ITEM, itemstack)), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
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
        if (direction == Direction.UP || direction == Direction.DOWN) {
            this.setDeltaMovement(vec3.x, vec3.y < 0.0D ? -vec3.y * booster : 0.0D, vec3.z);
        }
        if (direction == Direction.WEST || direction == Direction.EAST) {
            this.setDeltaMovement(vec3.x < 0.65D ? -vec3.x * booster * Mth.sin(Mth.PI / 2): 0.0D, vec3.y, vec3.z);
        }
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            this.setDeltaMovement(vec3.x, vec3.y, vec3.z < 0.65D ? -vec3.z * booster * Mth.sin(3 * Mth.PI / 4) : 0.0D);
        }
        if (!this.level().isClientSide() && this.isInWater()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

}
