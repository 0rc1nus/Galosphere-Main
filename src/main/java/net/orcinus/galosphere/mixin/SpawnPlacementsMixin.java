package net.orcinus.galosphere.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.orcinus.galosphere.entities.SparkleEntity;
import net.orcinus.galosphere.init.GEntityTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SpawnPlacements.class)
public class SpawnPlacementsMixin {

    @Shadow @Final private static Map<EntityType<?>, SpawnPlacements.Data> DATA_BY_TYPE;

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void G$init(CallbackInfo ci) {
        DATA_BY_TYPE.put(GEntityTypes.SPARKLE, new SpawnPlacements.Data(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnPlacements.Type.ON_GROUND, SparkleEntity::checkSparkleSpawnRules));
        DATA_BY_TYPE.put(GEntityTypes.SPECTRE, new SpawnPlacements.Data(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnPlacements.Type.ON_GROUND, (entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> serverLevelAccessor.getBlockState(blockPos.below()).isValidSpawn(serverLevelAccessor, blockPos.below(), entityType)));
    }

}
