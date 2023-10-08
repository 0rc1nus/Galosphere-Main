package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.BerserkerModel;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class BerserkerRenderer extends MobRenderer<Berserker, BerserkerModel<Berserker>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/berserker/berserker.png");

    public BerserkerRenderer(EntityRendererProvider.Context context) {
        super(context, new BerserkerModel<>(context.bakeLayer(GModelLayers.BLIGHTED)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(Berserker entity) {
        return TEXTURE;
    }
}
