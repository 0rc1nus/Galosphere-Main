package net.orcinus.galosphere;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.orcinus.galosphere.client.gui.CombustionTableScreen;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.client.model.SpecterpillarModel;
import net.orcinus.galosphere.client.model.SpectreModel;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.client.particles.CrystalRainParticle;
import net.orcinus.galosphere.client.particles.IndicatorParticle;
import net.orcinus.galosphere.client.particles.providers.SilverBombProvider;
import net.orcinus.galosphere.client.particles.providers.WarpedProvider;
import net.orcinus.galosphere.client.renderer.GlowFlareEntityRenderer;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.SpecterpillarRenderer;
import net.orcinus.galosphere.client.renderer.SpectreRenderer;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GParticleTypes;
import org.jetbrains.annotations.Nullable;

public class GalosphereClient implements ClientModInitializer {

    public static int clearWeatherTime;

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
                GBlocks.GLOW_INK_CLUMPS,
                GBlocks.POTTED_BOWL_LICHEN,
                GBlocks.POTTED_LICHEN_ROOTS,
                GBlocks.SILVER_LATTICE,
                GBlocks.CHANDELIER
        );

        MenuScreens.register(GMenuTypes.COMBUSTION_TABLE, CombustionTableScreen::new);

        ParticleFactoryRegistry particleFactoryRegistry = ParticleFactoryRegistry.getInstance();
        particleFactoryRegistry.register(GParticleTypes.AURA_RINGER_INDICATOR, IndicatorParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.SILVER_BOMB, new SilverBombProvider());
        particleFactoryRegistry.register(GParticleTypes.WARPED, WarpedProvider::new);
        particleFactoryRegistry.register(GParticleTypes.ALLURITE_RAIN, CrystalRainParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.LUMIERE_RAIN, CrystalRainParticle.Provider::new);

        EntityRendererRegistry.register(GEntityTypes.SIVLER_BOMB, context -> new ThrownItemRenderer<>(context, 1.5F, false));
        EntityRendererRegistry.register(GEntityTypes.GLOW_FLARE, GlowFlareEntityRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPARKLE, SparkleRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTRE, SpectreRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTERPILLAR, SpecterpillarRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPARKLE, SparkleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPECTRE, SpectreModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPECTERPILLAR, SpecterpillarModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.STERLING_HELMET, SterlingArmorModel::createBodyLayer);

        GNetwork.init();

        ItemProperties.register(Items.CROSSBOW, Galosphere.id("glow_flare"), (stack, world, entity, i) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.GLOW_FLARE) ? 1 : 0);
        ItemProperties.register(GItems.BAROMETER, Galosphere.id("weather_level"), new ClampedItemPropertyFunction() {
            private double rotation;
            private int ticksBeforeChange;

            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                Entity entity = livingEntity != null ? livingEntity : itemStack.getEntityRepresentation();
                float[][] predicates = new float[][]{{0.25F, 0.3F, 0.5F, 0.7F, 0.9F}, {0.9F, 0.7F, 0.5F, 0.3F, 0.25F}};
                if (entity == null) {
                    return 0.0f;
                }
                if (clientLevel == null && entity.level instanceof ClientLevel clientWorld) {
                    clientLevel = clientWorld;
                }
                if (clientLevel == null) {
                    return 0.0f;
                }
                float max;
                float speed = 0.00525F;
                if (GalosphereClient.clearWeatherTime < 12000) {
                    max = predicates[clientLevel.isRaining() ? 1 : 0][GalosphereClient.clearWeatherTime / 3000];
                } else {
                    max = clientLevel.getLevelData().isRaining() ? 0.0F : 1.0F;
                }
                float rainLevel = clientLevel.getRainLevel(1.0F);
                if ((rainLevel > 0.9F || rainLevel < 0.1F) && GalosphereClient.clearWeatherTime == 0 && this.ticksBeforeChange == 0) {
                    this.ticksBeforeChange = 800;
                }
                if (this.ticksBeforeChange > 0) {
                    this.ticksBeforeChange--;
                }
                if (!clientLevel.dimensionType().natural() && clientLevel.getRandom().nextFloat() < 0.1F) {
                    this.rotation = Mth.positiveModulo(Math.random() - this.rotation, 1);
                }
                if (this.rotation < max && this.ticksBeforeChange == 0) {
                    this.rotation += speed;
                }
                if (this.rotation > max && this.ticksBeforeChange == 0) {
                    this.rotation -= speed;
                }
                return this.rotation >= 0.99 ? 1 : (float) this.rotation;
            }

        });
    }
}