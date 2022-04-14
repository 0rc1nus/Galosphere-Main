package net.orcinus.galosphere.entities.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.orcinus.galosphere.entities.SparkleEntity;

public class SmoothSwimmingGroundControl extends MoveControl {
    private final SparkleEntity entity;
    private final float speedMultiplier;
    private final float ySpeedModifier;

    public SmoothSwimmingGroundControl(SparkleEntity entity, float speedMultiplier, float ySpeedModifier) {
        super(entity);
        this.entity = entity;
        this.speedMultiplier = speedMultiplier;
        this.ySpeedModifier = ySpeedModifier;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO && !this.entity.getNavigation().isDone()) {
            double distanceX = this.wantedX - this.entity.getX();
            double distanceY = this.wantedY - this.entity.getY();
            double distanceZ = this.wantedZ - this.entity.getZ();
            double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
            if (distance < (double)2.5000003E-7F) {
                this.mob.setZza(0.0F);
            } else {
                float delta = (float) (Mth.atan2(distanceZ, distanceX) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.entity.setYRot(this.rotlerp(this.entity.getYRot(), delta, 10.0F));
                this.entity.yBodyRot = this.entity.getYRot();
                this.entity.yHeadRot = this.entity.getYRot();
                float speed = (float) (this.speedModifier * speedMultiplier * 3 * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.entity.isInWater()) {
                    if(distanceY > 0 && entity.horizontalCollision){
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.08F, 0.0D));
                    } else {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, (double) this.entity.getSpeed() * distanceY * 0.6D * ySpeedModifier, 0.0D));
                    }
                    this.entity.setSpeed(speed * 0.02F);
                    float f2 = -((float) (Mth.atan2(distanceY, Mth.sqrt((float) (distanceX * distanceX + distanceZ * distanceZ))) * 57.2957763671875D));
                    f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                    this.entity.setXRot(this.rotlerp(this.entity.getXRot(), f2, 5.0F));
                    float f4 = Mth.cos(this.entity.getXRot() * 0.017453292F);
                    float f3 = Mth.sin(this.entity.getXRot() * 0.017453292F);
                    this.entity.zza = f4 * speed;
                    this.entity.yya = -f3 * speed;
                } else {
                    this.entity.setSpeed(speed * 0.1F);
                }

            }
        } else {
            this.entity.setSpeed(0.0F);
            this.entity.setXxa(0.0F);
            this.entity.setYya(0.0F);
            this.entity.setZza(0.0F);
        }
    }
}
