package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.orcinus.galosphere.init.GMobEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Unique
    private static float distance = 24.0F;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", shift = At.Shift.BEFORE), method = "setupFog", locals = LocalCapture.CAPTURE_FAILHARD)
    private static void G$setupFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci, FogType fogType, Entity entity, FogRenderer.FogData fogData) {
        Entity cameraEntity = camera.getEntity();
        if (renderShadowPhase(cameraEntity)) {
            if (entity instanceof LivingEntity livingEntity) {
                boolean lock = getViewBlockingState(livingEntity) != null;
                if (distance == f && !lock) {
                    return;
                }
                if (distance > 12 && lock) {
                    distance-=2.0F;
                }
                if (!lock && distance < f) {
                    distance+=1.0F;
                }
                float h = distance;
                fogData.start = fogData.mode == FogRenderer.FogMode.FOG_SKY ? 0.0f : h * 0.75f;
                fogData.end = h;
            }
        }
    }

    @Unique
    private static boolean renderShadowPhase(Entity entity) {
        return entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(GMobEffects.ASTRAL.get());
    }

    @Unique
    @Nullable
    private static BlockState getViewBlockingState(LivingEntity player) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 8; ++i) {
            double d = player.getX() + (double)(((float)((i) % 2) - 0.5f) * player.getBbWidth() * 0.8f);
            double e = player.getEyeY() + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
            double f = player.getZ() + (double)(((float)((i >> 2) % 2) - 0.5f) * player.getBbWidth() * 0.8f);
            mutableBlockPos.set(d, e, f);
            BlockState blockState = player.level().getBlockState(mutableBlockPos);
            if (blockState.getRenderShape() == RenderShape.INVISIBLE || !blockState.isViewBlocking(player.level(), mutableBlockPos)) continue;
            return blockState;
        }
        return null;
    }

}
