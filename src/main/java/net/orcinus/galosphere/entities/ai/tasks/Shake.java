package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

public class Shake extends Behavior<Berserker> {

    public Shake() {
        super(ImmutableMap.of(
                GMemoryModuleTypes.IS_SHAKING, MemoryStatus.VALUE_PRESENT
        ), 20);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Berserker livingEntity) {
        return livingEntity.getStationaryTicks() > 0;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Berserker livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.setPose(Pose.EMERGING);
        livingEntity.playSound(GSoundEvents.BERSERKER_SHAKE, 1.0f, 1.0f);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Brain<Berserker> brain = livingEntity.getBrain();
        if (livingEntity.hasPose(Pose.EMERGING)) {
            livingEntity.setPose(Pose.STANDING);
        }
        brain.eraseMemory(GMemoryModuleTypes.IS_SHAKING);
        livingEntity.playSound(GSoundEvents.BERSERKER_ROAR, 3.0F, 1.0F);
    }
}
