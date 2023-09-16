package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.orcinus.galosphere.entities.ai.BlightedAi;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GSensorTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class Blighted extends Monster {
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Blighted>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, GSensorTypes.BLIGHTED_ENTITY_SENSOR);
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.AVOID_TARGET, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, GMemoryModuleTypes.IS_ROARING, GMemoryModuleTypes.UNDERMINE_COOLDOWN);
    private static final EntityDataAccessor<String> PHASE = SynchedEntityData.defineId(Blighted.class, EntityDataSerializers.STRING);
    private final List<MobEffect> selectedEffects = Util.make(Lists.newArrayList(), list -> {
        list.add(GMobEffects.BLOCK_BANE);
        list.add(MobEffects.DIG_SLOWDOWN);
    });
    public AnimationState roarAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState undermineAnimationState = new AnimationState();

    public Blighted(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PHASE, Phase.IDLING.name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        String phase = compoundTag.getString("Phase");
        if (!phase.isEmpty()) {
            this.setPhase(Phase.valueOf(phase));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("Phase", this.getPhase().name());
    }

    public void setPhase(Phase phase) {
        this.entityData.set(PHASE, phase.name());
    }

    public Phase getPhase() {
        String s = this.entityData.get(PHASE);
        return Phase.valueOf(s);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setPose(Pose.ROARING);
        this.getBrain().setMemoryWithExpiry(GMemoryModuleTypes.IS_ROARING, Unit.INSTANCE, 52);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    public boolean canTargetEntity(@Nullable Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return false;
        if (this.level() != entity.level()) return false;
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) return false;
        if (this.isAlliedTo(entity)) return false;
        if (livingEntity.getType() == EntityType.ARMOR_STAND) return false;
        if (livingEntity.getType() == GEntityTypes.BLIGHTED) return false;
        if (livingEntity.isInvulnerable()) return false;
        if (livingEntity.isDeadOrDying()) return false;
        return this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
    }

    @Override
    protected void updateWalkAnimation(float f) {
        float g = Math.min(f * 10.0F, 1.0f);
        this.walkAnimation.update(g, 0.2f);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == Pose.ROARING) {
                this.roarAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else if (b == 62) {
            this.undermineAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof AbstractArrow) {
            return false;
        }
        return super.hurt(damageSource, f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Blighted.createMonsterAttributes().add(Attributes.ARMOR, 4.0D).add(Attributes.MAX_HEALTH, 200.0).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 25.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 1.5D);
    }

    @Override
    protected Brain.Provider<Blighted> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return BlightedAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<Blighted> getBrain() {
        return (Brain<Blighted>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("blightedBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        BlightedAi.updateActivity(this);
        super.customServerAiStep();
        if ((this.tickCount + this.getId()) % 1200 == 0) {
            this.selectedEffects.forEach(mobEffect -> {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(mobEffect, 6000, 2);
                MobEffectUtil.addEffectToPlayersAround((ServerLevel) this.level(), this, this.position(), 50.0, mobEffectInstance, 1200);
            });
        }
    }

    public enum Phase {
        IDLING,
        SMASH,
        UNDERMINE
    }

}
