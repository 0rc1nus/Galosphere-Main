package net.orcinus.galosphere.entities.ai.tasks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;

import java.util.Optional;
import java.util.function.Function;

public class ConditionalWalkIfTargetOutOfReach {

    public static OneShot<LivingEntity> create(float f) {
        return create(livingEntity -> f);
    }

    public static OneShot<LivingEntity> create(Function<LivingEntity, Float> function) {
        return BehaviorBuilder.create(instance -> instance.group(instance.registered(MemoryModuleType.WALK_TARGET), instance.registered(MemoryModuleType.LOOK_TARGET), instance.present(MemoryModuleType.ATTACK_TARGET), instance.registered(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(instance, (memoryAccessor, memoryAccessor2, memoryAccessor3, memoryAccessor4) -> (serverLevel, mob, l) -> {
            LivingEntity livingEntity = instance.get(memoryAccessor3);
            Optional<NearestVisibleLivingEntities> optional = instance.tryGet(memoryAccessor4);
            if (optional.isPresent() && optional.get().contains(livingEntity) && mob instanceof Mob mob1 && BehaviorUtils.isWithinAttackRange(mob1, livingEntity, 1)) {
                memoryAccessor.erase();
            } else {
                memoryAccessor2.set(new EntityTracker(livingEntity, true));
                memoryAccessor.set(new WalkTarget(new EntityTracker(livingEntity, false), function.apply(mob), 0));
            }
            return true;
        }));
    }

}
