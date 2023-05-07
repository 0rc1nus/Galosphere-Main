package net.orcinus.galosphere.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
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
import net.orcinus.galosphere.entities.SpectreEntity;

@OnlyIn(Dist.CLIENT)
public class SpectreModel<T extends SpectreEntity> extends EntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;

    public SpectreModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 1.0F));
        PartDefinition leftAntenna = head.addOrReplaceChild("leftAntenna", CubeListBuilder.create().texOffs(6, 10).addBox(0.0F, -1.5F, -3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.5F, -3.0F));
        PartDefinition rightAntenna = head.addOrReplaceChild("rightAntenna", CubeListBuilder.create().texOffs(6, 10).mirror().addBox(0.0F, -1.5F, -3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -3.5F, -3.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, 0.5F));
        PartDefinition topLegs = body.addOrReplaceChild("topLegs", CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -1.5F, -0.3927F, 0.0F, 0.0F));
        PartDefinition bottomLegs = body.addOrReplaceChild("bottomLegs", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -1.5F, -0.3927F, 0.0F, 0.0F));
        PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(14, 8).addBox(0.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.0F, 0.5F, 0.0F, -1.0472F, 0.0F));
        PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(14, 8).mirror().addBox(-9.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.5F, 0.0F, 1.0472F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5F;
        float degree = 1.5f;
        float tilt = Math.min(limbSwingAmount / 0.3f, 1.0f);

        this.body.xRot = tilt * 0.5F;
        this.body.xRot += (Mth.cos(ageInTicks * 0.2F) * 0.15F);
        this.head.getChild("leftAntenna").xRot = -(Mth.cos(ageInTicks * 0.2F) * 0.25F);
        this.head.getChild("rightAntenna").xRot = -(Mth.sin(ageInTicks * 0.2F) * 0.25F);
        this.body.getChild("rightWing").yRot = -(Mth.cos(ageInTicks * speed * 0.8F) * degree * 2F * 0.25F - 1F);
        this.body.getChild("leftWing").yRot = -this.body.getChild("rightWing").yRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}