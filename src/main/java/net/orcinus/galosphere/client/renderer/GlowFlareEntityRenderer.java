package net.orcinus.galosphere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.entities.GlowFlareEntity;
import net.orcinus.galosphere.init.GItems;

@Environment(EnvType.CLIENT)
public class GlowFlareEntityRenderer extends EntityRenderer<GlowFlareEntity> {
    private final ItemRenderer itemRenderer;

    public GlowFlareEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(GlowFlareEntity entity, float p_114657_, float p_114658_, PoseStack poseStack, MultiBufferSource source, int p_114661_) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        if (entity.isShotAtAngle()) {
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        }
        this.itemRenderer.renderStatic(new ItemStack(GItems.GLOW_FLARE), ItemTransforms.TransformType.GROUND, 15728880, OverlayTexture.NO_OVERLAY, poseStack, source, entity.getId());
        poseStack.popPose();
        super.render(entity, p_114657_, p_114658_, poseStack, source, p_114661_);
    }

    @Override
    public ResourceLocation getTextureLocation(GlowFlareEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
