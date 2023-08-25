package net.orcinus.galosphere.events;

import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.init.GMobEffects;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class CameraEvents {

    @SubscribeEvent
    public void computeFogColor(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        if (renderShadowPhase(camera.getEntity()) && getViewBlockingState((LivingEntity) camera.getEntity()) != null) {
            event.setRed(0.0F);
            event.setGreen(0.0F);
            event.setBlue(0.0F);
        }
    }

    @SubscribeEvent
    public void renderBlockScreen(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.BLOCK && event.getPlayer().hasEffect(GMobEffects.ASTRAL.get())) {
            event.setCanceled(true);
        }
    }

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

    private static boolean renderShadowPhase(Entity entity) {
        return entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(GMobEffects.ASTRAL.get());
    }

}
