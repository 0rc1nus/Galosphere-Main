package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.init.GBlocks;

import java.util.Random;

public class LumiereBlock extends AmethystBlock {
    private final boolean charged;

    public LumiereBlock(boolean charged, Properties properties) {
        super(properties);
        this.charged = charged;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return this.charged ? 6 : 0;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        BlockPos abovePos = pos.above();
        if (this.charged && world.getBlockState(abovePos).is(Blocks.GRANITE)) {
            world.playSound(null, pos, SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.BLOCKS, 0.5F, 1.4F);
            world.setBlockAndUpdate(abovePos, Blocks.RED_SAND.defaultBlockState());
            if (world.getBlockState(abovePos).isCollisionShapeFullBlock(world, abovePos)) {
                world.levelEvent(2009, abovePos, 0);
            }
            world.setBlockAndUpdate(pos, GBlocks.LUMIERE_BLOCK.get().defaultBlockState());
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos dirPos = pos.below().relative(direction);
                if (world.getBlockState(dirPos).isCollisionShapeFullBlock(world, dirPos)) {
                    world.levelEvent(2009, dirPos, 0);
                }
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState pOldState, boolean pIsMoving) {
        if (this.charged) {
            world.scheduleTick(pos, this, 120);
        }
    }
}
