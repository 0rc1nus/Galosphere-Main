package net.orcinus.galosphere.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.warden.Roar;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.ai.tasks.BerserkerRoar;
import net.orcinus.galosphere.entities.ai.tasks.ConditionalWalkIfTargetOutOfReach;
import net.orcinus.galosphere.entities.ai.tasks.Smash;
import net.orcinus.galosphere.entities.ai.tasks.Summon;
import net.orcinus.galosphere.entities.ai.tasks.Undermine;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Optional;
import java.util.function.Predicate;

public class BerserkerAi {

    public static Brain<?> makeBrain(Berserker berserker, Brain<Berserker> brain) {
        BerserkerAi.initCoreActivity(brain);
        BerserkerAi.initIdleActivity(brain);
        BerserkerAi.initRoarActivity(brain);
        BerserkerAi.initFightActivity(berserker, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Berserker> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(GMemoryModuleTypes.RAMPAGE_TICKS)
        ));
    }

    private static void initIdleActivity(Brain<Berserker> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                StartAttacking.create(BerserkerAi::findNearestValidAttackTarget),
                SetEntityLookTargetSometimes.create(8.0f, UniformInt.of(30, 60)),
                BerserkerAi.createIdleMovementBehaviors()
        ));
    }

    private static void initRoarActivity(Brain<Berserker> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.ROAR, 10, ImmutableList.of(
                new BerserkerRoar()
        ), GMemoryModuleTypes.IS_ROARING);
    }

    private static void initFightActivity(Berserker berserker, Brain<Berserker> brain) {
        Predicate<LivingEntity> predicate = livingEntity -> livingEntity instanceof Berserker blighted && (!blighted.hasPose(Pose.ROARING) || blighted.getPhase() != Berserker.Phase.UNDERMINE);
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
                BehaviorBuilder.triggerIf(Berserker::shouldUseMeleeAttack, MeleeAttack.create(30)),
                new Smash(),
                BehaviorBuilder.triggerIf(predicate, ConditionalWalkIfTargetOutOfReach.create(1.2F)),
                new Undermine(),
                new Summon(),
                StopAttackingIfTargetInvalid.create(livingEntity -> !berserker.canTargetEntity(livingEntity), (mob, livingEntity) -> {}, false)
        ), MemoryModuleType.ATTACK_TARGET);
    }

    private static RunOne<Berserker> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(
                Pair.of(RandomStroll.stroll(0.3F), 2),
                Pair.of(SetWalkTargetFromLookTarget.create(1.2F, 3), 2),
                Pair.of(new DoNothing(30, 60), 1)
        ));
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Berserker blighted) {
        return blighted.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static void updateActivity(Berserker blighted) {
        blighted.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.ROAR, Activity.FIGHT, Activity.IDLE));
    }

}
