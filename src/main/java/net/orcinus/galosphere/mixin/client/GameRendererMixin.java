package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.mixin.access.GameRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(at = @At("TAIL"), method = "checkEntityPostEffect")
    private void GE$checkEntityPostEffect(Entity entity, CallbackInfo ci) {
        if (entity instanceof Spectatable) {
            ((GameRendererAccessor)this).callLoadEffect(Galosphere.id("shaders/post/spectre.json"));
        }
    }

}