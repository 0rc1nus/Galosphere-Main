package net.orcinus.galosphere.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.SaltBound;
import net.orcinus.galosphere.client.renderer.SterlingArmorRenderer;
import net.orcinus.galosphere.client.renderer.layer.BannerLayer;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.util.SaltLayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    @Shadow protected abstract void setModelProperties(AbstractClientPlayer abstractClientPlayer);

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void G$init(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        this.addLayer(new BannerLayer<>(this));
        this.addLayer(new SterlingArmorRenderer<>(this));
    }

    @Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void G$getTextureLocation(AbstractClientPlayer abstractClientPlayer, CallbackInfoReturnable<ResourceLocation> cir) {
        SaltBound saltBound = (SaltBound) abstractClientPlayer;
        SaltLayer saltLayers = SaltLayer.byId(saltBound.getSaltLayers());
        if (saltBound.getSaltLayers() > 0) {
            cir.setReturnValue(getSaltResourceLocation(saltLayers));
        }
    }

    @Unique
    private static ResourceLocation getSaltResourceLocation(SaltLayer saltLayers) {
        return new ResourceLocation(Galosphere.MODID, "textures/entity/player/" + saltLayers.getName() + "_salt_layer.png");
    }

    @Inject(at = @At("HEAD"), method = "renderHand", cancellable = true)
    private void G$renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer abstractClientPlayer, ModelPart modelPart, ModelPart modelPart2, CallbackInfo ci) {
        SaltBound saltBound = (SaltBound) abstractClientPlayer;
        SaltLayer saltLayers = SaltLayer.byId(saltBound.getSaltLayers());
        boolean hasSaltLayers = saltBound.getSaltLayers() > 0;
        ResourceLocation resourceLocation = hasSaltLayers ? getSaltResourceLocation(saltLayers) : abstractClientPlayer.getSkinTextureLocation();
        if (hasSaltLayers || abstractClientPlayer.hasEffect(GMobEffects.ASTRAL)) {
            PlayerModel<AbstractClientPlayer> playerModel = this.getModel();
            this.setModelProperties(abstractClientPlayer);
            playerModel.attackTime = 0.0f;
            playerModel.crouching = false;
            playerModel.swimAmount = 0.0f;
            playerModel.setupAnim(abstractClientPlayer, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            modelPart.xRot = 0.0f;
            float alpha = abstractClientPlayer.hasEffect(GMobEffects.ASTRAL) ? 0.35F : 1.0F;
            modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation)), i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
            modelPart2.xRot = 0.0f;
            modelPart2.render(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation)), i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
            ci.cancel();
        }
    }

}
