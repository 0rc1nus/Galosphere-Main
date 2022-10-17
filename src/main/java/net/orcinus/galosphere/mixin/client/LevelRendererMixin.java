package net.orcinus.galosphere.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private RenderBuffers renderBuffers;
    @Shadow private int renderedEntities;

    @Shadow protected abstract void renderEntity(Entity entity, double d, double e, double f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource);

    @Shadow private @Nullable PostChain entityEffect;
    @Unique
    private boolean bl5;

    /*
        This is why I prefer forge more than fabric
    */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V"), method = "renderLevel", locals = LocalCapture.CAPTURE_FAILHARD)
    private void afterRenderEntities(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        if (!this.shouldRenderPlayer()) {
            return;
        }
        Entity entity = this.minecraft.player;
        MultiBufferSource vertex;

        MultiBufferSource.BufferSource bufferSource = this.renderBuffers.bufferSource();
        Vec3 vec3 = camera.getPosition();
        double x = vec3.x();
        double y = vec3.y();
        double z = vec3.z();

        this.renderedEntities++;

        if (entity.tickCount == 0) {
            entity.xOld = entity.getX();
            entity.yOld = entity.getY();
            entity.zOld = entity.getZ();
        }

        if (this.shouldRenderPlayer() && this.minecraft.shouldEntityAppearGlowing(entity)) {
            this.bl5 = true;

            OutlineBufferSource outlineVertices = this.renderBuffers.outlineBufferSource();
            vertex = outlineVertices;
            int teamColor = entity.getTeamColor();
            int r = teamColor >> 16 & 0xFF;
            int g = teamColor >> 8  & 0xFF;
            int b = teamColor       & 0xFF;
            outlineVertices.setColor(r, g, b, 0xFF);
        } else {
            vertex = bufferSource;
        }

        this.renderEntity(entity, x, y, z, f, poseStack, vertex);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V"), method = "renderLevel")
    private void GE$renderLevel(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        if (!this.shouldRenderPlayer()) {
            return;
        }
        if (this.bl5) {
            this.entityEffect.process(f);
            this.minecraft.getMainRenderTarget().bindWrite(false);
        }
        this.bl5 = false;
    }

    private boolean shouldRenderPlayer() {
        LocalPlayer player = this.minecraft.player;
        return player != null && player.isScoping() && SpectreBoundSpyglass.canUseSpectreBoundSpyglass(player.getUseItem(), player) && ((SpectreBoundSpyglass)player).isUsingSpectreBoundedSpyglass();
    }
}