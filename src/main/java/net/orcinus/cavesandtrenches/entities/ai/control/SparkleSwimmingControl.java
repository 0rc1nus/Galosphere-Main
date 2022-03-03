package net.orcinus.cavesandtrenches.entities.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class SparkleSwimmingControl extends MoveControl {
    private final int maxTurnX;
    private final int maxTurnY;
    private final float inWaterSpeedModifier;
    private final float outsideWaterSpeedModifier;
    private final boolean applyGravity;

    public SparkleSwimmingControl(Mob mob) {
        super(mob);
        this.maxTurnX = 85;
        this.maxTurnY = 10;
        this.inWaterSpeedModifier = 0.1F;
        this.outsideWaterSpeedModifier = 0.5F;
        this.applyGravity = false;
    }

    @Override
    public void tick() {
        if (this.applyGravity && this.mob.isInWater()) {
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
        }

        if (this.operation == MoveControl.Operation.MOVE_TO && !this.mob.getNavigation().isDone()) {
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedY - this.mob.getY();
            double d2 = this.wantedZ - this.mob.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < (double)2.5000003E-7F) {
                this.mob.setZza(0.0F);
            } else {
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, (float)this.maxTurnY));
                this.mob.yBodyRot = this.mob.getYRot();
                this.mob.yHeadRot = this.mob.getYRot();
                float f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.mob.isInWater()) {
                    this.mob.setSpeed(f1 * this.inWaterSpeedModifier);
                    double d4 = Math.sqrt(d0 * d0 + d2 * d2);
                    if (Math.abs(d1) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
                        float f2 = -((float)(Mth.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), (float)(-this.maxTurnX), (float)this.maxTurnX);
                        this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, 5.0F));
                    }

                    float f4 = Mth.cos(this.mob.getXRot() * ((float)Math.PI / 180F));
                    float f3 = Mth.sin(this.mob.getXRot() * ((float)Math.PI / 180F));
                    this.mob.zza = f4 * f1;
                    this.mob.yya = -f3 * f1;
                } else {
                    super.tick();
                }

            }
        } else {
            this.mob.setSpeed(0.0F);
            this.mob.setXxa(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }
}