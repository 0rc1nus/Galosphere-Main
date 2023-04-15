package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.orcinus.galosphere.blocks.CordycepsBlock;
import net.orcinus.galosphere.entities.SpecterpillarEntity;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Optional;

public class Burrow extends Behavior<SpecterpillarEntity> {

    public Burrow() {
        super(ImmutableMap.of(MemoryModuleType.HURT_BY, MemoryStatus.VALUE_ABSENT, GMemoryModuleTypes.CAN_BURY, MemoryStatus.VALUE_PRESENT, GMemoryModuleTypes.NEAREST_LICHEN_MOSS, MemoryStatus.VALUE_PRESENT), Mth.ceil(65.0f));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, SpecterpillarEntity livingEntity) {
        return livingEntity.isOnGround() && this.getTargetPos(livingEntity).isPresent() && this.getTargetPos(livingEntity).get().distManhattan(livingEntity.blockPosition()) <= 0;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, SpecterpillarEntity livingEntity, long l) {
        Optional<BlockPos> targetPos = this.getTargetPos(livingEntity);
        if (targetPos.isEmpty() || !serverLevel.getBlockState(targetPos.get()).isAir() || livingEntity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY)) {
            livingEntity.setPose(Pose.STANDING);
            return false;
        } else {
            boolean air = serverLevel.getBlockState(targetPos.get()).isAir();
            boolean flag1 = !air;
            boolean flag2 = air && !serverLevel.getBlockState(targetPos.get().below()).is(GBlocks.LICHEN_MOSS);
            if (flag1 || flag2) {
                livingEntity.setPose(Pose.STANDING);
                livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS);
                return false;
            }
        }
        return livingEntity.getRemovalReason() == null;
    }

    @Override
    protected void start(ServerLevel serverLevel, SpecterpillarEntity livingEntity, long l) {
        this.getTargetPos(livingEntity).ifPresent(blockPos -> {
            boolean flag = blockPos.distManhattan(livingEntity.blockPosition()) <= 0;
            if (flag) {
                if (livingEntity.isOnGround()) {
                    livingEntity.setPose(Pose.DIGGING);
                } else {
                    this.stop(serverLevel, livingEntity, l);
                }
            }
        });
    }

    @Override
    protected void stop(ServerLevel serverLevel, SpecterpillarEntity livingEntity, long l) {
        if (livingEntity.getRemovalReason() == null && livingEntity.getBlockStateOn().is(GBlocks.LICHEN_MOSS)) {
            this.getTargetPos(livingEntity).ifPresent(pos -> {
                if (serverLevel.getBlockState(pos).isAir()) {
                    serverLevel.setBlock(pos, GBlocks.LICHEN_CORDYCEPS.defaultBlockState().setValue(CordycepsBlock.ALIVE_STAGE, 1).setValue(CordycepsBlock.ALIVE, true), 2);
                    livingEntity.discard();
                } else {
                    livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS);
                }
            });
        }
    }

    public Optional<BlockPos> getTargetPos(SpecterpillarEntity specterpillar) {
        return specterpillar.getBrain().getMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS);
    }

}
