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

    public SpectreModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition leftWing = head.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -7.0F, -3.5F, 0.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 1.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition rightWing = head.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -7.0F, -3.5F, 0.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -1.0F, 1.5F, -0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5F;
        float degree = 1.5f;
        this.head.y = Mth.cos(ageInTicks * speed * 0.5F + Mth.PI / 2) * 1.5F * 0.25F + 22.0F;
        this.head.getChild("rightWing").zRot = Mth.cos(ageInTicks * speed * 0.5F) * degree * 2F * 0.25F - 1F;
        this.head.getChild("leftWing").zRot = -this.head.getChild("rightWing").zRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}