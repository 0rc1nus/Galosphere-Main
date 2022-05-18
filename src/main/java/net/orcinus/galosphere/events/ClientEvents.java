package net.orcinus.galosphere.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.gui.CombustionTableScreen;
import net.orcinus.galosphere.client.gui.IllusiveOverlay;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.client.particles.AuraEmissionParticle;
import net.orcinus.galosphere.client.particles.AuraParticle;
import net.orcinus.galosphere.client.particles.providers.SilverBombProvider;
import net.orcinus.galosphere.client.particles.providers.WarpedProvider;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.layer.BannerLayer;
import net.orcinus.galosphere.client.renderer.layer.HorseBannerLayer;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GParticleTypes;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent 
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(GBlocks.ALLURITE_CLUSTER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GBlocks.LUMIERE_CLUSTER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GBlocks.MYSTERIA_VINES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GBlocks.MYSTERIA_VINES_PLANTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GBlocks.WARPED_ANCHOR.get(), RenderType.cutout());

        MenuScreens.register(GMenuTypes.COMBUSTION_TABLE.get(), CombustionTableScreen::new);

        MinecraftForge.EVENT_BUS.register(new IllusiveOverlay());

    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        HorseRenderer horseRenderer = event.getRenderer(EntityType.HORSE);
        horseRenderer.addLayer(new HorseBannerLayer(horseRenderer));
        event.getSkins().forEach(skin -> {
            PlayerRenderer playerRenderer = event.getSkin(skin);
            playerRenderer.addLayer(new BannerLayer<>(playerRenderer));
        });
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GModelLayers.SPARKLE, SparkleModel::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.STERLING_HELMET, SterlingArmorModel::createArmorLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(GEntityTypes.SPARKLE.get(), SparkleRenderer::new);
        event.registerEntityRenderer(GEntityTypes.SIVLER_BOMB.get(), context -> new ThrownItemRenderer<>(context, 1.5F, false));
    }

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(GParticleTypes.AURA_LISTENER.get(), AuraParticle.Provider::new);
        engine.register(GParticleTypes.AURA_EMISSION.get(), AuraEmissionParticle.Provider::new);
        engine.register(GParticleTypes.SILVER_BOMB.get(), new SilverBombProvider());
        engine.register(GParticleTypes.WARPED.get(), WarpedProvider::new);
    }

}
