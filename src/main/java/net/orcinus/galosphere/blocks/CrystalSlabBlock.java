package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CrystalSlabBlock extends SlabBlock {

    public CrystalSlabBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        if (!world.isClientSide()) {
            BlockPos blockpos = hitResult.getBlockPos();
            world.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
            world.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
        }
    }
}