package net.orcinus.galosphere.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.PinkSaltShard;

@Environment(EnvType.CLIENT)
public class PinkSaltShardRenderer extends ArrowRenderer<PinkSaltShard> {
    public static final ResourceLocation TEXTURE = Galosphere.id("textures/entity/projectiles/pink_salt_shard.png");

    public PinkSaltShardRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PinkSaltShard entity) {
        return TEXTURE;
    }
}
