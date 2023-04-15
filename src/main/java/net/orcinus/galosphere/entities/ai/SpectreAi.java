package net.orcinus.galosphere.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FlyingRandomStroll;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.entities.ai.tasks.FindValidLandingPosition;
import net.orcinus.galosphere.entities.ai.tasks.LaySpecterpillar;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItemTags;

public class SpectreAi {

    public static Brain<?> makeBrain(Brain<SpectreEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initLaySpawnActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<SpectreEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    private static void initIdleActivity(Brain<SpectreEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new AnimalMakeLove(GEntityTypes.SPECTRE, 1.0F)),
                Pair.of(1, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(2, new RunOne<>(
                        ImmutableList.of(
                                Pair.of(new FlyingRandomStroll(1.0F), 2),
                                Pair.of(new SetWalkTargetFromLookTarget(1.0F, 3), 2),
                                Pair.of(new DoNothing(30, 60), 1)
                        ))
                ))
        );
    }

    private static void initLaySpawnActivity(Brain<SpectreEntity> brain) {
        brain.addActivityWithConditions(Activity.LAY_SPAWN, ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(1, new FindValidLandingPosition(GBlocks.LICHEN_MOSS)),
                Pair.of(2, new LaySpecterpillar()),
                Pair.of(3, new RunOne<>(
                        ImmutableList.of(
                                Pair.of(new RandomStroll(1.0F), 2),
                                Pair.of(new SetWalkTargetFromLookTarget(1.0F, 3), 1),
                                Pair.of(new DoNothing(5, 20), 1)
                        )
                ))
                ),
                ImmutableSet.of(Pair.of(MemoryModuleType.IS_PREGNANT, MemoryStatus.VALUE_PRESENT)));
    }

    public static void updateActivity(SpectreEntity entity) {
        entity.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.LAY_SPAWN, Activity.IDLE));
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(GItemTags.SPECTRE_TEMPT_ITEMS);
    }

}