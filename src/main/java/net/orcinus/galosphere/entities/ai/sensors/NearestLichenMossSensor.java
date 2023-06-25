package net.orcinus.galosphere.entities.ai.sensors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class NearestLichenMossSensor extends Sensor<Specterpillar> {

    @Override
    protected void doTick(ServerLevel world, Specterpillar entity) {
        List<BlockPos> poses = Lists.newArrayList();
        int range = 8;
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                for (int y = -range; y <= range; y++) {
                    BlockPos pos = BlockPos.containing(entity.getX() + x, entity.getY() + y, entity.getZ() + z);
                    BlockState state = world.getBlockState(pos);
                    BlockState belowState = world.getBlockState(pos.below());
                    if (state.isAir() && belowState.is(GBlocks.LICHEN_MOSS.get())) {
                        poses.add(pos);
                    }
                }
            }
        }
        if (!poses.isEmpty()) {
            poses.sort(Comparator.comparingDouble(entity.blockPosition()::distSqr));
            for (BlockPos pos : poses) {
                Path path = entity.getNavigation().createPath(pos, 0);
                if (!entity.getBrain().hasMemoryValue(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get()) && path != null && path.canReach()) {
                    entity.getBrain().setMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get(), pos);
                }
            }
        } else {
            entity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get());
        }
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(GMemoryModuleTypes.CAN_BURY.get());
    }

}
