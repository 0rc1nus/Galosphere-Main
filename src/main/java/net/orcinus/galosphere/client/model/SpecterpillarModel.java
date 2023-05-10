package net.orcinus.galosphere.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.orcinus.galosphere.client.animations.SpecterpillarAnimations;
import net.orcinus.galosphere.entities.Specterpillar;

@Environment(EnvType.CLIENT)
public class SpecterpillarModel<T extends Specterpillar> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body;

    public SpecterpillarModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -4.5F, 5.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(T entity, float swing, float swingAmount, float age, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.burrowAnimationState, SpecterpillarAnimations.SPECTERPILLAR_BURROW, age);
        body.zScale = 1 + (Mth.sin(age * (0.2F + (swing / 600))) * (0.07F + (swingAmount / 3)));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}