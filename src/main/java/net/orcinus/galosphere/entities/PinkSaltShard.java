package net.orcinus.galosphere.entities;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;

public class PinkSaltShard extends AbstractArrow {

    public PinkSaltShard(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PinkSaltShard(LivingEntity livingEntity, Level level) {
        super(GEntityTypes.PINK_SALT_SHARD, level);
        this.setOwner(livingEntity);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        boolean flag = entity == this.getOwner() || this.getOwner() != null && this.getOwner().isAlliedTo(entity);
        if (!flag) {
            super.onHitEntity(entityHitResult);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 3) {
            ParticleOptions particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, GBlocks.PINK_SALT.defaultBlockState());
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleOptions, this.getX(), this.getY() + 1.0D, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }
}
