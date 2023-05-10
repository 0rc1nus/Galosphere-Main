package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.SpectreModel;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GModelLayers;

@Environment(EnvType.CLIENT)
public class SpectreRenderer extends MobRenderer<Spectre, SpectreModel<Spectre>> {
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/spectre/spectre.png");
    private static final ResourceLocation LINKED_TEXTURE = Galosphere.id("textures/entity/spectre/spectre_linked.png");

    public SpectreRenderer(EntityRendererProvider.Context context) {
        super(context, new SpectreModel<>(context.bakeLayer(GModelLayers.SPECTRE)), 0.1F);
    }

    @Override
    protected int getBlockLightLevel(Spectre entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(Spectre entity) {
        return entity.getManipulatorUUID() != null ? LINKED_TEXTURE : TEXTURE;
    }

}
