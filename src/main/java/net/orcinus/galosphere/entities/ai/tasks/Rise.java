package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.orcinus.galosphere.entities.Preserved;

public class Rise extends Behavior<Preserved> {

    public Rise(int i) {
        super(ImmutableMap.of(
                MemoryModuleType.IS_EMERGING, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED
        ), i);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Preserved livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Preserved livingEntity, long l) {
        livingEntity.setPose(Pose.EMERGING);
//        livingEntity.playSound(SoundEvents.WARDEN_EMERGE, 1.0F, 1.0F);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Preserved livingEntity, long l) {
        if (livingEntity.hasPose(Pose.EMERGING)) {
            livingEntity.setPose(Pose.STANDING);
        }
    }

}
