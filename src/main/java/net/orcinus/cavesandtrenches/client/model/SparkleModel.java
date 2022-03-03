package net.orcinus.cavesandtrenches.client.model;

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
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.cavesandtrenches.entities.SparkleEntity;

import java.util.Locale;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class SparkleModel<T extends SparkleEntity> extends AgeableListModel<T> {
	private final ModelPart head;
	private final ModelPart left_hand;
	private final ModelPart left_leg;
	private final ModelPart tail;
	private final ModelPart right_leg;
	private final ModelPart right_hand;
	private final ModelPart body;

	public SparkleModel(ModelPart root) {
		this.head = root.getChild("head");
		this.left_hand = root.getChild("left_hand");
		this.left_leg = root.getChild("left_leg");
		this.tail = root.getChild("tail");
		this.right_leg = root.getChild("right_leg");
		this.right_hand = root.getChild("right_hand");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 40).addBox(-4.0F, -2.8503F, -7.7119F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.8503F, -11.2881F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(35, 40).addBox(-4.0F, -0.2871F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5632F, 0.9885F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(19, 40).addBox(-4.0F, 2.1984F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.9513F, 0.1141F, -1.3526F, 0.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.7351F, -4.6008F, -1.5708F, 0.0F, 0.0F));

		PartDefinition left_hand = partdefinition.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(4.9837F, 20.4669F, -7.5F));

		PartDefinition cube_r4 = left_hand.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 6).addBox(-4.0F, 3.0F, -11.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0163F, 0.5331F, 10.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(4.9837F, 20.4669F, 2.5F));

		PartDefinition cube_r5 = left_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 9).addBox(-4.0F, 3.0F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0163F, 0.5331F, 0.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 18.5F, 7.5F));

		PartDefinition cube_r6 = tail.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(23, 0).addBox(-9.0F, 0.0F, -9.5F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r7 = tail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 24).addBox(-8.0F, 0.0F, -9.5F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-4.7841F, 20.5347F, 2.5F));

		PartDefinition cube_r8 = right_leg.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-16.75F, -9.0F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.7841F, 0.4653F, 0.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition right_hand = partdefinition.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-4.7841F, 20.5347F, -7.5F));

		PartDefinition cube_r9 = right_hand.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 3).addBox(-16.75F, -9.0F, -11.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.7841F, 0.4653F, 10.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -7.0F, 10.0F, 5.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.tail.visible = entity.getCrystaltype() != SparkleEntity.CrystalType.NONE;
		if (entity.isInWaterOrBubble()) {
			this.body.yRot = Mth.cos(ageInTicks / 2.0F) / 8.0F;
			this.tail.yRot = Mth.sin(ageInTicks / 2.0F) / 1.2F;
		}
		else {
			this.body.yRot = 0.0F;
			this.left_leg.xRot = Mth.sin(limbSwing) * limbSwingAmount;
			this.left_leg.yRot = Mth.cos(limbSwing) * limbSwingAmount * 2.0F;
			this.right_leg.xRot = -Mth.sin(limbSwing * 1.5F) * limbSwingAmount;
			this.right_leg.yRot = Mth.cos(limbSwing * 1.5F) * limbSwingAmount * 2.0F;
			this.left_hand.xRot = -Mth.sin(limbSwing * 1.5F) * limbSwingAmount;
			this.left_hand.yRot = Mth.cos(limbSwing * 1.5F) * limbSwingAmount * 2.0F;
			this.right_hand.xRot = Mth.sin(limbSwing) * limbSwingAmount;
			this.right_hand.yRot = Mth.cos(limbSwing) * limbSwingAmount * 2.0F;
			this.tail.yRot = Mth.cos(limbSwing * 1.0F * 2.0F) * limbSwingAmount;
			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
		}
	}

	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer consumer, int packedlight, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
		if (this.young) {
			matrix.pushPose();
			matrix.translate(0.0D, (5.0F / 20.0F), (2.0F / 5.0F));
			this.headParts().forEach((model) -> {
				model.render(matrix, consumer, packedlight, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
			matrix.popPose();
			matrix.pushPose();
			float f1 = 1.0F / 2.0F;
			matrix.scale(f1, f1, f1);
			matrix.translate(0.0D, (24.0F / 16.0F), 0.0D);
			this.bodyParts().forEach((p_102071_) -> {
				p_102071_.render(matrix, consumer, packedlight, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
			matrix.popPose();
		} else {
			this.headParts().forEach((p_102061_) -> {
				p_102061_.render(matrix, consumer, packedlight, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
			this.bodyParts().forEach((p_102051_) -> {
				p_102051_.render(matrix, consumer, packedlight, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
		}
	}

	//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		this.head.render(poseStack, buffer, packedLight, packedOverlay);
//		this.left_hand.render(poseStack, buffer, packedLight, packedOverlay);
//		this.left_leg.render(poseStack, buffer, packedLight, packedOverlay);
//		this.tail.render(poseStack, buffer, packedLight, packedOverlay);
//		this.right_leg.render(poseStack, buffer, packedLight, packedOverlay);
//		this.right_hand.render(poseStack, buffer, packedLight, packedOverlay);
//		this.body.render(poseStack, buffer, packedLight, packedOverlay);
//	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.left_hand, this.left_leg, this.right_hand, this.right_leg, this.tail);
	}
}