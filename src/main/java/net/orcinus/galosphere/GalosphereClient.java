package net.orcinus.galosphere;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.orcinus.galosphere.client.gui.CombustionTableScreen;
import net.orcinus.galosphere.client.model.BerserkerModel;
import net.orcinus.galosphere.client.model.PreservedModel;
import net.orcinus.galosphere.client.model.ImpactModel;
import net.orcinus.galosphere.client.model.PinkSaltPillarModel;
import net.orcinus.galosphere.client.model.SparkleModel;
import net.orcinus.galosphere.client.model.SpecterpillarModel;
import net.orcinus.galosphere.client.model.SpectreModel;
import net.orcinus.galosphere.client.model.SterlingArmorModel;
import net.orcinus.galosphere.client.particles.CrystalRainParticle;
import net.orcinus.galosphere.client.particles.ImpactParticle;
import net.orcinus.galosphere.client.particles.IndicatorParticle;
import net.orcinus.galosphere.client.particles.SpectateOrbParticle;
import net.orcinus.galosphere.client.particles.providers.PinkSaltFallingDustProvider;
import net.orcinus.galosphere.client.particles.providers.SilverBombProvider;
import net.orcinus.galosphere.client.particles.providers.WarpedProvider;
import net.orcinus.galosphere.client.renderer.BerserkerRenderer;
import net.orcinus.galosphere.client.renderer.PreservedRenderer;
import net.orcinus.galosphere.client.renderer.PinkSaltPillarRenderer;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.SpectatorVisionRenderer;
import net.orcinus.galosphere.client.renderer.SpecterpillarRenderer;
import net.orcinus.galosphere.client.renderer.SpectreRenderer;
import net.orcinus.galosphere.client.renderer.ThrowableLaunchedProjectileRenderer;
import net.orcinus.galosphere.client.renderer.block.GildedBeadsRenderer;
import net.orcinus.galosphere.client.renderer.block.ShadowFrameBlockRenderer;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GEvents;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.items.SaltboundTabletItem;
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
                GBlocks.CHANDELIER,
                GBlocks.GLOW_BERRIES_SILVER_LATTICE,
                GBlocks.GLINTED_ALLURITE_CLUSTER,
                GBlocks.GLINTED_LUMIERE_CLUSTER,
                GBlocks.GLINTED_AMETHYST_CLUSTER,
                GBlocks.PINK_SALT_STRAW,
                GBlocks.PINK_SALT_CLUSTER,
                GBlocks.SHADOW_FRAME,
                GBlocks.SUCCULENT
        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), GBlocks.STRANDED_MEMBRANE_BLOCK);

        BlockEntityRenderers.register(GBlockEntityTypes.SHADOW_FRAME, ShadowFrameBlockRenderer::new);
        BlockEntityRenderers.register(GBlockEntityTypes.GILDED_BEADS, GildedBeadsRenderer::new);

        MenuScreens.register(GMenuTypes.COMBUSTION_TABLE, CombustionTableScreen::new);

        ParticleFactoryRegistry particleFactoryRegistry = ParticleFactoryRegistry.getInstance();
        particleFactoryRegistry.register(GParticleTypes.AURA_RINGER_INDICATOR, IndicatorParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.SILVER_BOMB, new SilverBombProvider());
        particleFactoryRegistry.register(GParticleTypes.WARPED, WarpedProvider::new);
        particleFactoryRegistry.register(GParticleTypes.ALLURITE_RAIN, CrystalRainParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.LUMIERE_RAIN, CrystalRainParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.AMETHYST_RAIN, CrystalRainParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.SPECTATE_ORB, SpectateOrbParticle.Provider::new);
        particleFactoryRegistry.register(GParticleTypes.PINK_SALT_FALLING_DUST, PinkSaltFallingDustProvider::new);
        particleFactoryRegistry.register(GParticleTypes.IMPACT, ImpactParticle.Provider::new);

        EntityRendererRegistry.register(GEntityTypes.SIVLER_BOMB, context -> new ThrownItemRenderer<>(context, 1.5F, false));
        EntityRendererRegistry.register(GEntityTypes.GLOW_FLARE, ThrowableLaunchedProjectileRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTRE_FLARE, ThrowableLaunchedProjectileRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPARKLE, SparkleRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTRE, SpectreRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTERPILLAR, SpecterpillarRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.SPECTATOR_VISION, SpectatorVisionRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.BERSERKER, BerserkerRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.PINK_SALT_PILLAR, PinkSaltPillarRenderer::new);
        EntityRendererRegistry.register(GEntityTypes.PRESERVED, PreservedRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPARKLE, SparkleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPECTRE, SpectreModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.SPECTERPILLAR, SpecterpillarModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.BERSERKER, BerserkerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.STERLING_HELMET, SterlingArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.GILDED_BEADS, GildedBeadsRenderer::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.PINK_SALT_PILLAR, PinkSaltPillarModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.IMPACT, ImpactModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GModelLayers.PRESERVED, PreservedModel::createBodyLayer);

        GEvents.clientInit();
        GNetwork.init();

        ItemProperties.register(Items.CROSSBOW, Galosphere.id("glow_flare"), (stack, world, entity, i) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.GLOW_FLARE) ? 1 : 0);
        ItemProperties.register(Items.CROSSBOW, Galosphere.id("spectre_flare"), (stack, world, entity, i) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.SPECTRE_FLARE) ? 1 : 0);
        ItemProperties.register(GItems.BAROMETER, Galosphere.id("weather_level"), new ClampedItemPropertyFunction() {
            private double rotation;
            private int ticksBeforeChange;

            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                Entity entity = livingEntity != null ? livingEntity : itemStack.getEntityRepresentation();
                float[][] predicates = new float[][]{{0.15F, 0.13F, 0.21F, 0.28F, 0.36F, 0.44F, 0.52F, 0.59F, 0.70F, 0.75F, 0.82F, 0.9F}, {0.9F, 0.82F, 0.75F, 0.70F, 0.59F, 0.52F, 0.44F, 0.36F, 0.28F, 0.21F, 0.13F, 0.15F}};
                if (entity == null) {
                    return 0.0f;
                }
                if (clientLevel == null && entity.level() instanceof ClientLevel clientWorld) {
                    clientLevel = clientWorld;
                }
                if (clientLevel == null) {
                    return 0.0f;
                }
                float max;
                float speed = 0.00525F;
                int clearWeatherTime = GalosphereClient.clearWeatherTime;
                int index = clearWeatherTime < 5 ? 0 : Math.max(0, clearWeatherTime / 1000);
                if (clearWeatherTime < 12000) {
                    max = predicates[clientLevel.isRaining() ? 1 : 0][index];
                } else {
                    max = clientLevel.getLevelData().isRaining() ? 0.0F : 1.0F;
                }
                float rainLevel = clientLevel.getRainLevel(1.0F);
                if ((rainLevel > 0.9F || rainLevel < 0.1F) && clearWeatherTime == 0 && this.ticksBeforeChange == 0) {
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
        ItemProperties.register(GItems.SALTBOUND_TABLET, Galosphere.id("using"), (stack, world, entity, i) -> entity != null && entity.getUseItem().getItem() instanceof SaltboundTabletItem ? 1 : 0);
        ItemProperties.register(GItems.SALTBOUND_TABLET, Galosphere.id("cooldown"), (stack, world, entity, i) -> entity instanceof Player player && player.getCooldowns().isOnCooldown(GItems.SALTBOUND_TABLET) ? 1 : 0);
    }
}