package net.orcinus.galosphere.entities.ai.tasks;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

public class Smash extends Behavior<Berserker> {
    private static final int DURATION = Mth.ceil(27.0F);
    private static final int MAX_DURATION = 60;

    public Smash() {
        super(ImmutableMap.of(GMemoryModuleTypes.RAMPAGE_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, GMemoryModuleTypes.IS_SMASHING, MemoryStatus.REGISTERED, GMemoryModuleTypes.IS_IMPALING, MemoryStatus.VALUE_ABSENT, GMemoryModuleTypes.IS_SUMMONING, MemoryStatus.VALUE_ABSENT, GMemoryModuleTypes.SMASHING_COOLDOWN, MemoryStatus.VALUE_ABSENT), MAX_DURATION);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Berserker livingEntity) {
        Optional<LivingEntity> memory = livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        return livingEntity.shouldAttack() && memory.filter(livingEntity::isWithinMeleeAttackRange).isPresent();
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Berserker livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Brain<Berserker> brain = livingEntity.getBrain();
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, DURATION);
        brain.setMemoryWithExpiry(GMemoryModuleTypes.IS_SMASHING, Unit.INSTANCE, MAX_DURATION);
        livingEntity.setPhase(Berserker.Phase.SMASH);
        livingEntity.playSound(GSoundEvents.BERSERKER_DUO_SMASH, 10.0f, 1.0F);
    }

    @Override
    protected void tick(ServerLevel level, Berserker self, long l) {

        self.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> self.getLookControl().setLookAt(target.position()));
        self.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        if (self.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_COOLING_DOWN)) return;

        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, self.getBoundingBox().inflate(8))
                .stream()
                .filter(Predicate.not(Preserved.class::isInstance))
                .filter(LivingEntity::isAlive)
                .filter(livingEntity -> livingEntity.getUUID() != self.getUUID())
                .toList();

        for (LivingEntity enemy : list) {
            Vec3 selfPos = self.position().add(0, 1.6f, 0);
            Vec3 enemyPos = enemy.getEyePosition().subtract(selfPos);
            Vec3 normalizedDirection = enemyPos.normalize();

            double knockbackX = 0.5 * (1 - enemy.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            double knockbackY = 2.5 * (1 - enemy.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));

            double distanceFromEnemy = self.distanceTo(enemy);
            boolean canDamage = true;
            if (distanceFromEnemy > 3 && !enemy.onGround()) canDamage = false;

            if (canDamage) {
                self.doHurtTarget(enemy);
                self.heal(10.0F);
                enemy.push(normalizedDirection.x() * knockbackY, normalizedDirection.y() * knockbackX, normalizedDirection.z() * knockbackY);
            }
        }

        self.level().broadcastEntityEvent(self, (byte) 32);
        self.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, MAX_DURATION - DURATION);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.setPhase(Berserker.Phase.IDLING);
        if (livingEntity.shouldUseMeleeAttack()) {
            livingEntity.getBrain().setMemoryWithExpiry(GMemoryModuleTypes.SMASHING_COOLDOWN, Unit.INSTANCE, 100);
        }
    }
}
