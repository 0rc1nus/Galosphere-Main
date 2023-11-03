package net.orcinus.galosphere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.PinkSaltPillarModel;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class PinkSaltPillarRenderer extends EntityRenderer<PinkSaltPillar> {
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/pink_salt_pillar/pink_salt_pillar.png");
    private final PinkSaltPillarModel<PinkSaltPillar> model;

    public PinkSaltPillarRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PinkSaltPillarModel<>(context.bakeLayer(GModelLayers.PINK_SALT_PILLAR));
    }

    @Override
    public void render(PinkSaltPillar entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (!entity.isActive()) {
            return;
        }
        float ticks = entity.tickCount + g;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0F, -1.0F, 0.0F);
        this.model.setupAnim(entity, 0.0F, 0.0f, ticks, entity.getYRot(), entity.getXRot());
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(PinkSaltPillar entity) {
        return TEXTURE;
    }
}
