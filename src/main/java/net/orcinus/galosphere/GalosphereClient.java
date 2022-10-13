package net.orcinus.galosphere;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.orcinus.galosphere.client.gui.CombustionTableScreen;
import net.orcinus.galosphere.client.model.SpectreModel;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.client.particles.AuraParticle;
import net.orcinus.galosphere.client.particles.CrystalRainParticle;
import net.orcinus.galosphere.client.particles.providers.SilverBombProvider;
import net.orcinus.galosphere.client.particles.providers.WarpedProvider;
import net.orcinus.galosphere.client.renderer.GlowFlareEntityRenderer;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.SpectreRenderer;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GParticleTypes;

public class GalosphereClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                GBlocks.ALLURITE_CLUSTER,
                GBlocks.LUMIERE_CLUSTER,
                GBlocks.WARPED_ANCHOR,
                GBlocks.LICHEN_ROOTS,
                GBlocks.BOWL_LICHEN,
                GBlocks.LICHEN_SHELF,
                GBlocks.LICHEN_CORDYCEPS,
                GBlocks.LICHEN_CORDYCEPS_PLANT,
                GBlocks.GLOW_INK_CLUMPS
        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), GBlocks.CHANDELIER);

        MenuScreens.register(GMenuTypes.COMBUSTION_TABLE, CombustionTableScreen::new);

        ParticleFactoryRegistry.getInstance().register(GParticleTypes.AURA_LISTENER, AuraParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(GParticleTypes.SILVER_BOMB, new SilverBombProvider());
        ParticleFactoryRegistry.getInstance().register(GParticleTypes.WARPED, WarpedProvider::new);
        ParticleFactoryRegistry.getInstance().register(GParticleTypes.ALLURITE_RAIN, CrystalRainParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(GParticleTypes.LUMIERE_RAIN, CrystalRainParticle.Provider::new);

        EntityRendererRegistry.register(GEntityTypes.SIVLER_BOMB, context -> new ThrownItemRenderer<>(context, 1.5F, false));
        EntityRendererRegistry.register(GEntityTypes.SPARKLE, SparkleRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTRE, SpectreRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.GLOW_FLARE, GlowFlareEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPARKLE, SparkleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPECTRE, SpectreModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.STERLING_HELMET, SterlingArmorModel::createBodyLayer);

        GNetwork.init();

        ItemProperties.register(Items.CROSSBOW, Galosphere.id("glow_flare"), (stack, world, entity, p_174608_) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.GLOW_FLARE) ? 1.0F : 0.0F);

    }

}
