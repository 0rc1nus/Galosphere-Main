package net.orcinus.galosphere.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SpecterpillarModel;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.init.GModelLayers;

@OnlyIn(Dist.CLIENT)
public class SpecterpillarRenderer extends MobRenderer<Specterpillar, SpecterpillarModel<Specterpillar>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/specterpillar/specterpillar.png");

    public SpecterpillarRenderer(EntityRendererProvider.Context context) {
        super(context, new SpecterpillarModel<>(context.bakeLayer(GModelLayers.SPECTERPILLAR)), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(Specterpillar entity) {
        return TEXTURE;
    }
}