package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.entities.ai.PreservedAi;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypeTags;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GSensorTypes;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Preserved extends Monster {
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Preserved>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, GSensorTypes.PRESERVED_ENTITY_SENSOR);
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.IS_EMERGING);
    private boolean fromChamber;
    public AnimationState digAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();

    public Preserved(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.fromChamber = compoundTag.getBoolean("FromChamber");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromChamber", this.fromChamber);
    }

    @Override
    public boolean shouldDropExperience() {
        return !this.fromChamber;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int i, boolean bl) {
        super.dropCustomDeathLoot(damageSource, i, bl);
        if (this.level().getRandom().nextFloat() > 0.25F && this.isFromChamber()) {
            this.spawnAtLocation(new ItemStack(GBlocks.PINK_SALT_CLUSTER));
        }
    }

    public boolean isFromChamber() {
        return this.fromChamber;
    }

    public void setFromChamber(boolean fromChamber) {
        this.fromChamber = fromChamber;
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
            if (this.getPose() == Pose.EMERGING) {
                this.digAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType == MobSpawnType.TRIGGERED) {
            this.setPose(Pose.EMERGING);
            this.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, 40);
            this.playSound(GSoundEvents.PRESERVED_EMERGE, 1.0f, 1.0f);
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (this.hasPose(Pose.EMERGING) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return GSoundEvents.PRESERVED_IDLE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return GSoundEvents.PRESERVED_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GSoundEvents.PRESERVED_HURT;
    }

    protected SoundEvent getStepSound() {
        return GSoundEvents.PRESERVED_STEP;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        playSound(getStepSound(), 0.15F, 1);
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
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.getPose() == Pose.EMERGING) {
                if ((float)this.digAnimationState.getAccumulatedTime() < 2000.0F) {
                    RandomSource randomSource = this.getRandom();
                    BlockState blockState = this.getBlockStateOn();
                    if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
                        for (int i = 0; i < 30; ++i) {
                            double d = this.getX() + (double) Mth.randomBetween(randomSource, -0.7f, 0.7f);
                            double e = this.getY();
                            double f = this.getZ() + (double)Mth.randomBetween(randomSource, -0.7f, 0.7f);
                            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
                        }
                    }
                }
            }
        }
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
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        }
        Predicate<LivingEntity> predicate = e -> e.getType().is(GEntityTypeTags.PRESERVED_INVALID_TARGETS);
        if (livingEntity.isInvulnerable() || livingEntity.isDeadOrDying() || predicate.test(livingEntity)) {
            return false;
        }
        DamageSource lastSource = this.getLastDamageSource();
        Entity e;
        if (lastSource != null && (e = lastSource.getEntity()) instanceof LivingEntity living && e == livingEntity && !predicate.test(living)) {
            return true;
        }
        if (this.level() != entity.level() || !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) || this.isAlliedTo(entity) || !this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox())) {
            return false;
        }
        return livingEntity instanceof Player || livingEntity instanceof AbstractVillager || livingEntity instanceof IronGolem || livingEntity instanceof Turtle;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0).add(Attributes.MOVEMENT_SPEED, 0.26f).add(Attributes.ATTACK_DAMAGE, 4.0).add(Attributes.ARMOR, 2.0);
    }

}
