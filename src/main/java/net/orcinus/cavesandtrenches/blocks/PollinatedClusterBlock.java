package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class PollinatedClusterBlock extends AmethystClusterBlock {

    public PollinatedClusterBlock(Properties properties) {
        super(7, 3, properties);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        Direction direction = state.getValue(FACING);
//        if (random.nextInt(5) == 0) {
//            world.addParticle(ParticleTyapes.END_ROD, d0 + (double) direction.getStepX() * d3, d1 + (double) direction.getStepY() * d3, d2 + (double) direction.getStepZ() * d3, random.nextGaussian() * 0.005D, random.nextGaussian() * 0.005D, random.nextGaussian() * 0.005D);
//        }
        double velX = random.nextBoolean() ? -(random.nextFloat() / 10.0F) : random.nextFloat() / 10.0F;
        double velY = random.nextBoolean() ? -(random.nextFloat() / 10.0F) : random.nextFloat() / 10.0F;
        double velZ = random.nextBoolean() ? -(random.nextFloat() / 10.0F) : random.nextFloat() / 10.0F;
        double x = pos.getX() + 0.5D ;
        double y = pos.getY() + 0.9D ;
        double z = pos.getZ() + 0.5D ;
        if (random.nextInt(5) == 0) {
            world.addParticle(ParticleTypes.END_ROD, x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ(), velX, velY, velZ);
        }
    }
}
