package net.orcinus.galosphere.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.entities.SparkleEntity;

@OnlyIn(Dist.CLIENT)
public class SparkleModel<T extends SparkleEntity> extends AgeableListModel<T> {
	private final ModelPart body;

	public SparkleModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -4.0F, 5.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, -1.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -2.0F, -6.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(21, 5).addBox(1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(21, 5).mirror().addBox(-3.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -2.0F, -4.0F));

		PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -3.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition leftWhisker = head.addOrReplaceChild("leftWhisker", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.5F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.5F, -6.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition rightWhisker = head.addOrReplaceChild("rightWhisker", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -0.5F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 0.5F, -6.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(-3, 5).addBox(0.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.0F, -2.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(-3, 5).mirror().addBox(-5.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 1.0F, -2.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(-3, 5).addBox(0.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.0F, 5.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(-3, 5).mirror().addBox(-5.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 1.0F, 5.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 2.0F));

		PartDefinition crystal1 = tail.addOrReplaceChild("crystal1", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 5.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition crystal2 = tail.addOrReplaceChild("crystal2", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 5.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 48, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		boolean young1 = this.young;
		if (young1) {
			poseStack.pushPose();
			float f1 = 1.0F / 2.0F;
			poseStack.scale(f1, f1, f1);
			poseStack.translate(0.0D, 1.5F, 5.0F / 16.0F);
			this.headParts().forEach((part) -> {
				part.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			poseStack.popPose();
		} else {
			this.headParts().forEach((part) -> {
				part.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			});
		}
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.body);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of();
	}
}