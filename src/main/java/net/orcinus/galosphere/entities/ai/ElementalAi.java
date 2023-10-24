package net.orcinus.galosphere.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
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
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.orcinus.galosphere.entities.Elemental;

import java.util.Optional;

public class ElementalAi {

    public static Brain<?> makeBrain(Brain<Elemental>  brain) {
        ElementalAi.initCoreActivity(brain);
        ElementalAi.initIdleActivity(brain);
        ElementalAi.initFightActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Elemental> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));
    }

    private static void initIdleActivity(Brain<Elemental> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                StartAttacking.create(ElementalAi::findNearestValidAttackTarget),
                SetEntityLookTargetSometimes.create(8.0f, UniformInt.of(30, 60)),
                ElementalAi.createIdleMovementBehaviors()
        ));
    }

    private static void initFightActivity(Brain<Elemental> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.5F),
                MeleeAttack.create(40),
                StopAttackingIfTargetInvalid.create()
        ), MemoryModuleType.ATTACK_TARGET);
    }

    private static RunOne<Elemental> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(
                Pair.of(RandomStroll.stroll(1.0F), 2),
                Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                Pair.of(new DoNothing(30, 60), 1)
        ));
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Elemental elemental) {
        return elemental.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static void updateActivity(Elemental elemental) {
        elemental.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

}
