package net.orcinus.galosphere.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.entities.SparkleEntity;
import net.orcinus.galosphere.init.GSoundEvents;

public class BiteClusterGoal extends MoveToBlockGoal {
    private final SparkleEntity sparkle;
    protected int ticksWaited;

    public BiteClusterGoal(SparkleEntity sparkle) {
        super(sparkle, 1.2F, 12, 1);
        this.sparkle = sparkle;
    }

    @Override
    public double acceptedDistance() {
        return 2.0D;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos blockPos) {
        BlockState state = world.getBlockState(blockPos);
        return state.getBlock() instanceof PollinatedClusterBlock && !state.getValue(PollinatedClusterBlock.POLLINATED);
    }

    @Override
    public void tick() {
        this.sparkle.getLookControl().setLookAt(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ());
        if (this.isReachedTarget()) {
            if (this.ticksWaited % 4 == 0) {
                this.sparkle.playSound(GSoundEvents.SPARKLE_BITE.get(), 1.0F, 1.0F);
            }
            if (this.ticksWaited >= 40) {
                this.onReachedTarget();
            } else {
                this.ticksWaited++;
            }
        } else if (!this.isReachedTarget() && this.sparkle.getRandom().nextFloat() < 0.05F) {
            this.sparkle.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
        }

        super.tick();
    }

    protected void onReachedTarget() {
        if (ForgeEventFactory.getMobGriefingEvent(this.sparkle.level, this.sparkle)) {
            BlockState state = this.sparkle.level.getBlockState(this.blockPos);
            if (state.getBlock() instanceof PollinatedClusterBlock) {
                this.sparkle.playSound(SoundEvents.SMALL_AMETHYST_BUD_BREAK, 1.0F, 1.0F);
                this.sparkle.level.setBlock(this.blockPos, state.setValue(PollinatedClusterBlock.POLLINATED, true), 2);
            }
        }
    }

    @Override
    public boolean canUse() {
        return !this.sparkle.isSleeping() && super.canUse();
    }

    @Override
    public void start() {
        this.ticksWaited = 0;
        super.start();
    }

}
