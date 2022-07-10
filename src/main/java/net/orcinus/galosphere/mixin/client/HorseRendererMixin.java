package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.world.entity.animal.horse.Horse;
import net.orcinus.galosphere.client.renderer.layer.HorseBannerLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {

    public HorseRendererMixin(EntityRendererProvider.Context context, HorseModel<Horse> horseModel, float f) {
        super(context, horseModel, f);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void G$init(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new HorseBannerLayer(this));
    }
}
