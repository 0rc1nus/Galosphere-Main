package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

import java.util.Optional;

public class Smash extends Behavior<Berserker> {
    private static final int DURATION = Mth.ceil(27.0F);

    public Smash() {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT), 60);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Berserker livingEntity) {
        Optional<LivingEntity> memory = livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        if (livingEntity.getPhase() != Berserker.Phase.IDLING || livingEntity.isStationary()) {
            return false;
        }
        return memory.filter(livingEntity::isWithinMeleeAttackRange).isPresent();
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Berserker livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, DURATION);
        serverLevel.broadcastEntityEvent(livingEntity, (byte)4);
        livingEntity.setPhase(Berserker.Phase.SMASH);
        livingEntity.playSound(GSoundEvents.BERSERKER_SMASH, 10.0f, 1.0F);
    }

    @Override
    protected void tick(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> livingEntity.getLookControl().setLookAt(target.position()));
        livingEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        if (livingEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_COOLING_DOWN)) {
            return;
        }
        for (LivingEntity mob : serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(4.5D))) {
            if (mob.isAlive() && mob != livingEntity && mob.getType() != GEntityTypes.BERSERKER) {
                Vec3 vec3 = livingEntity.position().add(0.0, 1.6f, 0.0);
                Vec3 vec32 = mob.getEyePosition().subtract(vec3);
                Vec3 vec33 = vec32.normalize();
                double d = 0.5 * (1.0 - mob.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double e = 2.5 * (1.0 - mob.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                livingEntity.doHurtTarget(mob);
                mob.push(vec33.x() * e, vec33.y() * d, vec33.z() * e);
            }
        }
        livingEntity.heal(5.0F);
        livingEntity.level().broadcastEntityEvent(livingEntity, (byte)32);
        livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, 60 - DURATION);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.setPhase(Berserker.Phase.IDLING);
    }
}
