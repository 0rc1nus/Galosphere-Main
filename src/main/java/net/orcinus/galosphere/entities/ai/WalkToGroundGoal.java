package net.orcinus.galosphere.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.orcinus.galosphere.entities.SparkleEntity;

import java.util.EnumSet;

public class WalkToGroundGoal extends Goal {
    private final SparkleEntity creature;
    private final Level world;
    private BlockPos landPos;

    public WalkToGroundGoal(SparkleEntity creature) {
        this.creature = creature;
        this.world = creature.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.creature.level.getFluidState(this.creature.blockPosition()).is(FluidTags.WATER) && (this.creature.getTarget() != null || this.creature.getRandom().nextInt(30) == 0)){
            if(this.creature.shouldLeaveWater()) {
                this.landPos = generateTarget();
                return landPos != null;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        if (this.landPos != null) {
            this.creature.getNavigation().createPath(this.landPos.getX(), this.landPos.getY(), this.landPos.getZ(), 0);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.creature.shouldLeaveWater()) {
            this.creature.getNavigation().stop();
            return false;
        }
        return !this.creature.getNavigation().isDone() && landPos != null && !this.creature.level.getFluidState(landPos).is(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        BlockPos resultPos = null;
        BlockPos blockPos = this.creature.blockPosition();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        CollisionContext collisionContext = CollisionContext.of(this.creature);
        for (BlockPos pos : BlockPos.withinManhattan(blockPos, 8, 8, 8)) {
            if (pos.getX() == blockPos.getX() && pos.getZ() == blockPos.getZ()) continue;
            BlockState blockState = world.getBlockState(pos);
            BlockState blockState2 = world.getBlockState(mutableBlockPos.setWithOffset(pos, Direction.DOWN));
            if (blockState.is(Blocks.WATER) || !world.getFluidState(pos).isEmpty() || !blockState.getCollisionShape(world, pos, collisionContext).isEmpty() || !blockState2.isFaceSturdy(world, mutableBlockPos, Direction.UP)) continue;
            resultPos = pos.immutable();
        }
        return resultPos;
    }
}
