package net.orcinus.galosphere.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.entities.SpecterpillarEntity;
import net.orcinus.galosphere.entities.SpectreEntity;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GMemoryModuleTypes;

import java.util.Comparator;
import java.util.Optional;

public class LaySpecterpillar extends Behavior<SpectreEntity> {

    public LaySpecterpillar() {
        super(ImmutableMap.of(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get(), MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.IS_PREGNANT, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, SpectreEntity livingEntity) {
        return !livingEntity.getBlockStateOn().is(GBlocks.LICHEN_MOSS.get());
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, SpectreEntity livingEntity, long l) {
        return this.checkExtraStartConditions(serverLevel, livingEntity);
    }

    @Override
    protected void start(ServerLevel serverLevel, SpectreEntity livingEntity, long l) {
        this.getNearstLichenMoss(livingEntity).filter(blockPos -> serverLevel.getBlockState(blockPos.below()).is(GBlocks.LICHEN_MOSS.get())).ifPresent(blockPos -> {
            BehaviorUtils.setWalkAndLookTargetMemories(livingEntity, blockPos, 1.0F, 0);
            boolean flag = blockPos.distManhattan(livingEntity.blockPosition()) <= 0;
            if (flag) {
                this.stop(serverLevel, livingEntity, l);
            }
        });
    }

    @Override
    protected void stop(ServerLevel serverLevel, SpectreEntity livingEntity, long l) {
        if (!livingEntity.getBlockStateOn().is(GBlocks.LICHEN_MOSS.get())) {
            livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get());
        } else {
            SpecterpillarEntity specterpillar = GEntityTypes.SPECTERPILLAR.get().create(serverLevel);
            Vec3 pos = livingEntity.position();
            specterpillar.moveTo(pos.x(), pos.y() + 0.2D, pos.z(), 0.0F, 0.0f);
            specterpillar.setPersistenceRequired();
            serverLevel.addFreshEntity(specterpillar);
            livingEntity.playSound(SoundEvents.FROG_LAY_SPAWN, 1.0F, 1.0F);
            livingEntity.getBrain().eraseMemory(MemoryModuleType.IS_PREGNANT);
            livingEntity.getBrain().eraseMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get());
        }
    }

    private Optional<BlockPos> getNearstLichenMoss(SpectreEntity livingEntity) {
        return livingEntity.getBrain().getMemory(GMemoryModuleTypes.NEAREST_LICHEN_MOSS.get());
    }

}
