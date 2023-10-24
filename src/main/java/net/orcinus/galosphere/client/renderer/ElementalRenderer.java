package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.ElementalModel;
import net.orcinus.galosphere.entities.Elemental;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class ElementalRenderer extends MobRenderer<Elemental, ElementalModel<Elemental>> {
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/elemental/elemental.png");

    public ElementalRenderer(EntityRendererProvider.Context context) {
        super(context, new ElementalModel<>(context.bakeLayer(GModelLayers.ELEMENTAL)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Elemental entity) {
        return TEXTURE;
    }
}
