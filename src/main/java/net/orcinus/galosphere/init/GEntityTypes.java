package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.SpecterpillarEntity;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.entities.GlowFlareEntity;
import net.orcinus.galosphere.entities.SilverBombEntity;
import net.orcinus.galosphere.entities.SparkleEntity;

public class GEntityTypes {

    public static final EntityType<SilverBombEntity> SIVLER_BOMB = register("silver_bomb", EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("silver_bomb").toString()));
    public static final EntityType<GlowFlareEntity> GLOW_FLARE = register("glow_flare", EntityType.Builder.<GlowFlareEntity>of(GlowFlareEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(Galosphere.id("glow_flare").toString()));
    public static final EntityType<SparkleEntity> SPARKLE = register("sparkle", EntityType.Builder.of(SparkleEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(0.75f, 0.42f).clientTrackingRange(10).build(Galosphere.id("sparkle").toString()));
    public static final EntityType<SpectreEntity> SPECTRE = register("spectre", EntityType.Builder.of(SpectreEntity::new, MobCategory.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(2).build(Galosphere.id("spectre").toString()));
    public static final EntityType<SpecterpillarEntity> SPECTERPILLAR = register("specterpillar", EntityType.Builder.of(SpecterpillarEntity::new, MobCategory.CREATURE).sized(0.4F, 0.3F).clientTrackingRange(8).updateInterval(2).build(Galosphere.id("specterpillar").toString()));

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, Galosphere.id(id), builder);
    }

    public static void init() {
        Util.make(ImmutableMap.<EntityType<? extends LivingEntity>, AttributeSupplier.Builder>builder(), map -> {
            map.put(GEntityTypes.SPARKLE, SparkleEntity.createAttributes());
            map.put(GEntityTypes.SPECTRE, SpectreEntity.createAttributes());
            map.put(GEntityTypes.SPECTERPILLAR, SpecterpillarEntity.createAttributes());
        }).build().forEach(FabricDefaultAttributeRegistry::register);
    }

}
