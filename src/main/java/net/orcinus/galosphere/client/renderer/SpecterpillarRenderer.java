package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SpecterpillarModel;
import net.orcinus.galosphere.entities.SpecterpillarEntity;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class SpecterpillarRenderer extends MobRenderer<SpecterpillarEntity, SpecterpillarModel<SpecterpillarEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/specterpillar/specterpillar.png");

    public SpecterpillarRenderer(EntityRendererProvider.Context context) {
        super(context, new SpecterpillarModel<>(context.bakeLayer(GModelLayers.SPECTERPILLAR)), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpecterpillarEntity entity) {
        return TEXTURE;
    }
}
