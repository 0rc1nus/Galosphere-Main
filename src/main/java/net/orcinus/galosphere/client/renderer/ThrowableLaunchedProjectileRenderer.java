package net.orcinus.galosphere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.entities.ThrowableLaunchedProjectile;

@OnlyIn(Dist.CLIENT)
public class ThrowableLaunchedProjectileRenderer<T extends ThrowableLaunchedProjectile> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public ThrowableLaunchedProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(T entity, float f, float g, PoseStack poseStack, MultiBufferSource source, int i) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        if (entity.isShotAtAngle()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        }
        this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, 15728880, OverlayTexture.NO_OVERLAY, poseStack, source, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, f, g, poseStack, source, i);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}