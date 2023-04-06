package net.orcinus.galosphere.events;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterEntitySpectatorShadersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.gui.CombustionTableScreen;
import net.orcinus.galosphere.client.gui.GoldenBreathOverlay;
import net.orcinus.galosphere.client.gui.IllusiveOverlay;
import net.orcinus.galosphere.client.gui.SpectreOverlay;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.client.model.SpectreModel;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.client.particles.AuraParticle;
import net.orcinus.galosphere.client.particles.CrystalRainParticle;
import net.orcinus.galosphere.client.particles.IndicatorParticle;
import net.orcinus.galosphere.client.particles.providers.SilverBombProvider;
import net.orcinus.galosphere.client.particles.providers.WarpedProvider;
import net.orcinus.galosphere.client.renderer.GlowFlareEntityRenderer;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.SpectreRenderer;
import net.orcinus.galosphere.client.renderer.layer.BannerLayer;
import net.orcinus.galosphere.client.renderer.layer.HorseBannerLayer;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GParticleTypes;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent 
    public static void onClientSetup(final FMLClientSetupEvent event) {

        MenuScreens.register(GMenuTypes.COMBUSTION_TABLE.get(), CombustionTableScreen::new);

        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.register(new IllusiveOverlay());
        eventBus.register(new GoldenBreathOverlay());
        eventBus.register(new SpectreOverlay());

        event.enqueueWork(() -> ItemProperties.register(Items.CROSSBOW, new ResourceLocation(Galosphere.MODID, "glow_flare"), (stack, world, entity, p_174608_) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.GLOW_FLARE.get()) ? 1.0F : 0.0F));

    }

    @SubscribeEvent
    public static void loadEntityShader(RegisterEntitySpectatorShadersEvent event) {
        event.register(GEntityTypes.SPECTRE.get(), new ResourceLocation(Galosphere.MODID, "shaders/post/fay.json"));
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        HorseRenderer horseRenderer = event.getRenderer(EntityType.HORSE);
        if (horseRenderer == null) return;
        horseRenderer.addLayer(new HorseBannerLayer(horseRenderer));
        event.getSkins().forEach(skin -> {
            PlayerRenderer playerRenderer = event.getSkin(skin);
            if (playerRenderer == null) return;
            playerRenderer.addLayer(new BannerLayer<>(playerRenderer));
        });
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GModelLayers.SPARKLE, SparkleModel::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.STERLING_HELMET, SterlingArmorModel::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.SPECTRE, SpectreModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(GEntityTypes.SPARKLE.get(), SparkleRenderer::new);
        event.registerEntityRenderer(GEntityTypes.SIVLER_BOMB.get(), context -> new ThrownItemRenderer<>(context, 1.5F, false));
        event.registerEntityRenderer(GEntityTypes.SPECTRE.get(), SpectreRenderer::new);
        event.registerEntityRenderer(GEntityTypes.GLOW_FLARE.get(), GlowFlareEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(GParticleTypes.AURA_LISTENER.get(), AuraParticle.Provider::new);
        event.register(GParticleTypes.SILVER_BOMB.get(), new SilverBombProvider());
        event.register(GParticleTypes.WARPED.get(), WarpedProvider::new);
        event.register(GParticleTypes.ALLURITE_RAIN.get(), CrystalRainParticle.Provider::new);
        event.register(GParticleTypes.LUMIERE_RAIN.get(), CrystalRainParticle.Provider::new);
        event.register(GParticleTypes.AURA_RINGER_INDICATOR.get(), IndicatorParticle.Provider::new);
    }

}
