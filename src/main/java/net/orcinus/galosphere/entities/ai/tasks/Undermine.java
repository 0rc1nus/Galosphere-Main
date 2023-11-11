package net.orcinus.galosphere.entities.ai.tasks;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.entities.Berserker;
import net.orcinus.galosphere.entities.PinkSaltPillar;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMemoryModuleTypes;
import net.orcinus.galosphere.init.GSoundEvents;

public class Undermine extends Behavior<Berserker> {
    private static final int DURATION = Mth.ceil(22.4F);
    private static final int MAX_DURATION = 50;

    public Undermine() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, GMemoryModuleTypes.IMPALING_COOLDOWN, MemoryStatus.VALUE_ABSENT, GMemoryModuleTypes.IS_SMASHING, MemoryStatus.VALUE_ABSENT, GMemoryModuleTypes.IS_IMPALING, MemoryStatus.REGISTERED, GMemoryModuleTypes.IS_SUMMONING, MemoryStatus.VALUE_ABSENT), MAX_DURATION);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Berserker livingEntity) {
        List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox());
        for (LivingEntity nearby : list) {
            if (nearby.isAlive() && nearby.getType() != GEntityTypes.BERSERKER) {
                return false;
            }
        }
        return livingEntity.hasPose(Pose.STANDING) && livingEntity.shouldAttack() && livingEntity.closerThan(livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get(), 15.0, 20.0);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Berserker livingEntity, long l) {
        return true;
    }

    @Override
    protected void start(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Brain<Berserker> brain = livingEntity.getBrain();
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, DURATION);
        brain.setMemoryWithExpiry(GMemoryModuleTypes.IS_IMPALING, Unit.INSTANCE, MAX_DURATION);
        brain.setMemory(GMemoryModuleTypes.IMPALING_COUNT, brain.getMemory(GMemoryModuleTypes.IMPALING_COUNT).orElse(0) + 1);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        livingEntity.setPhase(Berserker.Phase.UNDERMINE);
        livingEntity.playSound(GSoundEvents.BERSERKER_SMASH, 3.0f, 1.0f);
    }

    @Override
    protected void tick(ServerLevel serverLevel, Berserker livingEntity, long l) {
        Optional<LivingEntity> memory = livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        memory.ifPresent(target -> livingEntity.getLookControl().setLookAt(target.position()));
        livingEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        if (livingEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_COOLING_DOWN)) {
            return;
        }
        if (memory.isPresent() && livingEntity.canTargetEntity(memory.get())) {
            LivingEntity target = memory.get();
            double d = Math.min(target.getY(), livingEntity.getY());
            double e = Math.max(target.getY(), livingEntity.getY()) + 1.0;
            float f = (float)Mth.atan2(target.getZ() - livingEntity.getZ(), target.getX() - livingEntity.getX());
            if (livingEntity.distanceTo(target) < 4.0D) {
                float g;
                int i;
                for (i = 0; i < 5; ++i) {
                    g = f + (float)i * (float)Math.PI * 0.4f;
                    this.createPillar(livingEntity, livingEntity.getX() + (double)Mth.cos(g) * 1.5, livingEntity.getZ() + (double)Mth.sin(g) * 1.5, d, e, g, 0);
                }
                for (i = 0; i < 8; ++i) {
                    g = f + (float)i * (float)Math.PI * 2.0f / 8.0f + 1.2566371f;
                    this.createPillar(livingEntity, livingEntity.getX() + (double)Mth.cos(g) * 2.5, livingEntity.getZ() + (double)Mth.sin(g) * 2.5, d, e, g, 3);
                }
            } else {
                for (int i = 0; i < 16; ++i) {
                    double h = 1.25 * (double)(i + 1);
                    this.createPillar(livingEntity, livingEntity.getX() + (double)Mth.cos(f) * h, livingEntity.getZ() + (double)Mth.sin(f) * h, d, e, f, i);
                }
            }
        }
        livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, MAX_DURATION- DURATION);
    }

    private void createPillar(Berserker blighted, double d, double e, double f, double g, float h, int i) {
        BlockPos blockPos = BlockPos.containing(d, g, e);
        boolean bl = false;
        double j = 0.0;
        do {
            VoxelShape voxelShape;
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState = blighted.level().getBlockState(blockPos2);
            if (!blockState.isFaceSturdy(blighted.level(), blockPos2, Direction.UP)) continue;
            if (!blighted.level().isEmptyBlock(blockPos) && !(voxelShape = blighted.level().getBlockState(blockPos).getCollisionShape(blighted.level(), blockPos)).isEmpty()) {
                j = voxelShape.max(Direction.Axis.Y);
            }
            bl = true;
            break;
        } while ((blockPos = blockPos.below()).getY() >= Mth.floor(f) - 1);
        if (bl) {
            blighted.level().addFreshEntity(new PinkSaltPillar(blighted.level(), d, (double)blockPos.getY() + j, e, h, i, blighted));
        }
    }

    @Override
    protected void stop(ServerLevel serverLevel, Berserker livingEntity, long l) {
        livingEntity.setPhase(Berserker.Phase.IDLING);
        Optional<LivingEntity> memory = livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        Optional<Integer> undermine = livingEntity.getBrain().getMemory(GMemoryModuleTypes.IMPALING_COUNT);
        if (undermine.isPresent() && undermine.get() >= 3) {
            livingEntity.getBrain().setMemoryWithExpiry(GMemoryModuleTypes.IMPALING_COOLDOWN, Unit.INSTANCE, MAX_DURATION);
            livingEntity.getBrain().setMemory(GMemoryModuleTypes.IMPALING_COUNT, 0);
        } else if (memory.isPresent() && memory.get().distanceTo(livingEntity) < 4.0D) {
            livingEntity.getBrain().setMemoryWithExpiry(GMemoryModuleTypes.IMPALING_COOLDOWN, Unit.INSTANCE, 400);
        }
    }
}
