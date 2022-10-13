package net.orcinus.galosphere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GModelLayers;

public class SterlingArmorRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/sterling_helmet.png");
    private final SterlingArmorModel<T> model;

    public SterlingArmorRenderer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        this.model = new SterlingArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(GModelLayers.STERLING_HELMET));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (stack.is(GItems.STERLING_HELMET)) {
            poseStack.pushPose();
            ((HeadedModel) this.getParentModel()).getHead().translateAndRotate(poseStack);
            poseStack.translate(0.0F, -0.1F, 0.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(TEXTURE), false, stack.hasFoil());
            this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
