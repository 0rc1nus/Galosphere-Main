package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.Preserved;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Summon extends Behavior<Berserker> {
    private static final int MAX_DURATION = 70;

    public Summon() {
        super(ImmutableMap.of(
                GMemoryModuleTypes.RAMPAGE_TICKS, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT,
                GMemoryModuleTypes.IS_SMASHING, MemoryStatus.VALUE_ABSENT,
                GMemoryModuleTypes.IS_IMPALING, MemoryStatus.VALUE_ABSENT,
                GMemoryModuleTypes.IS_SUMMONING, MemoryStatus.REGISTERED,
                GMemoryModuleTypes.SUMMONING_COOLDOWN, MemoryStatus.VALUE_ABSENT,
                GMemoryModuleTypes.SUMMON_COUNT, MemoryStatus.REGISTERED
        ), MAX_DURATION);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Berserker livingEntity) {
        return livingEntity.getHealth() / livingEntity.getMaxHealth() <= 0.66F && livingEntity.hasPose(Pose.STANDING) && livingEntity.shouldAttack();
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Berserker livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Brain<Berserker> brain = livingEntity.getBrain();
        brain.setMemoryWithExpiry(GMemoryModuleTypes.IS_SUMMONING, Unit.INSTANCE, MAX_DURATION);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        livingEntity.setPhase(Berserker.Phase.SUMMONING);
        livingEntity.playSound(GSoundEvents.BERSERKER_SUMMONING, 10.0f, 1.0F);
    }

    @Override
    protected void tick(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Brain<Berserker> brain = livingEntity.getBrain();
        brain.setMemory(GMemoryModuleTypes.SUMMON_COUNT, brain.getMemory(GMemoryModuleTypes.SUMMON_COUNT).orElse(0) + 1);
        List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(4))
                .stream()
                .filter(livingEntity::canTargetEntity)
                .filter(e -> e.getUUID() != livingEntity.getUUID())
                .toList();
        float time = livingEntity.getBrain().getTimeUntilExpiry(GMemoryModuleTypes.IS_SUMMONING);
        if (time > 20) {
            for (LivingEntity entity : list) {
                if (time % 4 == 0) {
                    entity.hurt(serverLevel.damageSources().mobAttack(livingEntity), 2.0F);
                }
            }
        }
        int max = UniformInt.of(3, 5).sample(serverLevel.getRandom());
        if (serverLevel.getDifficulty() == Difficulty.HARD) {
            max = UniformInt.of(4, 8).sample(serverLevel.getRandom());
        }
        if (brain.hasMemoryValue(GMemoryModuleTypes.SUMMON_COUNT) && brain.getMemory(GMemoryModuleTypes.SUMMON_COUNT).get() > max) {
            return;
        }
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        List<BlockPos> positions = Lists.newArrayList();
        int range = 5;
        int yRange = 2;
        for (int y = -yRange; y <= yRange; y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos position = livingEntity.blockPosition().offset(x, y, z);
                    if (serverLevel.getBlockState(position.below()).isFaceSturdy(serverLevel, position.below(), Direction.UP) && serverLevel.getBlockState(position).isAir()) {
                        positions.add(position);
                    }
                }
            }
        }
        BlockPos randomPos = positions.get(serverLevel.getRandom().nextInt(positions.size()));
        Preserved preserved = GEntityTypes.PRESERVED.create(serverLevel, null, null, livingEntity.blockPosition(), MobSpawnType.TRIGGERED, true, true);
        preserved.moveTo(randomPos.getX(), randomPos.getY(), randomPos.getZ(), 0.0f, 0.0f);
        preserved.setHealth(preserved.getMaxHealth() * ((float) livingEntity.getRandom().nextInt(4, 7) / 10));
        serverLevel.addFreshEntityWithPassengers(preserved);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.setPhase(Berserker.Phase.IDLING);
        livingEntity.getBrain().setMemoryWithExpiry(GMemoryModuleTypes.SUMMONING_COOLDOWN, Unit.INSTANCE, 600);
        livingEntity.getBrain().setMemory(GMemoryModuleTypes.SUMMON_COUNT, 0);
    }
}
