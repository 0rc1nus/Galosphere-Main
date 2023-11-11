package net.orcinus.galosphere.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


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
import net.orcinus.galosphere.client.animations.BerserkerAnimations;
import net.orcinus.galosphere.entities.Berserker;

@Environment(EnvType.CLIENT)
public class BerserkerModel<T extends Berserker> extends HierarchicalModel<T> {
    private final ModelPart root;

    public BerserkerModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(62, 0).addBox(-4.0F, -2.5F, -2.0F, 8.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -12.5F, 11.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(58, 62).addBox(-4.0F, -2.5F, -2.0F, 8.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -12.5F, 11.0F));

        PartDefinition rightArm = root.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 36).addBox(-4.0F, -3.5F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 75).addBox(-4.0F, -5.5F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, -20.5F, -8.0F));

        PartDefinition leftArm = root.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(32, 36).addBox(-4.0F, -3.5F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(78, 75).addBox(-4.0F, -5.5F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -20.5F, -8.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -5.75F, -18.5F, 20.0F, 14.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.25F, 9.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition firstSpike = body.addOrReplaceChild("firstSpike", CubeListBuilder.create().texOffs(64, 36).addBox(-3.0F, -17.0F, -3.5F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, -13.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition secondSpike = body.addOrReplaceChild("secondSpike", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -15.0F, -1.5F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, -6.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition thirdSpike = body.addOrReplaceChild("thirdSpike", CubeListBuilder.create().texOffs(90, 0).addBox(-1.0F, -12.0F, -1.5F, 2.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, 0.5F, -0.3491F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 68).addBox(-4.0F, 3.0F, -6.0F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, -9.0F));

        return LayerDefinition.create(meshdefinition, 112, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        ModelPart head = this.root.getChild("head");
        head.xRot = headPitch * ((float)Math.PI / 180);
        head.yRot = netHeadYaw * ((float)Math.PI / 180);
        this.animateWalk(BerserkerAnimations.BERSERKER_WALK, limbSwing, limbSwingAmount, 3.0F, 4.5F);
        this.animate(entity.attackAnimationState, BerserkerAnimations.BERSERKER_ATTACK, ageInTicks, 1.0F);
        this.animate(entity.impalingAnimationState, BerserkerAnimations.BERSERKER_IMPALING, ageInTicks, 1.0F);
        this.animate(entity.roarAnimationState, BerserkerAnimations.BERSERKER_ROAR, ageInTicks, 1.0F);
        this.animate(entity.punchAnimationState, BerserkerAnimations.BERSERKER_PUNCH, ageInTicks, 1.0F);
        this.animate(entity.summoningAnimationState, BerserkerAnimations.BERSERKER_SPAWN_MINIONS, ageInTicks, 1.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}