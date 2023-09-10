package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.BlightedModel;
import net.orcinus.galosphere.entities.Blighted;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class BlightedRenderer extends MobRenderer<Blighted, BlightedModel<Blighted>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/blighted/blighted.png");

    public BlightedRenderer(EntityRendererProvider.Context context) {
        super(context, new BlightedModel<>(context.bakeLayer(GModelLayers.BLIGHTED)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(Blighted entity) {
        return TEXTURE;
    }
}
