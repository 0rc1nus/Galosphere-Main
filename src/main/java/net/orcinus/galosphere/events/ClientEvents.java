package net.orcinus.galosphere.events;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterEntitySpectatorShadersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.SpectatorTickHandler;
import net.orcinus.galosphere.client.gui.CombustionTableScreen;
import net.orcinus.galosphere.client.gui.GoldenBreathOverlay;
import net.orcinus.galosphere.client.gui.SpectatorVisionOverlay;
import net.orcinus.galosphere.client.gui.SpectreOverlay;
import net.orcinus.galosphere.client.model.BerserkerModel;
import net.orcinus.galosphere.client.model.PinkSaltPillarModel;
import net.orcinus.galosphere.client.model.PreservedModel;
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
import net.orcinus.galosphere.client.renderer.PinkSaltPillarRenderer;
import net.orcinus.galosphere.client.renderer.PinkSaltShardRenderer;
import net.orcinus.galosphere.client.renderer.PreservedRenderer;
import net.orcinus.galosphere.client.renderer.SparkleRenderer;
import net.orcinus.galosphere.client.renderer.SpectatorVisionRenderer;
import net.orcinus.galosphere.client.renderer.SpecterpillarRenderer;
import net.orcinus.galosphere.client.renderer.SpectreRenderer;
import net.orcinus.galosphere.client.renderer.ThrowableLaunchedProjectileRenderer;
import net.orcinus.galosphere.client.renderer.block.GildedBeadsRenderer;
import net.orcinus.galosphere.client.renderer.block.ShadowFrameBlockRenderer;
import net.orcinus.galosphere.client.renderer.layer.BannerLayer;
import net.orcinus.galosphere.client.renderer.layer.HorseBannerLayer;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GModelLayers;
import net.orcinus.galosphere.init.GParticleTypes;
import net.orcinus.galosphere.items.SaltboundTabletItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;
    public static int clearWeatherTime;

    @SubscribeEvent 
    public static void onClientSetup(final FMLClientSetupEvent event) {

        MenuScreens.register(GMenuTypes.COMBUSTION_TABLE.get(), CombustionTableScreen::new);

        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.register(new GoldenBreathOverlay());
        eventBus.register(new SpectreOverlay());
        eventBus.register(new SpectatorVisionOverlay());
        eventBus.register(new CameraEvents());

        eventBus.addListener((TickEvent.ClientTickEvent clientTickEvent) -> SpectatorTickHandler.tick());

        event.enqueueWork(() -> {
            ItemProperties.register(Items.CROSSBOW, Galosphere.id("glow_flare"), (stack, world, entity, p_174608_) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.GLOW_FLARE.get()) ? 1.0F : 0.0F);
            ItemProperties.register(Items.CROSSBOW, Galosphere.id("spectre_flare"), (stack, world, entity, p_174608_) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, GItems.SPECTRE_FLARE.get()) ? 1.0F : 0.0F);
            ItemProperties.register(GItems.BAROMETER.get(), Galosphere.id("weather_level"), new ClampedItemPropertyFunction() {
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
                    int clearWeatherTime = ClientEvents.clearWeatherTime;
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
            ItemProperties.register(GItems.SALTBOUND_TABLET.get(), Galosphere.id("using"), (stack, world, entity, i) -> entity != null && entity.getUseItem().getItem() instanceof SaltboundTabletItem ? 1 : 0);
            ItemProperties.register(GItems.SALTBOUND_TABLET.get(), Galosphere.id("cooldown"), (stack, world, entity, i) -> entity instanceof Player player && player.getCooldowns().isOnCooldown(GItems.SALTBOUND_TABLET.get()) ? 1 : 0);
        });

    }

    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike after, ItemLike... block) {
        List<ItemLike> stream = Lists.newArrayList(Arrays.stream(block).toList());
        Collections.reverse(stream);
        stream.forEach(blk -> addAfter(map, after, blk));
    }

    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike before, ItemLike... block) {
        List<ItemLike> stream = Lists.newArrayList(Arrays.stream(block).toList());
        Collections.reverse(stream);
        stream.forEach(blk -> addBefore(map, before, blk));
    }

    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike after, ItemLike block) {
        map.putAfter(FUNCTION.apply(after), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike before, ItemLike block) {
        map.putBefore(FUNCTION.apply(before), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void accept(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike block) {
        map.put(new ItemStack(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (tabKey.equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            addAfter(entries, Blocks.AMETHYST_BLOCK, GBlocks.AMETHYST_STAIRS.get(), GBlocks.AMETHYST_SLAB.get(), GBlocks.CHISELED_AMETHYST.get(), GBlocks.AMETHYST_LAMP.get(), GBlocks.SMOOTH_AMETHYST.get(), GBlocks.SMOOTH_AMETHYST_STAIRS.get(), GBlocks.SMOOTH_AMETHYST_SLAB.get(), GBlocks.AMETHYST_BRICKS.get(), GBlocks.AMETHYST_BRICK_STAIRS.get(), GBlocks.AMETHYST_BRICK_SLAB.get(), GBlocks.ALLURITE_BLOCK.get(), GBlocks.ALLURITE_STAIRS.get(), GBlocks.ALLURITE_SLAB.get(), GBlocks.CHISELED_ALLURITE.get(), GBlocks.ALLURITE_LAMP.get(), GBlocks.SMOOTH_ALLURITE.get(), GBlocks.SMOOTH_ALLURITE_STAIRS.get(), GBlocks.SMOOTH_ALLURITE_SLAB.get(), GBlocks.ALLURITE_BRICKS.get(), GBlocks.ALLURITE_BRICK_STAIRS.get(), GBlocks.ALLURITE_BRICK_SLAB.get(), GBlocks.LUMIERE_BLOCK.get(), GBlocks.LUMIERE_STAIRS.get(), GBlocks.LUMIERE_SLAB.get(), GBlocks.CHISELED_LUMIERE.get(), GBlocks.LUMIERE_LAMP.get(), GBlocks.SMOOTH_LUMIERE.get(), GBlocks.SMOOTH_LUMIERE_STAIRS.get(), GBlocks.SMOOTH_LUMIERE_SLAB.get(), GBlocks.LUMIERE_BRICKS.get(), GBlocks.LUMIERE_BRICK_STAIRS.get(), GBlocks.LUMIERE_BRICK_SLAB.get());
            accept(entries, GBlocks.SILVER_BLOCK.get());
            accept(entries, GBlocks.SILVER_PANEL.get());
            accept(entries, GBlocks.SILVER_PANEL_STAIRS.get());
            accept(entries, GBlocks.SILVER_PANEL_SLAB.get());
            accept(entries, GBlocks.SILVER_TILES.get());
            accept(entries, GBlocks.SILVER_TILES_STAIRS.get());
            accept(entries, GBlocks.SILVER_TILES_SLAB.get());
            accept(entries, GBlocks.SILVER_LATTICE.get());
        }

        if (tabKey.equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            addAfter(entries, Blocks.DEEPSLATE_IRON_ORE, GBlocks.SILVER_ORE.get(), GBlocks.DEEPSLATE_SILVER_ORE.get());
            addAfter(entries, Blocks.RAW_GOLD_BLOCK, GBlocks.RAW_SILVER_BLOCK.get());
            addAfter(entries, Blocks.AMETHYST_CLUSTER, GBlocks.GLINTED_AMETHYST_CLUSTER.get(), GBlocks.ALLURITE_BLOCK.get(), GBlocks.ALLURITE_CLUSTER.get(), GBlocks.GLINTED_ALLURITE_CLUSTER.get(), GBlocks.LUMIERE_BLOCK.get(), GBlocks.LUMIERE_CLUSTER.get(), GBlocks.GLINTED_LUMIERE_CLUSTER.get());
            addAfter(entries, Blocks.GLOW_LICHEN, GBlocks.GLOW_INK_CLUMPS.get());
            addAfter(entries, Blocks.PEARLESCENT_FROGLIGHT, GBlocks.AMETHYST_LAMP.get(), GBlocks.ALLURITE_LAMP.get(), GBlocks.LUMIERE_LAMP.get());
        }

        if (tabKey.equals(CreativeModeTabs.INGREDIENTS)) {
            addAfter(entries, Items.RAW_GOLD, GItems.RAW_SILVER.get());
            addAfter(entries, Items.GOLD_INGOT, GItems.SILVER_INGOT.get());
            addAfter(entries, Items.GOLD_NUGGET, GItems.SILVER_NUGGET.get());
            addAfter(entries, Items.AMETHYST_SHARD, GItems.ALLURITE_SHARD.get(), GItems.LUMIERE_SHARD.get());
        }

        if (tabKey.equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            addAfter(entries, Blocks.END_ROD, GBlocks.CHANDELIER.get());
            addAfter(entries, Blocks.SMITHING_TABLE, GBlocks.COMBUSTION_TABLE.get());
            addAfter(entries, Blocks.RESPAWN_ANCHOR, GBlocks.MONSTROMETER.get(), GBlocks.WARPED_ANCHOR.get());
        }

        if (tabKey.equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            addAfter(entries, Blocks.SNOW, GBlocks.LICHEN_MOSS.get());
            addAfter(entries, Blocks.RED_MUSHROOM, GBlocks.BOWL_LICHEN.get());
            addAfter(entries, Blocks.CACTUS, GBlocks.LICHEN_SHELF.get(), GBlocks.LICHEN_ROOTS.get());
        }

        if (tabKey.equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            addAfter(entries, Items.GLOW_BERRIES, GItems.LICHEN_CORDYCEPS.get(), GItems.GOLDEN_LICHEN_CORDYCEPS.get());
        }

        if (tabKey.equals(CreativeModeTabs.SPAWN_EGGS)) {
            addAfter(entries, Items.SHEEP_SPAWN_EGG, GItems.SPARKLE_SPAWN_EGG.get(), GItems.SPECTRE_SPAWN_EGG.get(), GItems.SPECTERPILLAR_SPAWN_EGG.get());
        }

        if (tabKey.equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            addAfter(entries, Items.CLOCK, GItems.BAROMETER.get());
            addBefore(entries, Items.SADDLE, GItems.GLOW_FLARE.get(), GItems.SPECTRE_FLARE.get());
        }

        if (tabKey.equals(CreativeModeTabs.COMBAT)) {
            addAfter(entries, Items.CHAINMAIL_BOOTS, GItems.STERLING_HELMET.get(), GItems.STERLING_CHESTPLATE.get(), GItems.STERLING_LEGGINGS.get(), GItems.STERLING_BOOTS.get());
            addAfter(entries, Items.LEATHER_HORSE_ARMOR, GItems.STERLING_HORSE_ARMOR.get());
            addAfter(entries, Items.TNT, GItems.SILVER_BOMB.get());
        }

    }

    @SubscribeEvent
    public static void loadEntityShader(RegisterEntitySpectatorShadersEvent event) {
        event.register(GEntityTypes.SPECTRE.get(), Galosphere.id("shaders/post/spectre.json"));
        event.register(GEntityTypes.SPECTATOR_VISION.get(), Galosphere.id("shaders/post/spectre.json"));
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
        event.registerLayerDefinition(GModelLayers.SPECTERPILLAR, SpecterpillarModel::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.GILDED_BEADS, GildedBeadsRenderer::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.BERSERKER, BerserkerModel::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.PRESERVED, PreservedModel::createBodyLayer);
        event.registerLayerDefinition(GModelLayers.PINK_SALT_PILLAR, PinkSaltPillarModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(GEntityTypes.SPARKLE.get(), SparkleRenderer::new);
        event.registerEntityRenderer(GEntityTypes.SILVER_BOMB.get(), context -> new ThrownItemRenderer<>(context, 1.5F, false));
        event.registerEntityRenderer(GEntityTypes.SPECTRE.get(), SpectreRenderer::new);
        event.registerEntityRenderer(GEntityTypes.GLOW_FLARE.get(), ThrowableLaunchedProjectileRenderer::new);
        event.registerEntityRenderer(GEntityTypes.SPECTRE_FLARE.get(), ThrowableLaunchedProjectileRenderer::new);
        event.registerEntityRenderer(GEntityTypes.SPECTERPILLAR.get(), SpecterpillarRenderer::new);
        event.registerEntityRenderer(GEntityTypes.SPECTATOR_VISION.get(), SpectatorVisionRenderer::new);
        event.registerEntityRenderer(GEntityTypes.BERSERKER.get(), BerserkerRenderer::new);
        event.registerEntityRenderer(GEntityTypes.PRESERVED.get(), PreservedRenderer::new);
        event.registerEntityRenderer(GEntityTypes.PINK_SALT_PILLAR.get(), PinkSaltPillarRenderer::new);
        event.registerEntityRenderer(GEntityTypes.PINK_SALT_SHARD.get(), PinkSaltShardRenderer::new);
        event.registerBlockEntityRenderer(GBlockEntityTypes.SHADOW_FRAME.get(), ShadowFrameBlockRenderer::new);
        event.registerBlockEntityRenderer(GBlockEntityTypes.GILDED_BEADS.get(), GildedBeadsRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(GParticleTypes.SILVER_BOMB.get(), new SilverBombProvider());
        event.registerSpriteSet(GParticleTypes.WARPED.get(), WarpedProvider::new);
        event.registerSpriteSet(GParticleTypes.ALLURITE_RAIN.get(), CrystalRainParticle.Provider::new);
        event.registerSpriteSet(GParticleTypes.LUMIERE_RAIN.get(), CrystalRainParticle.Provider::new);
        event.registerSpriteSet(GParticleTypes.AMETHYST_RAIN.get(), CrystalRainParticle.Provider::new);
        event.registerSpriteSet(GParticleTypes.AURA_RINGER_INDICATOR.get(), IndicatorParticle.Provider::new);
        event.registerSpriteSet(GParticleTypes.SPECTATE_ORB.get(), SpectateOrbParticle.Provider::new);
        event.registerSpriteSet(GParticleTypes.PINK_SALT_FALLING_DUST.get(), PinkSaltFallingDustProvider::new);
        event.registerSpriteSet(GParticleTypes.IMPACT.get(), ImpactParticle.Provider::new);
    }

}
