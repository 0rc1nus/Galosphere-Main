package net.orcinus.galosphere.mixin.access;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.renderer.block.ModelBlockRenderer$AmbientOcclusionFace")
public interface AmbientOcclusionFaceAccessor {
    @Accessor
    float[] getBrightness();

    @Accessor
    int[] getLightmap();
}
