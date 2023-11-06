package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.mixin.ProjectileAccessor;

public abstract class ThrowableLaunchedProjectile extends FireworkRocketEntity {
    protected static final EntityDataAccessor<Boolean> THROWN = SynchedEntityData.defineId(ThrowableLaunchedProjectile.class, EntityDataSerializers.BOOLEAN);

    public ThrowableLaunchedProjectile(EntityType<? extends FireworkRocketEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrowableLaunchedProjectile(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(THROWN, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setThrown(compoundTag.getBoolean("Thrown"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Thrown", this.isThrown());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
    }

    public boolean isThrown() {
        return this.entityData.get(THROWN);
    }

    public void setThrown(boolean thrown) {
        this.entityData.set(THROWN, thrown);
    }

    @Override
    public void tick() {
        if (this.isThrown()) {
            ProjectileAccessor accessor = (ProjectileAccessor) this;
            float h;
            if (!accessor.isHasBeenShot()) {
                this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
                accessor.setHasBeenShot(true);
            }
            if (!accessor.isLeftOwner()) {
                accessor.setLeftOwner(accessor.callCheckLeftOwner());
            }
            this.baseTick();
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            boolean bl = false;
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                BlockState blockState = this.level().getBlockState(blockPos);
                if (blockState.is(Blocks.NETHER_PORTAL)) {
                    this.handleInsidePortal(blockPos);
                    bl = true;
                } else if (blockState.is(Blocks.END_GATEWAY)) {
                    BlockEntity blockEntity = this.level().getBlockEntity(blockPos);
                    if (blockEntity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                        TheEndGatewayBlockEntity.teleportEntity(this.level(), blockPos, blockState, this, (TheEndGatewayBlockEntity)blockEntity);
                    }
                    bl = true;
                }
            }
            if (hitResult.getType() != HitResult.Type.MISS && !bl) {
                this.onHit(hitResult);
            }
            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d = this.getX() + vec3.x;
            double e = this.getY() + vec3.y;
            double f = this.getZ() + vec3.z;
            this.updateRotation();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d - vec3.x * 0.25, e - vec3.y * 0.25, f - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
                }
                h = 0.8f;
            } else {
                h = 0.99f;
            }
            this.setDeltaMovement(vec3.scale(h));
            if (!this.isNoGravity()) {
                Vec3 vec32 = this.getDeltaMovement();
                this.setDeltaMovement(vec32.x, vec32.y - (double)this.getGravity(), vec32.z);
            }
            this.setPos(d, e, f);
        } else {
            super.tick();
            this.handleLaunchedProjectile();
        }
    }

    public float getGravity() {
        return 0.03F;
    }


    public void handleLaunchedProjectile() {
    }

    protected abstract Item getDefaultItem();

    @Override
    public ItemStack getItem() {
        return new ItemStack(this.getDefaultItem());
    }
}