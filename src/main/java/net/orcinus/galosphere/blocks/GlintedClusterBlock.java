package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GlintedClusterBlock extends AmethystClusterBlock {
    private final Supplier<? extends SimpleParticleType> particleType;

    public GlintedClusterBlock(Supplier<? extends SimpleParticleType> particleType, Properties properties) {
        super(7, 3, properties);
        this.particleType = particleType;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        double i = pos.getX();
        double j = pos.getY();
        double k = pos.getZ();
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        double velX = random.nextBoolean() ? -(random.nextFloat() / 10.0F) : random.nextFloat() / 10.0F;
        double velY = random.nextBoolean() ? -(random.nextFloat() / 10.0F) : random.nextFloat() / 10.0F;
        double velZ = random.nextBoolean() ? -(random.nextFloat() / 10.0F) : random.nextFloat() / 10.0F;
        double x = i + 0.5D;
        double y = j + 0.9D;
        double z = k + 0.5D;
        mut.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
        BlockState blockstate = world.getBlockState(mut);
        if (!blockstate.isCollisionShapeFullBlock(world, mut)) {
            world.addParticle(this.particleType.get(), (double) mut.getX() + direction.getStepX() + random.nextDouble(), (double) mut.getY() + direction.getStepY() + random.nextDouble(), (double) mut.getZ() + direction.getStepZ() + random.nextDouble(), velX, velY, velZ);
        }
        if (random.nextInt(5) == 0) {
            world.addParticle(this.particleType.get(), x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ(), velX, velY, velZ);
        }
    }
}
