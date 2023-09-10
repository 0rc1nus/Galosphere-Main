package net.orcinus.galosphere.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2DoubleLinkedOpenHashMap;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.orcinus.galosphere.entities.Blighted;
import net.orcinus.galosphere.entities.ai.tasks.ConditionalWalkIfTargetOutOfReach;
import net.orcinus.galosphere.entities.ai.tasks.Smash;
import net.orcinus.galosphere.entities.ai.tasks.Undermine;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Optional;
import java.util.function.Predicate;

public class BlightedAi {

    public static Brain<?> makeBrain(Brain<Blighted> brain) {
        BlightedAi.initCoreActivity(brain);
        BlightedAi.initIdleActivity(brain);
        BlightedAi.initFightActivity(brain);
        BlightedAi.initRoarActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Blighted> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));
    }

    private static void initRoarActivity(Brain<Blighted> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.ROAR, 5, ImmutableList.of(), GMemoryModuleTypes.IS_ROARING);
    }

    private static void initIdleActivity(Brain<Blighted> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                StartAttacking.create(BlightedAi::findNearestValidAttackTarget),
                SetEntityLookTargetSometimes.create(8.0f, UniformInt.of(30, 60)),
                BlightedAi.createIdleMovementBehaviors()
        ));
    }

    private static void initFightActivity(Brain<Blighted> brain) {
        Predicate<LivingEntity> predicate = livingEntity -> livingEntity instanceof Blighted blighted && blighted.getPhase() != Blighted.Phase.UNDERMINE;
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
                new Smash(),
                BehaviorBuilder.triggerIf(predicate, ConditionalWalkIfTargetOutOfReach.create(1.2F)),
                new Undermine(),
                StopAttackingIfTargetInvalid.create()
        ), MemoryModuleType.ATTACK_TARGET);
    }

    private static RunOne<Blighted> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.stroll(0.3F), 2), Pair.of(SetWalkTargetFromLookTarget.create(1.2F, 3), 2), Pair.of(new DoNothing(30, 60), 1)));
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Blighted blighted) {
        return blighted.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static void updateActivity(Blighted blighted) {
        blighted.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.ROAR, Activity.FIGHT, Activity.IDLE));
    }

}
