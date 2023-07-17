package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.orcinus.galosphere.blocks.GlowInkClumpsBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.mixin.access.FireworkRocketEntityAccessor;

public class GlowFlare extends FireworkRocketEntity {

    public GlowFlare(EntityType<? extends GlowFlare> type, Level world) {
        super(type, world);
    }

    public GlowFlare(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    public GlowFlare(Level level, @org.jetbrains.annotations.Nullable Entity entity, double d, double e, double f, ItemStack itemStack) {
        this(level, d, e, f, itemStack);
        this.setOwner(entity);
    }

    public GlowFlare(Level level, double d, double e, double f, ItemStack itemStack) {
        super(GEntityTypes.GLOW_FLARE, level);
        ((FireworkRocketEntityAccessor)this).setLife(0);
        this.setPos(d, e, f);
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.entityData.set(FireworkRocketEntityAccessor.getDATA_ID_FIREWORKS_ITEM(), itemStack.copy());
        }
        this.setDeltaMovement(this.random.triangle(0.0, 0.002297), 0.05, this.random.triangle(0.0, 0.002297));
        ((FireworkRocketEntityAccessor) this).setLifeTime(200);
    }

    public GlowFlare(Level level, ItemStack itemStack, double d, double e, double f, boolean bl) {
        this(level, d, e, f, itemStack);
        this.entityData.set(FireworkRocketEntityAccessor.getDATA_SHOT_AT_ANGLE(), bl);
    }

    @Override
    public void tick() {
        super.tick();
        Level world = this.level();
        if (world.isClientSide && ((FireworkRocketEntityAccessor)this).getLife() % 2 < 2) {
            world.addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, -this.getDeltaMovement().y * 0.5D, this.random.nextGaussian() * 0.05D);
        }
    }

    @Override
    public EntityType<?> getType() {
        return GEntityTypes.GLOW_FLARE;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        Level world = this.level();
        if (!world.isClientSide()) {
            BlockPos hitPos = result.getBlockPos();
            BlockPos placePos = hitPos.relative(result.getDirection());
            if (world.getBlockState(hitPos).isSolidRender(world, hitPos) && ((world.getBlockState(placePos).canBeReplaced() && !world.getFluidState(placePos).is(FluidTags.LAVA)) || world.isStateAtPosition(placePos, DripstoneUtils::isEmptyOrWater))) {
                world.setBlock(placePos, GBlocks.GLOW_INK_CLUMPS.defaultBlockState().setValue(GlowInkClumpsBlock.getFaceProperty(result.getDirection().getOpposite()), true).setValue(BlockStateProperties.AGE_15, 15).setValue(BlockStateProperties.WATERLOGGED, world.getBlockState(placePos).is(Blocks.WATER)), 2);
            }
            this.playSound(GSoundEvents.GLOW_FLARE_SPREAD, 1.0F, 1.0F);
            this.discard();
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(GItems.GLOW_FLARE);
    }

}
