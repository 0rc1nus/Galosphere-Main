package net.orcinus.galosphere.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.FayEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FlyWanderGoal extends Goal {
    private final FayEntity lubricEntity;

    public FlyWanderGoal(FayEntity lubricEntity) {
        this.lubricEntity = lubricEntity;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.lubricEntity.getNavigation().isDone() && this.lubricEntity.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.lubricEntity.getNavigation().isInProgress();
    }

    @Override
    public void start() {
        Vec3 vec3 = this.findPos();
        if (vec3 != null) {
            this.lubricEntity.getNavigation().moveTo(this.lubricEntity.getNavigation().createPath(new BlockPos(vec3), 1), 1.0);
        }
    }

    @Nullable
    private Vec3 findPos() {
        Vec3 vec3 = this.lubricEntity.getViewVector(0.0f);
        return AirAndWaterRandomPos.getPos(this.lubricEntity, 10, 7, -2, vec3.x, vec3.z, 1.5707963705062866);
    }
}
