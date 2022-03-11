package net.orcinus.cavesandtrenches.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SparkleRandomSwimmingGoal extends RandomStrollGoal {

    public SparkleRandomSwimmingGoal(PathfinderMob mob, double speed, int chance) {
        super(mob, speed, chance, false);
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle()) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if (this.mob.getNoActionTime() >= 100) {
                    return false;
                }
                if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }

            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (this.mob.hasRestriction() && this.mob.distanceToSqr(Vec3.atCenterOf(this.mob.getRestrictCenter())) > this.mob.getRestrictRadius() * this.mob.getRestrictRadius()) {
            return DefaultRandomPos.getPosTowards(this.mob, 7, 3, Vec3.atBottomCenterOf(this.mob.getRestrictCenter()), 1);
        }
        if (this.mob.getRandom().nextFloat() < 0.3F) {
            Vec3 vector3d = findSurfaceTarget(this.mob);
            if (vector3d != null){
                return vector3d;
            }
        }

        return DefaultRandomPos.getPos(this.mob, 7, 3);
    }

    private boolean waterIsClear(BlockPos pos, int dx, int dz, int scale) {
        return this.mob.level.getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir() && this.mob.level.getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
    }

    private boolean surffaceClear(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
        return this.mob.level.getFluidState(blockpos).is(FluidTags.LAVA) || this.mob.level.getFluidState(blockpos).is(FluidTags.WATER) && !this.mob.level.getBlockState(blockpos).getMaterial().blocksMotion();
    }

    public Vec3 findSurfaceTarget(PathfinderMob sparkleEntity) {
        BlockPos abovePos = sparkleEntity.blockPosition();
        while (sparkleEntity.level.getFluidState(abovePos).is(FluidTags.WATER) || sparkleEntity.level.getFluidState(abovePos).is(FluidTags.LAVA)){
            abovePos = abovePos.above();
        }
        if(waterIsClear(abovePos.below(), 0, 0, 0) && surffaceClear(abovePos.below(), 0, 0, 0)){
            return new Vec3(abovePos.getX() + 0.5F, abovePos.getY() - 1F, abovePos.getZ() + 0.5F);
        }
        return null;
    }
}
