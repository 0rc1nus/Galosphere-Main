package net.orcinus.galosphere.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.orcinus.galosphere.entities.SparkleEntity;

import java.util.EnumSet;

public class WalkAndSwimGoal extends Goal {
    private final SparkleEntity sparkle;
    private final Level world;
    private BlockPos waterPos;

    public WalkAndSwimGoal(SparkleEntity sparkle) {
        this.sparkle = sparkle;
        this.world = sparkle.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.sparkle.isOnGround() && !this.sparkle.level.getFluidState(this.sparkle.blockPosition()).is(FluidTags.WATER)){
            if (this.sparkle.shouldEnterWater() && this.sparkle.getRandom().nextInt(30) == 0) {
                this.waterPos = this.getWaterPos();
                return this.waterPos != null;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.sparkle.shouldEnterWater() && !this.sparkle.getNavigation().isDone() && this.waterPos != null && !this.sparkle.level.getFluidState(this.sparkle.blockPosition()).is(FluidTags.WATER);
    }

    @Override
    public void start() {
        BlockPos swimPos = this.waterPos;
        if (swimPos != null) {
            this.sparkle.getNavigation().createPath(swimPos.getX(), swimPos.getY(), swimPos.getZ(), 0);
        }
    }

    public BlockPos getWaterPos() {
        BlockPos blockpos = null;
        BlockPos blockPos = this.sparkle.blockPosition();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        CollisionContext shapeContext = CollisionContext.of(this.sparkle);
        for (BlockPos boundPos : BlockPos.withinManhattan(blockPos, 8, 8, 8)) {
            if (boundPos.getX() == blockPos.getX() && boundPos.getZ() == blockPos.getZ() || !world.getBlockState(boundPos).getCollisionShape(world, boundPos, shapeContext).isEmpty() || world.getBlockState(mutable.setWithOffset(boundPos, Direction.DOWN)).getCollisionShape(world, boundPos, shapeContext).isEmpty()) continue;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                mutable.setWithOffset(boundPos, direction);
                if (world.getBlockState(mutable).isAir() || world.getBlockState(mutable.move(Direction.DOWN)).is(Blocks.WATER)) {
                    blockpos = mutable;
                }
            }
        }
        return blockpos;
    }
}
