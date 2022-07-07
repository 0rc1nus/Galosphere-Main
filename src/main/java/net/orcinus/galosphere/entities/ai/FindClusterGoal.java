package net.orcinus.galosphere.entities.ai;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.entities.SparkleEntity;

import java.util.List;

public class FindClusterGoal extends Goal {
    private final SparkleEntity sparkle;
    private int eatingTicks = 0;
    private final UniformInt EATING_COOLDOWN = UniformInt.of(400, 1200);

    public FindClusterGoal(SparkleEntity sparkle) {
        this.sparkle = sparkle;
    }

    @Override
    public boolean canUse() {
        return this.getClusterPos() != null && !this.sparkle.isBaby() && this.sparkle.getEatingCooldownTicks() == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.eatingTicks < 60;
    }

    @Override
    public void stop() {
        BlockPos pos = this.getClusterPos();
        Level world = this.sparkle.level;
        if (pos != null) {
            world.setBlock(pos, world.getBlockState(pos).setValue(PollinatedClusterBlock.POLLINATED, true), 2);
            world.levelEvent(1500, pos, 1);
            this.sparkle.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1.0F, 1.0F);
            this.eatingTicks = 0;
            this.sparkle.setEatingCooldownTicks(-this.EATING_COOLDOWN.sample(this.sparkle.getRandom()));
        }
    }

    @Override
    public void tick() {
        BlockPos pos = this.getClusterPos();
        if (pos != null) {
            this.sparkle.getNavigation().moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 1);
            this.sparkle.getLookControl().setLookAt(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            if (this.eatingTicks % 7 == 0) {
                this.sparkle.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
            }
            if (this.sparkle.blockPosition().closerThan(pos, 2.0D)) {
                if (this.eatingTicks < 60) {
                    this.eatingTicks++;
                }
            }
        }
    }

    public BlockPos getClusterPos() {
        int radius = 3;
        BlockPos sparklePos = this.sparkle.blockPosition();
        List<BlockPos> list = Lists.newArrayList();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos pos = new BlockPos(sparklePos.getX() + x, sparklePos.getY() + y, sparklePos.getZ() + z);
                    BlockState blockState = this.sparkle.level.getBlockState(pos);
                    if (blockState.getBlock() instanceof PollinatedClusterBlock && !blockState.getValue(PollinatedClusterBlock.POLLINATED)) {
                        list.add(pos);
                    }
                }
            }
        }

        if (list.isEmpty()) return null;

        return list.get(0);
    }

}
