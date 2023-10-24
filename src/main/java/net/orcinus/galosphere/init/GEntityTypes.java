package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.Elemental;
import net.orcinus.galosphere.entities.GlowFlare;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.entities.SilverBomb;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.entities.SpectatorVision;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.entities.SpectreFlare;

import java.util.Map;

public class GEntityTypes {
    public static final Map<ResourceLocation, EntityType<?>> ENTITY_TYPES = Maps.newLinkedHashMap();

    public static final EntityType<SilverBomb> SIVLER_BOMB = register("silver_bomb", EntityType.Builder.<SilverBomb>of(SilverBomb::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("silver_bomb").toString()));
    public static final EntityType<GlowFlare> GLOW_FLARE = register("glow_flare", EntityType.Builder.<GlowFlare>of(GlowFlare::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("glow_flare").toString()));
    public static final EntityType<SpectreFlare> SPECTRE_FLARE = register("spectre_flare", EntityType.Builder.<SpectreFlare>of(SpectreFlare::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("spectre_flare").toString()));
    public static final EntityType<Sparkle> SPARKLE = register("sparkle", EntityType.Builder.of(Sparkle::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(0.75f, 0.42f).clientTrackingRange(10).build(Galosphere.id("sparkle").toString()));
    public static final EntityType<Spectre> SPECTRE = register("spectre", EntityType.Builder.of(Spectre::new, MobCategory.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(2).build(Galosphere.id("spectre").toString()));
    public static final EntityType<Specterpillar> SPECTERPILLAR = register("specterpillar", EntityType.Builder.of(Specterpillar::new, MobCategory.CREATURE).sized(0.4F, 0.3F).clientTrackingRange(8).updateInterval(2).build(Galosphere.id("specterpillar").toString()));
    public static final EntityType<SpectatorVision> SPECTATOR_VISION = register("spectator_vision", EntityType.Builder.<SpectatorVision>of(SpectatorVision::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(5).build(Galosphere.id("spectator_vision").toString()));
    public static final EntityType<Berserker> BERSERKER = register("berserker", EntityType.Builder.of(Berserker::new, MobCategory.MONSTER).sized(1.4F, 2.2F).clientTrackingRange(16).fireImmune().build(Galosphere.id("berserker").toString()));
    public static final EntityType<Elemental> ELEMENTAL = register("elemental", EntityType.Builder.of(Elemental::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(Galosphere.id("elemental").toString()));
    public static final EntityType<PinkSaltPillar> PINK_SALT_PILLAR = register("pink_salt_pillar", EntityType.Builder.<PinkSaltPillar>of(PinkSaltPillar::new, MobCategory.MISC).sized(0.7F, 1.0F).clientTrackingRange(16).fireImmune().build(Galosphere.id("pink_salt_pillar").toString()));

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> builder) {
        EntityType<T> type = Registry.register(BuiltInRegistries.ENTITY_TYPE, Galosphere.id(id), builder);
        ENTITY_TYPES.put(Galosphere.id(id), type);
        return type;
    }

    public static void init() {
        Util.make(ImmutableMap.<EntityType<? extends LivingEntity>, AttributeSupplier.Builder>builder(), map -> {
            map.put(GEntityTypes.SPARKLE, Sparkle.createAttributes());
            map.put(GEntityTypes.SPECTRE, Spectre.createAttributes());
            map.put(GEntityTypes.SPECTERPILLAR, Specterpillar.createAttributes());
            map.put(GEntityTypes.SPECTATOR_VISION, SpectatorVision.createAttributes());
            map.put(GEntityTypes.BERSERKER, Berserker.createAttributes());
            map.put(GEntityTypes.ELEMENTAL, Elemental.createAttributes());
        }).build().forEach(FabricDefaultAttributeRegistry::register);
    }

}
