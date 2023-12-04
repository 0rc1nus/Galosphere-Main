package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

public class BerserkerRoar extends Behavior<Berserker> {

    public BerserkerRoar() {
        super(ImmutableMap.of(
                GMemoryModuleTypes.IS_ROARING, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.ROAR_SOUND_COOLDOWN, MemoryStatus.REGISTERED,
                MemoryModuleType.ROAR_SOUND_DELAY, MemoryStatus.REGISTERED
        ), 52);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Berserker livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Brain<Berserker> brain = livingEntity.getBrain();
        brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> BehaviorUtils.lookAtEntity(livingEntity, target));
        brain.setMemoryWithExpiry(MemoryModuleType.ROAR_SOUND_DELAY, Unit.INSTANCE, 10);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        livingEntity.setPose(Pose.ROARING);
    }

    @Override
    protected void tick(ServerLevel serverLevel, Berserker livingEntity, long l) {
        if (livingEntity.getBrain().hasMemoryValue(MemoryModuleType.ROAR_SOUND_DELAY) || livingEntity.getBrain().hasMemoryValue(MemoryModuleType.ROAR_SOUND_COOLDOWN)) {
            return;
        }
        livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ROAR_SOUND_COOLDOWN, Unit.INSTANCE, 42);
        livingEntity.playSound(GSoundEvents.BERSERKER_ROAR, 3.0f, 1.0f);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Berserker livingEntity, long l) {
        if (livingEntity.hasPose(Pose.ROARING)) {
            livingEntity.setPose(Pose.STANDING);
        }
        livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.IS_ROARING);
    }
}
