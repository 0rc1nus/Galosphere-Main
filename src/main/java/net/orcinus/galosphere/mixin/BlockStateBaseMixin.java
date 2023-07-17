package net.orcinus.galosphere.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GMobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {

    @Inject(at = @At("RETURN"), method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true)
    private void G$getCollisionShape(BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext, CallbackInfoReturnable<VoxelShape> cir) {
        if (collisionContext instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() instanceof LivingEntity livingEntity) {
            boolean above = livingEntity.getY() > blockPos.getY() + cir.getReturnValue().max(Direction.Axis.Y) - (livingEntity.onGround() ? 0.5F : 0.001F);
            boolean flag = !above || livingEntity.isShiftKeyDown();
            if (livingEntity.hasEffect(GMobEffects.TRANSIT) && flag && !blockGetter.getBlockState(blockPos).is(GBlockTags.OMIT_TRANSIT)) {
                cir.setReturnValue(Shapes.empty());
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "entityInside", cancellable = true)
    private void G$entityInside(Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(GMobEffects.TRANSIT) && !level.getBlockState(blockPos).is(GBlockTags.OMIT_TRANSIT)) {
            ci.cancel();
            if (level instanceof ServerLevel serverLevel) {
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                RandomSource randomSource = level.getRandom();
                if (bl && randomSource.nextBoolean()) {
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(blockPos)), entity.getX(), blockPos.getY() + 1, entity.getZ(), 1, Mth.randomBetween(randomSource, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.randomBetween(randomSource, -1.0f, 1.0f) * 0.083333336f, 0);
                }
            }
        }
    }

}
