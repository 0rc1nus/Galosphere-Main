package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.orcinus.galosphere.blocks.GlowInkClumpsBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GParticleTypes;

import javax.annotation.Nullable;

public class GlowFlareEntity extends FireworkRocketEntity {

    public GlowFlareEntity(EntityType<? extends GlowFlareEntity> type, Level world) {
        super(type, world);
    }

    public GlowFlareEntity(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    public GlowFlareEntity(Level level, @Nullable Entity entity, double d, double e, double f, ItemStack itemStack) {
        this(level, d, e, f, itemStack);
        this.setOwner(entity);
    }

    public GlowFlareEntity(Level level, double d, double e, double f, ItemStack itemStack) {
        super(GEntityTypes.GLOW_FLARE.get(), level);
        this.life = 0;
        this.setPos(d, e, f);
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.entityData.set(FireworkRocketEntity.DATA_ID_FIREWORKS_ITEM, itemStack.copy());
        }
        this.setDeltaMovement(this.random.triangle(0.0, 0.002297), 0.05, this.random.triangle(0.0, 0.002297));
        this.lifetime = 200;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide && this.life % 2 < 2) {
            this.level.addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, -this.getDeltaMovement().y * 0.5D, this.random.nextGaussian() * 0.05D);
        }
    }

    @Override
    public EntityType<?> getType() {
        return GEntityTypes.GLOW_FLARE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level.isClientSide()) {
            BlockPos hitPos = result.getBlockPos();
            BlockPos placePos = hitPos.relative(result.getDirection());
            Material material = this.level.getBlockState(placePos).getMaterial();
            if (this.level.getBlockState(hitPos).isSolidRender(this.level, hitPos) && ((material != Material.LAVA && material.isReplaceable()) || this.level.isStateAtPosition(placePos, DripstoneUtils::isEmptyOrWater))) {
                this.level.setBlock(placePos, GBlocks.GLOW_INK_CLUMPS.get().defaultBlockState().setValue(GlowInkClumpsBlock.getFaceProperty(result.getDirection().getOpposite()), true).setValue(BlockStateProperties.AGE_15, 15).setValue(BlockStateProperties.WATERLOGGED, this.level.getBlockState(placePos).is(Blocks.WATER)), 2);
            }
            this.playSound(SoundEvents.SCULK_BLOCK_CHARGE, 1.0F, 1.0F);
            ((ServerLevel)this.level).sendParticles(GParticleTypes.FAY_DUST.get(), this.getX(), this.getY(0.0625D), this.getZ() , 100, 0.0D , 0.0D , 0.0D, 0.2);
            this.discard();
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(GItems.GLOW_FLARE.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
