package net.orcinus.cavesandtrenches.mixin.client;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.world.entity.animal.horse.Horse;
import net.orcinus.cavesandtrenches.client.renderer.layer.HorseBannerLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {

    public HorseRendererMixin(EntityRendererProvider.Context context, HorseModel<Horse> model, float shadowSize) {
        super(context, model, shadowSize);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new HorseBannerLayer(this));
    }

}
