package net.orcinus.galosphere.entities.ai.sensors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.orcinus.galosphere.entities.Preserved;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class PreservedEntitySensor extends NearestLivingEntitySensor<Preserved> {

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.copyOf(Iterables.concat(super.requires(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    @Override
    protected void doTick(ServerLevel serverLevel, Preserved preserved) {
        super.doTick(serverLevel, preserved);
        getClosest(preserved, livingEntity -> livingEntity.getType() == EntityType.PLAYER)
                .or(() -> getClosest(preserved, livingEntity -> livingEntity.getType() != EntityType.PLAYER))
                .ifPresentOrElse(livingEntity -> preserved.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, livingEntity), () -> preserved.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE));
    }

    private static Optional<LivingEntity> getClosest(Preserved preserved, Predicate<LivingEntity> predicate) {
        return preserved.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).stream().flatMap(Collection::stream).filter(preserved::canTargetEntity).filter(predicate).findFirst();
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
