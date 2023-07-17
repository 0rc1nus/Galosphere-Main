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
import net.orcinus.galosphere.entities.Specterpillar;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Optional;

public class Burrow extends Behavior<Specterpillar> {

    public Burrow() {
        super(ImmutableMap.of(MemoryModuleType.HURT_BY, MemoryStatus.VALUE_ABSENT, GMemoryModuleTypes.CAN_BURY, MemoryStatus.VALUE_PRESENT, GMemoryModuleTypes.NEAREST_LICHEN_MOSS, MemoryStatus.VALUE_PRESENT), Mth.ceil(65.0f));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Specterpillar livingEntity) {
        return livingEntity.onGround() && this.getTargetPos(livingEntity).isPresent() && this.getTargetPos(livingEntity).get().distManhattan(livingEntity.blockPosition()) <= 0;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Specterpillar livingEntity, long l) {
        Optional<BlockPos> targetPos = this.getTargetPos(livingEntity);
        if (targetPos.isEmpty()) return false;
        BlockPos blockPos = targetPos.get();
        if (serverLevel.getBlockState(blockPos).isAir() && serverLevel.getBlockState(blockPos.below()).is(GBlocks.LICHEN_MOSS)) {
            return true;
        } else {
            livingEntity.setPose(Pose.STANDING);
            livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS);
            return false;
        }
    }

    @Override
    protected void start(ServerLevel serverLevel, Specterpillar livingEntity, long l) {
        this.getTargetPos(livingEntity).ifPresent(blockPos -> {
            if (blockPos.distManhattan(livingEntity.blockPosition()) <= 0 && livingEntity.onGround()) {
                livingEntity.setPose(Pose.DIGGING);
            }
        });
    }

    @Override
    protected void stop(ServerLevel serverLevel, Specterpillar livingEntity, long l) {
        if (this.timedOut(l)) {
            this.getTargetPos(livingEntity).filter(blockPos -> serverLevel.getBlockState(blockPos).isAir()).ifPresentOrElse(pos -> {
                serverLevel.setBlock(pos, GBlocks.LICHEN_CORDYCEPS.defaultBlockState().setValue(CordycepsBlock.ALIVE_STAGE, 1).setValue(CordycepsBlock.ALIVE, true), 2);
                livingEntity.discard();
            }, () -> livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS));
        }
    }

    public Optional<BlockPos> getTargetPos(Specterpillar specterpillar) {
        return specterpillar.getBrain().getMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS);
    }

}