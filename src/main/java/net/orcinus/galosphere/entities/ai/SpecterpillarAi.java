package net.orcinus.galosphere.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.entities.ai.tasks.Burrow;
import net.orcinus.galosphere.entities.ai.tasks.PathfindBurrowSpot;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

public class SpecterpillarAi {

    public static Brain<?> makeBrain(Brain<Specterpillar> brain) {
        SpecterpillarAi.initCoreActivity(brain);
        SpecterpillarAi.initIdleActivity(brain);
        SpecterpillarAi.initDigActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Specterpillar> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic(0.5F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    private static void initIdleActivity(Brain<Specterpillar> brain) {
        brain.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0f, UniformInt.of(30, 60))),
                Pair.of(1, new FollowTemptation(livingEntity -> 0.25F)),
                Pair.of(2, new RunOne<>(
                        ImmutableList.of(
                                Pair.of(RandomStroll.stroll(0.25F), 2),
                                Pair.of(SetWalkTargetFromLookTarget.create(0.25F, 3), 2),
                                Pair.of(new DoNothing(30, 60), 1))))
        ), ImmutableSet.of(Pair.of(GMemoryModuleTypes.CAN_BURY.get(), MemoryStatus.VALUE_ABSENT)));
    }

    private static void initDigActivity(Brain<Specterpillar> brain) {
        brain.addActivityWithConditions(Activity.DIG, ImmutableList.of(
                Pair.of(0, new PathfindBurrowSpot()),
                Pair.of(1, new Burrow())
        ), ImmutableSet.of(Pair.of(GMemoryModuleTypes.CAN_BURY.get(), MemoryStatus.VALUE_PRESENT)));
    }

    public static void updateActivity(Specterpillar specterpillar) {
        specterpillar.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.DIG, Activity.IDLE));
    }

}
