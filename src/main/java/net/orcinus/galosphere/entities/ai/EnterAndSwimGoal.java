package net.orcinus.galosphere.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.orcinus.galosphere.entities.SparkleEntity;

import java.util.EnumSet;
import java.util.Random;

public class EnterAndSwimGoal extends Goal {
    private final SparkleEntity sparkle;
    private BlockPos waterPos;

    public EnterAndSwimGoal(SparkleEntity sparkle) {
        this.sparkle = sparkle;
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
            this.sparkle.getNavigation().moveTo(swimPos.getX(), swimPos.getY(), swimPos.getZ(), 1.2D);
        }
    }

    public BlockPos getWaterPos() {
        BlockPos blockpos = null;
        Random random = this.sparkle.getRandom();
        int range = 10;
        for(int i = 0; i < 15; i++) {
            BlockPos blockpos1 = this.sparkle.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
            while (this.sparkle.level.isEmptyBlock(blockpos1) && blockpos1.getY() > 1){
                blockpos1 = blockpos1.below();
            }
            if (this.sparkle.level.getFluidState(blockpos1).is(FluidTags.WATER)){
                blockpos = blockpos1;
            }
        }
        return blockpos;
    }
}
