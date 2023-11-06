package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.orcinus.galosphere.entities.ai.PreservedAi;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GSensorTypes;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

public class Preserved extends Monster {
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Preserved>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, GSensorTypes.PRESERVED_ENTITY_SENSOR.get());
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.HURT_BY_ENTITY);
    public AnimationState digAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();

    public Preserved(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        return super.hurt(damageSource, f);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == Pose.DIGGING) {
                this.digAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setPose(Pose.DIGGING);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return GSoundEvents.PINK_SALT.getBreakSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GSoundEvents.PRESERVED_HURT.get();
    }

    @Override
    protected Brain.Provider<Preserved> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return PreservedAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<Preserved> getBrain() {
        return (Brain<Preserved>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("preservedBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        PreservedAi.updateActivity(this);
        super.customServerAiStep();
    }

    public boolean canTargetEntity(@Nullable Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return false;
        if (this.level() != entity.level()) return false;
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) return false;
        if (this.isAlliedTo(entity)) return false;
        if (livingEntity.getType() == EntityType.ARMOR_STAND) return false;
        if (livingEntity.getType() == GEntityTypes.BERSERKER.get()) return false;
        if (livingEntity.getType() == GEntityTypes.PRESERVED.get()) return false;
        if (livingEntity.isInvulnerable()) return false;
        if (livingEntity.isDeadOrDying()) return false;
        return this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0).add(Attributes.MOVEMENT_SPEED, 0.23f).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.ARMOR, 2.0);
    }

}