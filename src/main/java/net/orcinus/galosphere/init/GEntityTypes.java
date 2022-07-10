package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.SilverBombEntity;
import net.orcinus.galosphere.entities.SparkleEntity;

public class GEntityTypes {

    public static final EntityType<SilverBombEntity> SIVLER_BOMB = register("silver_bomb", EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("silver_bomb"));
    public static final EntityType<SparkleEntity> SPARKLE = register("sparkle", EntityType.Builder.of(SparkleEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(1.0F, 0.55F).clientTrackingRange(10).build("sparkle"));

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Galosphere.MODID, id), builder);
    }

}
