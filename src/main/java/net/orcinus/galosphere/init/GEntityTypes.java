package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.entities.SilverBombEntity;
import net.orcinus.galosphere.entities.SparkleEntity;

public class GEntityTypes {

    public static final EntityType<SilverBombEntity> SIVLER_BOMB = register("silver_bomb", EntityType.Builder.<SilverBombEntity>of(SilverBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("silver_bomb"));
    public static final EntityType<SparkleEntity> SPARKLE = register("sparkle", FabricEntityTypeBuilder.createMob().entityFactory(SparkleEntity::new).spawnGroup(MobCategory.UNDERGROUND_WATER_CREATURE).spawnRestriction(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SparkleEntity::checkSparkleSpawnRules).dimensions(EntityDimensions.scalable(1.0F, 0.55F)).trackRangeChunks(10).build());

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Galosphere.MODID, id), builder);
    }

}
