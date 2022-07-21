package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.orcinus.galosphere.client.renderer.SterlingArmorRenderer;
import net.orcinus.galosphere.client.renderer.layer.BannerLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void G$init(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        this.addLayer(new BannerLayer<>(this));
        this.addLayer(new SterlingArmorRenderer<>(this));
    }

}
