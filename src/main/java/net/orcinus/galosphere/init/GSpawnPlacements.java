package net.orcinus.galosphere.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.orcinus.galosphere.entities.SparkleEntity;

import java.util.Map;

public class GSpawnPlacements {

    public static void init(Map<EntityType<?>, SpawnPlacements.Data> map) {
        map.put(GEntityTypes.SPARKLE, new SpawnPlacements.Data(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnPlacements.Type.ON_GROUND, SparkleEntity::checkSparkleSpawnRules));
//        map.put(GEntityTypes.FAY, new SpawnPlacements.Data(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnPlacements.Type.ON_GROUND, (entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> serverLevelAccessor.getBlockState(blockPos.below()).isValidSpawn(serverLevelAccessor, blockPos.below(), entityType)));
    }

}
