package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.orcinus.galosphere.entities.SpecterpillarEntity;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Optional;

public class PathfindBurrowSpot extends Behavior<SpecterpillarEntity> {

    public PathfindBurrowSpot() {
        super(ImmutableMap.of(GMemoryModuleTypes.NEAREST_LICHEN_MOSS, MemoryStatus.VALUE_PRESENT, GMemoryModuleTypes.CAN_BURY, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));
    }

    @Override
    protected void start(ServerLevel serverLevel, SpecterpillarEntity livingEntity, long l) {
        this.getTargetPos(livingEntity).ifPresent(blockPos -> BehaviorUtils.setWalkAndLookTargetMemories(livingEntity, blockPos, 0.75F, 0));
    }

    public Optional<BlockPos> getTargetPos(SpecterpillarEntity specterpillar) {
        return specterpillar.getBrain().getMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS);
    }

}
