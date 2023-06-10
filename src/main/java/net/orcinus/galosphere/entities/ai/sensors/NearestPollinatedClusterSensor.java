package net.orcinus.galosphere.entities.ai.sensors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class NearestPollinatedClusterSensor extends Sensor<Sparkle> {

    @Override
    protected void doTick(ServerLevel world, Sparkle entity) {
        List<BlockPos> poses = Lists.newArrayList();
        int range = 8;
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                for (int y = -range; y <= range; y++) {
                    BlockPos pos = new BlockPos(entity.getX() + x, entity.getY() + y, entity.getZ() + z);
                    BlockState state = world.getBlockState(pos);
                    if (state.is(Blocks.AMETHYST_CLUSTER) || state.getBlock() instanceof PollinatedClusterBlock && !state.getValue(PollinatedClusterBlock.POLLINATED)) {
                        poses.add(pos);
                    }
                }
            }
        }
        if (!poses.isEmpty()) {
            poses.sort(Comparator.comparingDouble(entity.blockPosition()::distSqr));
            for (BlockPos pos : poses) {
                if (!entity.getBrain().hasMemoryValue(GMemoryModuleTypes.NEAREST_POLLINATED_CLUSTER.get())) {
                    entity.getBrain().setMemory(GMemoryModuleTypes.NEAREST_POLLINATED_CLUSTER.get(), pos);
                }
            }
        } else {
            entity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_POLLINATED_CLUSTER.get());
        }
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(GMemoryModuleTypes.NEAREST_POLLINATED_CLUSTER.get());
    }

}