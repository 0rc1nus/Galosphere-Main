package net.orcinus.galosphere.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.entities.SpectatorVision;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "renderHandsWithItems", cancellable = true)
    private void G$renderHandsWithItems(float f, PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, LocalPlayer localPlayer, int i, CallbackInfo ci) {
        if (this.minecraft.getCameraEntity() instanceof Spectatable spectatable && spectatable.getManipulatorUUID() != null && this.minecraft.level != null) {
            Player player = this.minecraft.level.getPlayerByUUID(spectatable.getManipulatorUUID());
            if (player == this.minecraft.player) {
                ci.cancel();
            }
        }
    }

}