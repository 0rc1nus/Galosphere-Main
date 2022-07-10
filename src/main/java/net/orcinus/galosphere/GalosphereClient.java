package net.orcinus.galosphere;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.client.particles.AuraParticle;
import net.orcinus.galosphere.client.particles.providers.SilverBombProvider;
import net.orcinus.galosphere.client.particles.providers.WarpedProvider;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.SterlingArmorRenderer;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GParticleTypes;

public class GalosphereClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), GBlocks.ALLURITE_CLUSTER, GBlocks.LUMIERE_CLUSTER, GBlocks.WARPED_ANCHOR);

        ParticleFactoryRegistry.getInstance().register(GParticleTypes.AURA_LISTENER, AuraParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(GParticleTypes.SILVER_BOMB, new SilverBombProvider());
        ParticleFactoryRegistry.getInstance().register(GParticleTypes.WARPED, WarpedProvider::new);

        EntityRendererRegistry.register(GEntityTypes.SIVLER_BOMB, context -> new ThrownItemRenderer<>(context, 1.5F, false));
        EntityRendererRegistry.register(GEntityTypes.SPARKLE, SparkleRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPARKLE, SparkleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.STERLING_HELMET, SterlingArmorModel::createBodyLayer);

        ArmorRenderer.register(new SterlingArmorRenderer(), GItems.STERLING_HELMET);
    }

}
