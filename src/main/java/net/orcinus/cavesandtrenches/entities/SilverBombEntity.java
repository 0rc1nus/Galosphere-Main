package net.orcinus.cavesandtrenches.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.orcinus.cavesandtrenches.init.CTEntityTypes;
import net.orcinus.cavesandtrenches.init.CTItems;
import net.orcinus.cavesandtrenches.init.CTParticleTypes;

public class SilverBombEntity extends ThrowableItemProjectile {

    public SilverBombEntity(EntityType<? extends SilverBombEntity> entity, Level world) {
        super(entity, world);
    }

    public SilverBombEntity(Level world, LivingEntity entity, ItemStack stack) {
        super(CTEntityTypes.SIVLER_BOMB.get(), entity, world);
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected Item getDefaultItem() {
        return CTItems.SILVER_BOMB.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemStack itemstack = this.getItemRaw();
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle((itemstack.isEmpty() ? CTParticleTypes.SILVER_BOMB.get() : new ItemParticleOption(ParticleTypes.ITEM, itemstack)), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        if (!this.level.isClientSide() && !this.isInWater()) {
            this.level.explode(this, null, new ExplosionDamageCalculator() {
                @Override
                public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float p_46098_) {
                    BlockState blockstate = world.getBlockState(pos);
                    return blockstate.getBlock().defaultDestroyTime() < 3.0D;
                }
            }, this.getX(), this.getY(), this.getZ(), 3.0F, false, Explosion.BlockInteraction.BREAK);
            this.discard();
        }
    }
}
