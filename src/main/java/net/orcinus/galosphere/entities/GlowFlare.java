package net.orcinus.galosphere.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.galosphere.blocks.GlowInkClumpsBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

public class GlowFlare extends ThrowableLaunchedProjectile {

    public GlowFlare(EntityType<? extends GlowFlare> type, Level world) {
        super(type, world);
    }

    public GlowFlare(Level world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        super(world, stack, entity, x, y, z, shotAtAngle);
    }

    public GlowFlare(Level world, double x, double y, double z) {
        super(GEntityTypes.GLOW_FLARE.get(), world);
        this.setPos(x, y, z);
        this.entityData.set(THROWN, true);
    }

    public GlowFlare(Level level, @Nullable Entity entity, ItemStack itemStack) {
        super(GEntityTypes.GLOW_FLARE.get(), level);
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.entityData.set(DATA_ID_FIREWORKS_ITEM, itemStack.copy());
        }
        this.entityData.set(THROWN, true);
        this.setOwner(entity);
    }

    @Override
    public void tick() {
        super.tick();
        Level world = this.level();
        if (world.isClientSide && this.life % 2 < 2) {
            world.addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, -this.getDeltaMovement().y * 0.5D, this.random.nextGaussian() * 0.05D);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return GItems.GLOW_FLARE.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        Level world = this.level();
        if (!world.isClientSide()) {
            BlockPos hitPos = result.getBlockPos();
            BlockPos placePos = hitPos.relative(result.getDirection());
            if (world.getBlockState(hitPos).isSolidRender(world, hitPos) && ((world.getBlockState(placePos).canBeReplaced() && !world.getFluidState(placePos).is(FluidTags.LAVA)) || world.isStateAtPosition(placePos, DripstoneUtils::isEmptyOrWater))) {
                world.setBlock(placePos, GBlocks.GLOW_INK_CLUMPS.get().defaultBlockState().setValue(GlowInkClumpsBlock.getFaceProperty(result.getDirection().getOpposite()), true).setValue(BlockStateProperties.AGE_15, 15).setValue(BlockStateProperties.WATERLOGGED, world.getBlockState(placePos).is(Blocks.WATER)), 2);
            }
            this.playSound(GSoundEvents.GLOW_FLARE_SPREAD.get(), 1.0F, 1.0F);
            this.discard();
        }
    }

}