package net.orcinus.galosphere.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.PinkSaltShard;

@OnlyIn(Dist.CLIENT)
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