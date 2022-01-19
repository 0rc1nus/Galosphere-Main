package net.orcinus.cavesandtrenches.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.client.entity.model.SparkleModel;
import net.orcinus.cavesandtrenches.client.entity.model.SterlingArmorModel;
import net.orcinus.cavesandtrenches.client.entity.renderer.SparkleRenderer;
import net.orcinus.cavesandtrenches.client.gui.CombustionTableScreen;
import net.orcinus.cavesandtrenches.client.particles.AuraParticle;
import net.orcinus.cavesandtrenches.client.particles.providers.SilverBombProvider;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTEntityTypes;
import net.orcinus.cavesandtrenches.init.CTMenuTypes;
import net.orcinus.cavesandtrenches.init.CTModelLayers;
import net.orcinus.cavesandtrenches.init.CTParticleTypes;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(CTBlocks.ALLURITE_CLUSTER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CTBlocks.LUMIERE_CLUSTER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CTBlocks.MYSTERIA_VINES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CTBlocks.MYSTERIA_VINES_PLANTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CTBlocks.LUMEN_BLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CTBlocks.WARPED_ANCHOR.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(CTBlocks.GLOW_LICHEN_VINES.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(CTBlocks.GLOW_LICHEN_VINES_PLANT.get(), RenderType.cutout());

        MenuScreens.register(CTMenuTypes.COMBUSTION_TABLE.get(), CombustionTableScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CTModelLayers.STERLING_HELMET, SterlingArmorModel::createArmorLayer);
        event.registerLayerDefinition(CTModelLayers.SPARKLE, SparkleModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CTEntityTypes.SIVLER_BOMB.get(), context -> new ThrownItemRenderer<>(context, 1.5F, false));
        event.registerEntityRenderer(CTEntityTypes.SPARKLE.get(), SparkleRenderer::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void init(ParticleFactoryRegisterEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(CTParticleTypes.AURA_LISTENER.get(), AuraParticle.Provider::new);
        engine.register(CTParticleTypes.SILVER_BOMB.get(), new SilverBombProvider());
    }

}
