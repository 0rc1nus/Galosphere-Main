package net.orcinus.galosphere.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.entities.ai.SpecterpillarAi;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSensorTypes;
import net.orcinus.galosphere.init.GSoundEvents;

public class Specterpillar extends PathfinderMob {
    protected static final ImmutableList<SensorType<? extends Sensor<? super Specterpillar>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, GSensorTypes.SPECTRE_TEMPTATIONS.get(), GSensorTypes.NEAREST_LICHEN_MOSS.get());
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.BREED_TARGET, MemoryModuleType.IS_PANICKING, GMemoryModuleTypes.CAN_BURY.get(), GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get());
    public final AnimationState burrowAnimationState = new AnimationState();
    private int age;

    public Specterpillar(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0F;
    }

    @Override
    public boolean isPushable() {
        return this.getPose() != Pose.DIGGING && super.isPushable();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == Pose.DIGGING) {
                this.burrowAnimationState.start(this.tickCount);
            } else {
                this.burrowAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0).add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    protected Brain.Provider<Specterpillar> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    public Brain<Specterpillar> getBrain() {
        return (Brain<Specterpillar>) super.getBrain();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return SpecterpillarAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return GSoundEvents.SPECTERPILLAR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return GSoundEvents.SPECTERPILLAR_DEATH.get();
    }

    @Override
    protected void customServerAiStep() {
        this.level.getProfiler().push("specterpillarBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("specterpillarActivityUpdate");
        SpecterpillarAi.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    public void setAge(int i) {
        this.age = i;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        MemoryModuleType<Boolean> moduleType = GMemoryModuleTypes.CAN_BURY.get();
        if (!this.level.isClientSide && stack.is(GItemTags.SPECTRE_TEMPT_ITEMS) && !this.getBrain().hasMemoryValue(moduleType)) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (this.getRandom().nextInt(5) == 0) {
                this.getBrain().setMemory(moduleType, true);
            }
            this.level.broadcastEntityEvent(this, (byte) 4);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Age", this.getAge());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAge(compoundTag.getInt("Age"));
    }

}
