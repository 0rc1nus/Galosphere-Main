package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.FayModel;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class FayRenderer extends MobRenderer<SpectreEntity, FayModel<SpectreEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Galosphere.MODID, "textures/entity/fay/fay.png");

    public FayRenderer(EntityRendererProvider.Context context) {
        super(context, new FayModel<>(context.bakeLayer(GModelLayers.FAY)), 0.1F);
    }

    @Override
    protected int getBlockLightLevel(SpectreEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SpectreEntity entity) {
        return TEXTURE;
    }

}
