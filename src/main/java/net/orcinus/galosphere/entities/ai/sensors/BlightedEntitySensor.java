package net.orcinus.galosphere.entities.ai.sensors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.orcinus.galosphere.entities.Berserker;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class BlightedEntitySensor extends NearestLivingEntitySensor<Berserker> {

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.copyOf(Iterables.concat(super.requires(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    @Override
    protected void doTick(ServerLevel serverLevel, Berserker blighted) {
        super.doTick(serverLevel, blighted);
        getClosest(blighted, livingEntity -> livingEntity.getType() == EntityType.PLAYER)
                .or(() -> getClosest(blighted, livingEntity -> {
                    Optional<LivingEntity> memory = blighted.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY);
                    return memory.filter(entity -> entity == livingEntity).isPresent();
                }))
                .ifPresentOrElse(livingEntity -> blighted.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, livingEntity), () -> blighted.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE));
    }

    private static Optional<LivingEntity> getClosest(Berserker blighted, Predicate<LivingEntity> predicate) {
        return blighted.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).stream().flatMap(Collection::stream).filter(blighted::canTargetEntity).filter(predicate).findFirst();
    }

    @Override
    protected int radiusXZ() {
        return 24;
    }

    @Override
    protected int radiusY() {
        return 2;
    }

}