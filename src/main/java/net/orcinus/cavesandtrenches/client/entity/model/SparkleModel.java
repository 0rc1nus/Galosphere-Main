package net.orcinus.cavesandtrenches.client.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;

@OnlyIn(Dist.CLIENT)
public class SparkleModel<T extends SparkleEntity> extends AgeableListModel<T> {

    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart snout;
    private final ModelPart spines_top1;
    private final ModelPart spines_top2;
    private final ModelPart spines_top3;
    private final ModelPart spines_right1;
    private final ModelPart spines_right2;
    private final ModelPart spines_right3;
    private final ModelPart spines_left1;
    private final ModelPart spines_left2;
    private final ModelPart spines_left3;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart right_foot;
    private final ModelPart left_foot;
    private final ModelPart left_hand;
    private final ModelPart right_hand;

    public SparkleModel(ModelPart root) {
        this.root = root;

        this.body = root.getChild("body");
        this.right_foot = root.getChild("right_foot");
        this.left_foot = root.getChild("left_foot");
        this.left_hand = root.getChild("left_hand");
        this.right_hand = root.getChild("right_hand");

        this.snout = body.getChild("snout");
        this.spines_top1 = body.getChild("spines_top1");
        this.spines_top2 = body.getChild("spines_top2");
        this.spines_top3 = body.getChild("spines_top3");
        this.spines_right1 = body.getChild("spines_right1");
        this.spines_right2 = body.getChild("spines_right2");
        this.spines_right3 = body.getChild("spines_right3");
        this.spines_left1 = body.getChild("spines_left1");
        this.spines_left2 = body.getChild("spines_left2");
        this.spines_left3 = body.getChild("spines_left3");
        this.left_ear = body.getChild("left_ear");
        this.right_ear = body.getChild("right_ear");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 1)
                        .mirror(false)
                        .addBox(-3.0F, -3.0F, -4.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 20.0F, -1.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition snout = body.addOrReplaceChild(
                "snout",
                CubeListBuilder.create()
                        .texOffs(0, 3)
                        .mirror(false)
                        .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.0F, -4.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition spines_top1 = body.addOrReplaceChild(
                "spines_top1",
                CubeListBuilder.create()
                        .texOffs(21, 5)
                        .mirror(false)
                        .addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -1.0472F, 0.0F, 0.0F)
        );

        PartDefinition spines_top2 = body.addOrReplaceChild(
                "spines_top2",
                CubeListBuilder.create()
                        .texOffs(21, 8)
                        .mirror(false)
                        .addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -1.0472F, 0.0F, 0.0F)
        );

        PartDefinition spines_top3 = body.addOrReplaceChild(
                "spines_top3",
                CubeListBuilder.create()
                        .texOffs(21, 5)
                        .mirror(false)
                        .addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, -1.0472F, 0.0F, 0.0F)
        );

        PartDefinition spines_right1 = body.addOrReplaceChild(
                "spines_right1",
                CubeListBuilder.create()
                        .texOffs(22, 0)
                        .mirror(true)
                        .addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, -0.5F, -2.0F, 0.0F, 1.0472F, 0.0F)
        );

        PartDefinition spines_right2 = body.addOrReplaceChild(
                "spines_right2",
                CubeListBuilder.create()
                        .texOffs(26, 0)
                        .mirror(true)
                        .addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, -0.5F, 0.0F, 0.0F, 1.0472F, 0.0F)
        );

        PartDefinition spines_right3 = body.addOrReplaceChild(
                "spines_right3",
                CubeListBuilder.create()
                        .texOffs(22, 0)
                        .mirror(true)
                        .addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, -0.5F, 2.0F, 0.0F, 1.0472F, 0.0F)
        );

        PartDefinition spines_left1 = body.addOrReplaceChild(
                "spines_left1",
                CubeListBuilder.create()
                        .texOffs(22, 0)
                        .mirror(false)
                        .addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -0.5F, -2.0F, 0.0F, -1.0472F, 0.0F)
        );

        PartDefinition spines_left2 = body.addOrReplaceChild(
                "spines_left2",
                CubeListBuilder.create()
                        .texOffs(26, 0)
                        .mirror(false)
                        .addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -0.5F, 0.0F, 0.0F, -1.0472F, 0.0F)
        );

        PartDefinition spines_left3 = body.addOrReplaceChild(
                "spines_left3",
                CubeListBuilder.create()
                        .texOffs(22, 0)
                        .mirror(false)
                        .addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -0.5F, 2.0F, 0.0F, -1.0472F, 0.0F)
        );

        PartDefinition left_ear = body.addOrReplaceChild(
                "left_ear",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .mirror(true)
                        .addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, 0.5F, -2.5F, 0.0F, -0.7854F, 0.0F)
        );

        PartDefinition right_ear = body.addOrReplaceChild(
                "right_ear",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .mirror(false)
                        .addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, 0.5F, -2.5F, 0.0F, 0.7854F, 0.0F)
        );

        PartDefinition right_foot = root.addOrReplaceChild(
                "right_foot",
                CubeListBuilder.create()
                        .texOffs(0, 7)
                        .mirror(false)
                        .addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 23.0F, 1.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition left_foot = root.addOrReplaceChild(
                "left_foot",
                CubeListBuilder.create()
                        .texOffs(0, 7)
                        .mirror(false)
                        .addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, 23.0F, 1.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition left_hand = root.addOrReplaceChild(
                "left_hand",
                CubeListBuilder.create()
                        .texOffs(0, 7)
                        .mirror(false)
                        .addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.5F, 23.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition right_hand = root.addOrReplaceChild(
                "right_hand",
                CubeListBuilder.create()
                        .texOffs(0, 7)
                        .mirror(false)
                        .addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.5F, 23.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(data, 32, 16);
    }


    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 2.0f;
        float degree = 1.5f;
        if (!entity.isInWater()) {
            this.body.zRot = Mth.cos(limbAngle * speed * 0.2F) * degree * 0.3F * limbDistance;
            this.body.yRot = Mth.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.3F * limbDistance;
            this.body.y = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.75F * limbDistance + 20.025F;
            this.left_ear.yRot = Mth.cos(limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 0.8F;
            this.right_ear.yRot = Mth.cos(limbAngle * speed * 0.2F) * degree * -0.4F * limbDistance + 0.8F;
            this.snout.xRot = Mth.cos(animationProgress * speed * 0.6F) * degree * 0.2F * 0.25F;
            this.left_hand.yRot = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
            this.right_hand.yRot = Mth.cos(limbAngle * speed * 0.4F) * degree * -0.8F * limbDistance;
            this.left_hand.z = Mth.cos(-1.0F + limbAngle * speed * 0.4F) * degree * 0.5F * limbDistance - 2.0F;
            this.right_hand.z = Mth.cos(-1.0F + limbAngle * speed * 0.4F) * degree * -0.5F * limbDistance - 2.0F;
            this.left_foot.yRot = Mth.cos(limbAngle * speed * 0.4F) * degree * -0.8F * limbDistance;
            this.right_foot.yRot = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
            this.left_foot.z = Mth.cos(-1.0F + limbAngle * speed * 0.4F) * degree * -0.5F * limbDistance + 1.5F;
            this.right_foot.z = Mth.cos(-1.0F + limbAngle * speed * 0.4F) * degree * 0.5F * limbDistance + 1.5F;
            this.spines_top1.xRot = Mth.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 1.0F;
            this.spines_top2.xRot = Mth.cos(limbAngle * speed * 0.2F) * degree * -0.4F * limbDistance - 1.0F;
            this.spines_top3.xRot = Mth.cos(-4.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 1.0F;
            this.spines_right1.yRot = Mth.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance + 1.0F;
            this.spines_right2.yRot = Mth.cos(limbAngle * speed * 0.2F) * degree * -0.4F * limbDistance + 1.0F;
            this.spines_right3.yRot = Mth.cos(-4.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance + 1.0F;
            this.spines_left1.yRot = Mth.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 1.0F;
            this.spines_left2.yRot = Mth.cos(limbAngle * speed * 0.2F) * degree * -0.4F * limbDistance - 1.0F;
            this.spines_left3.yRot = Mth.cos(-4.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 1.0F;
            this.left_hand.y = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.5F * limbDistance + 23.0F;
            this.right_hand.y = Mth.cos(limbAngle * speed * 0.4F) * degree * -0.5F * limbDistance + 23.0F;
            this.right_foot.y = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.5F * limbDistance + 23.005F;
            this.left_foot.y = Mth.cos(-3.0F + limbAngle * speed * 0.4F) * degree * 0.5F * limbDistance + 23.005F;

            this.left_hand.xRot = 0F;
            this.right_hand.xRot = 0F;
            this.right_foot.xRot = 0F;
            this.left_foot.xRot = 0F;
        } else  {
            this.left_hand.xRot = 2.2F;
            this.right_hand.xRot = 2.2F;

            this.right_foot.xRot = Mth.cos(animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F + 0.8F;
            this.left_foot.xRot = Mth.cos(animationProgress * speed * 0.1F) * degree * -0.8F * 0.25F + 0.8F;
            this.snout.xRot = Mth.cos(animationProgress * speed * 0.6F) * degree * 0.2F * 0.25F;
            this.body.zRot = Mth.cos(animationProgress * speed * 0.05F) * degree * 0.3F * 0.25F;
            this.spines_top1.xRot = Mth.cos(-1.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F - 1.0F;
            this.spines_top2.xRot = Mth.cos(animationProgress * speed * 0.2F) * degree * -0.4F * 0.25F - 1.0F;
            this.spines_top3.xRot = Mth.cos(-4.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F - 1.0F;
            this.spines_right1.yRot = Mth.cos(-1.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F + 1.0F;
            this.spines_right2.yRot = Mth.cos(animationProgress * speed * 0.2F) * degree * -0.4F * 0.25F + 1.0F;
            this.spines_right3.yRot = Mth.cos(-4.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F + 1.0F;
            this.spines_left1.yRot = Mth.cos(-1.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F - 1.0F;
            this.spines_left2.yRot = Mth.cos(animationProgress * speed * 0.2F) * degree * -0.4F * 0.25F - 1.0F;
            this.spines_left3.yRot = Mth.cos(-4.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F - 1.0F;

            this.body.yRot = 0F;
            this.body.y =20.0F;
            this.left_hand.y = 23.0F;
            this.right_hand.y = 23.0F;
            this.right_foot.y = 23.0F;
            this.left_foot.y = 23.0F;
            this.left_hand.yRot = 0F;
            this.right_hand.yRot = 0F;
            this.left_hand.z = - 2.0F;
            this.right_hand.z = - 2.0F;
            this.left_foot.yRot = 0F;
            this.right_foot.yRot = 0F;
            this.left_foot.z = 1.5F;
            this.right_foot.z = 1.5F;
        }
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.left_hand, this.right_hand, this.left_foot, this.right_foot);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

}
