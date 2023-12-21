package net.orcinus.galosphere.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.PreservedModel;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.init.GModelLayers;

@OnlyIn(Dist.CLIENT)
public class PreservedRenderer extends MobRenderer<Preserved, PreservedModel<Preserved>> {
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/preserved/preserved.png");

    public PreservedRenderer(EntityRendererProvider.Context context) {
        super(context, new PreservedModel<>(context.bakeLayer(GModelLayers.PRESERVED)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Preserved entity) {
        return TEXTURE;
    }
}