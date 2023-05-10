package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

public class FindValidLandingPosition extends Behavior<Spectre> {
    private final Block block;

    public FindValidLandingPosition(Block block) {
        super(ImmutableMap.of());
        this.block = block;
    }

    @Override
    protected void start(ServerLevel serverLevel, Spectre livingEntity, long l) {
        CollisionContext collisionContext = CollisionContext.of(livingEntity);
        BlockPos blockPos = livingEntity.blockPosition();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (BlockPos blockPos2 : BlockPos.withinManhattan(blockPos, 8, 8, 8)) {
            if (blockPos2.getX() == blockPos.getX() && blockPos2.getZ() == blockPos.getZ() || !serverLevel.getBlockState(blockPos2).getCollisionShape(serverLevel, blockPos2, collisionContext).isEmpty() || serverLevel.getBlockState(mutableBlockPos.setWithOffset((Vec3i)blockPos2, Direction.DOWN)).getCollisionShape(serverLevel, blockPos2, collisionContext).isEmpty()) continue;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                mutableBlockPos.setWithOffset(blockPos2, direction);
                boolean flag = serverLevel.getBlockState(mutableBlockPos).isAir() && serverLevel.getBlockState(mutableBlockPos.move(Direction.DOWN)).is(this.block);
                if (!flag) continue;
                livingEntity.getBrain().setMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS, blockPos2);
                return;
            }
        }
    }

}
