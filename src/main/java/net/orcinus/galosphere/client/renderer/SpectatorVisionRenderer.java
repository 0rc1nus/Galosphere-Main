package net.orcinus.galosphere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.SpectatorVision;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class SpectatorVisionRenderer extends EntityRenderer<SpectatorVision> {
    private static final Function<Integer, ResourceLocation> FUNCTION = integer -> new ResourceLocation(Galosphere.MODID, "textures/entity/spectre_vision/spectre_vision_" + integer + ".png");

    public SpectatorVisionRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SpectatorVision entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.scale(1.5F, 1.5F, 1.5F);
        float sin = Mth.sin(entity.tickCount / 4.0F) / 16.0F;
        poseStack.translate(0, sin, 0);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        SpectatorVisionRenderer.vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0f, 0, 0, 1);
        SpectatorVisionRenderer.vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0f, 0, 1, 1);
        SpectatorVisionRenderer.vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0f, 1, 1, 0);
        SpectatorVisionRenderer.vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0f, 1, 0, 0);
        poseStack.popPose();
    }

    @Override
    protected int getBlockLightLevel(SpectatorVision entity, BlockPos blockPos) {
        return entity.getPhase();
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float f, int j, int k, int l) {
        vertexConsumer.vertex(matrix4f, f - 0.5f, (float)j - 0.5f, 0.0f).color(255, 255, 255, 255).uv(k, l).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(SpectatorVision entity) {
        return FUNCTION.apply(entity.getPhase() + 1);
    }
}
