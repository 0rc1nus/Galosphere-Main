package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.ai.BerserkerAi;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GSensorTypes;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class Berserker extends Monster {
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Berserker>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, GSensorTypes.BLIGHTED_ENTITY_SENSOR);
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.AVOID_TARGET, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, GMemoryModuleTypes.IMPALING_COOLDOWN, GMemoryModuleTypes.IMPALING_COUNT, GMemoryModuleTypes.IS_SMASHING, GMemoryModuleTypes.IS_IMPALING, GMemoryModuleTypes.IS_SUMMONING, GMemoryModuleTypes.SUMMONING_COOLDOWN, GMemoryModuleTypes.SUMMON_COUNT, GMemoryModuleTypes.SMASHING_COOLDOWN, GMemoryModuleTypes.HURT_COUNT, GMemoryModuleTypes.RAMPAGE_TICKS, MemoryModuleType.ROAR_SOUND_COOLDOWN, MemoryModuleType.ROAR_SOUND_DELAY, GMemoryModuleTypes.IS_SHAKING);
    private static final EntityDataAccessor<String> PHASE = SynchedEntityData.defineId(Berserker.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> STATIONARY_TICKS = SynchedEntityData.defineId(Berserker.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHEDDING = SynchedEntityData.defineId(Berserker.class, EntityDataSerializers.BOOLEAN);
    private final List<MobEffect> selectedEffects = Util.make(Lists.newArrayList(), list -> {
        list.add(GMobEffects.BLOCK_BANE);
        list.add(MobEffects.DIG_SLOWDOWN);
    });
    public AnimationState roarAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState punchAnimationState = new AnimationState();
    public AnimationState impalingAnimationState = new AnimationState();
    public AnimationState summoningAnimationState = new AnimationState();

    public Berserker(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new BerserkerLookControl(this);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player && !player.getAbilities().instabuild && this.getStationaryTicks() > 0) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PHASE, Phase.IDLING.name());
        this.entityData.define(STATIONARY_TICKS, 0);
        this.entityData.define(SHEDDING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        String phase = compoundTag.getString("Phase");
        if (!phase.isEmpty()) {
            this.setPhase(Phase.valueOf(phase));
        }
        this.setStationaryTicks(compoundTag.getInt("StationaryTicks"));
        this.setShedding(compoundTag.getBoolean("Shedding"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("Phase", this.getPhase().name());
        compoundTag.putInt("StationaryTicks", this.getStationaryTicks());
        compoundTag.putBoolean("Shedding", this.isShedding());
    }

    public boolean shouldAttack() {
        return this.getPhase() == Phase.IDLING && !this.isStationary();
    }

    public int getStage() {
        float health = this.getHealth() / this.getMaxHealth();
        if (this.isStationary()) {
            return 3;
        } else if (health > 0.66F) {
            return 0;
        } else if (health <= 0.66F && health > 0.33F) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setShedding(boolean shedding) {
        this.entityData.set(SHEDDING, shedding);
    }

    public boolean isShedding() {
        return this.entityData.get(SHEDDING);
    }

    public void setStationaryTicks(int stationaryTicks) {
        this.entityData.set(STATIONARY_TICKS, stationaryTicks);
    }

    public int getStationaryTicks() {
        return this.entityData.get(STATIONARY_TICKS);
    }

    public void setPhase(Phase phase) {
        if (phase == Phase.IDLING) {
            this.setPose(Pose.STANDING);
        } else if (phase == Phase.SMASH) {
            this.level().broadcastEntityEvent(this, (byte)4);
        } else if (phase == Phase.UNDERMINE) {
            this.level().broadcastEntityEvent(this, (byte)6);
        } else if (phase == Phase.SUMMONING) {
            this.level().broadcastEntityEvent(this, (byte)7);
        }
        this.entityData.set(PHASE, phase.name());
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else if (b == 5) {
            this.punchAnimationState.start(this.tickCount);
        } else if (b == 6) {
            this.impalingAnimationState.start(this.tickCount);
        } else if (b == 7) {
            this.summoningAnimationState.start(this.tickCount);
        } else if (b == 32) {
            BlockPos blockPos = this.getOnPos();
            this.level().addParticle(GParticleTypes.IMPACT, blockPos.getX() + 0.5D, blockPos.getY() + 1.15, blockPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
        }else {
            super.handleEntityEvent(b);
        }
    }

    public Phase getPhase() {
        String s = this.entityData.get(PHASE);
        return Phase.valueOf(s);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        double range = 0.75D;
        double threshold = range - 0.6D;
        double increment = 0.2D;
        if (!this.level().isClientSide) {
            int count = this.level().getDifficulty() == Difficulty.HARD ? 125 : 250;
            boolean stationary = this.isStationary();
            boolean shedding = this.isShedding();
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % count == 0) {
                this.heal(10.0f);
            }
            if (stationary) {
                this.getBrain().getMemories().keySet().stream().filter(memoryModuleType -> {
                    return memoryModuleType.equals(MemoryModuleType.WALK_TARGET) || memoryModuleType.equals(MemoryModuleType.LOOK_TARGET);
                }).forEach(this.getBrain()::eraseMemory);
                Optional<Player> player = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(3.0D)).stream().filter(p -> !p.isCreative() && p.isAlive()).findAny();
                if (!shedding) {
                    player.ifPresent(this::setTarget);
                } else {
                    if (this.getStationaryTicks() == 32) {
                        this.getBrain().setMemory(GMemoryModuleTypes.IS_SHAKING, Unit.INSTANCE);
                    }
                    this.setStationaryTicks(this.getStationaryTicks() - 1);
                    this.addParticles(range, increment, threshold);
                }
            } else {
                if (shedding) {
                    this.setShedding(false);
                    this.setPersistenceRequired();
                }
            }
        }
    }

    private void addParticles(double range, double increment, double threshold) {
        if (this.tickCount % 20 == 0) {
            for (double y = 0; y <= 1.95D; y += 0.35D) {
                for (double x = -range; x <= range; x += increment) {
                    for (double z = -range; z <= range; z += increment) {
                        if (x >= -threshold && x <= threshold || z >= -threshold && z <= threshold) {
                            continue;
                        }
                        ((ServerLevel) this.level()).sendParticles(GParticleTypes.PINK_SALT_FALLING_DUST, this.getX() + x, this.getY() + y, this.getZ() + z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }

    public boolean isStationary() {
        return this.getStationaryTicks() > 0;
    }

    private void setTarget(Player player) {
        Brain<Berserker> brain = this.getBrain();
        brain.setMemory(MemoryModuleType.ATTACK_TARGET, player);
        this.setShedding(true);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType == MobSpawnType.STRUCTURE) {
            this.setStationaryTicks(200);
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isStationary() ? null : GSoundEvents.BERSERKER_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GSoundEvents.BERSERKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return GSoundEvents.BERSERKER_DEATH;
    }

    protected SoundEvent getStepSound() {
        return GSoundEvents.BERSERKER_STEP;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        playSound(getStepSound(), 1, 1);
    }

    public boolean canTargetEntity(@Nullable Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return false;
        if (this.level() != entity.level()) return false;
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) return false;
        if (this.isAlliedTo(entity)) return false;
        if (livingEntity.getType() == EntityType.ARMOR_STAND) return false;
        if (livingEntity.getType() == GEntityTypes.BERSERKER) return false;
        if (livingEntity.getType() == GEntityTypes.PRESERVED) return false;
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
            if (this.getPose() == Pose.EMERGING) {
                this.roarAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    public boolean shouldUseMeleeAttack() {
        Optional<LivingEntity> memory = this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        return memory.filter(livingEntity -> this.isWithinMeleeAttackRange(livingEntity) && this.getPhase() != Phase.SMASH && this.shouldAttack() && this.isInHardMode() && this.getBrain().getMemory(GMemoryModuleTypes.RAMPAGE_TICKS).isPresent() && this.getBrain().getMemory(GMemoryModuleTypes.RAMPAGE_TICKS).get() > 0).isPresent();
    }

    public boolean isInHardMode() {
        return this.level().getDifficulty() == Difficulty.HARD;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
            if (this.shouldUseMeleeAttack()) {
                Vec3 start = this.position().add(0, 1.6f, 0);
                Vec3 diff = entity.getEyePosition().subtract(start);
                Vec3 normalized = diff.normalize();
                double knockbackX = 0.25 * (1 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double knockbackY = 1.5 * (1 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                livingEntity.push(normalized.x() * knockbackY, normalized.y() * knockbackX, normalized.z() * knockbackY);
                this.level().broadcastEntityEvent(this, (byte) 5);
                this.playSound(GSoundEvents.BERSERKER_PUNCH, 1, 1);
            }
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (!this.level().isClientSide && this.getPhase() != Phase.IDLING && this.isInHardMode()) {
            if (this.getBrain().getMemory(GMemoryModuleTypes.HURT_COUNT).isEmpty()) {
                this.getBrain().setMemory(GMemoryModuleTypes.HURT_COUNT, 0);
            } else {
                int i = this.getBrain().getMemory(GMemoryModuleTypes.HURT_COUNT).get() + 1;
                if (i > 2) {
                    i = 0;
                    this.getBrain().setMemory(GMemoryModuleTypes.RAMPAGE_TICKS, UniformInt.of(30, 150).sample(this.getRandom()));
                }
                this.getBrain().setMemory(GMemoryModuleTypes.HURT_COUNT, i);
            }
        }
        if (damageSource.getDirectEntity() instanceof AbstractArrow && this.getPhase() != Phase.IDLING) {
            return false;
        }
        return super.hurt(damageSource, f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Berserker.createMonsterAttributes().add(Attributes.MAX_HEALTH, 250.0D).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 20.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 1.5D);
    }

    @Override
    protected Brain.Provider<Berserker> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return BerserkerAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<Berserker> getBrain() {
        return (Brain<Berserker>) super.getBrain();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("blightedBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        BerserkerAi.updateActivity(this);
        super.customServerAiStep();
        if ((this.tickCount + this.getId()) % 1200 == 0) {
            this.selectedEffects.forEach(mobEffect -> {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(mobEffect, 6000, 2);
                MobEffectUtil.addEffectToPlayersAround((ServerLevel) this.level(), this, this.position(), 50.0, mobEffectInstance, 1200);
            });
        }
        if (this.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).isEmpty() && this.getPhase() != Phase.IDLING) {
            this.setPhase(Phase.IDLING);
        }
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.isStationary() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    public class BerserkerLookControl extends LookControl {

        public BerserkerLookControl(Mob mob) {
            super(mob);
        }

        @Override
        public void tick() {
            if (!Berserker.this.isStationary()) {
                super.tick();
            }
        }
    }

    public enum Phase {
        IDLING,
        SMASH,
        UNDERMINE,
        SUMMONING
    }

}
