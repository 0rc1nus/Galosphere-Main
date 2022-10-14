package net.orcinus.galosphere.mixin.client;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.mixin.access.GuiAccessor;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow private int screenWidth;
    @Shadow private int screenHeight;
    @Shadow @Final private Minecraft minecraft;
    private static final ResourceLocation GALOSPHERE_ICONS = Galosphere.id("textures/gui/galosphere_icons.png");

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void G$render(PoseStack poseStack, float f, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        int left = mc.getWindow().getGuiScaledWidth() / 2 - 91;
        int top = mc.getWindow().getGuiScaledHeight() - 39;

        if (player.hasEffect(GMobEffects.ILLUSIVE)) {
            ci.cancel();
            poseStack.pushPose();
            poseStack.translate(0, 0, 0.01);

            float currentHealth = player.getMaxHealth();
            int ticks = mc.gui.getGuiTicks();
            Random rand = new Random();
            rand.setSeed(ticks * 312871L);

            float absorb = Mth.ceil(player.getAbsorptionAmount());
            long healthBlinkTime = ((GuiAccessor)mc.gui).getHealthBlinkTime();
            boolean highlight = healthBlinkTime > (long) ticks && (healthBlinkTime - (long) ticks) / 3L % 2L == 1L;

            int healthRows = (int) player.getMaxHealth();
            int rowHeight = Math.max(10 - (healthRows - 2), 3);

            final int TOP = 17 * (mc.level.getLevelData().isHardcore() ? 1 : 0);
            float absorbRemaining = absorb;
            RenderSystem.setShaderTexture(0, GALOSPHERE_ICONS);
            RenderSystem.enableBlend();

            for (int i = 20; i >= 0; --i) {
                int row = Mth.ceil((float) (i + 1) / 10.0F);
                int x = left + i % 10 * 8;
                int y = top - row * rowHeight - 1;

                if (currentHealth <= 4) y += rand.nextInt(2);
                if (highlight) {
                    int displayHealth = ((GuiAccessor)mc.gui).getDisplayHealth();
                    if (i * 2 + 1 < displayHealth) {
                        mc.gui.blit(poseStack, x, y, 54, TOP, 9, 9);
                    } else if (i * 2 + 1 == displayHealth) {
                        mc.gui.blit(poseStack, x, y, 63, TOP, 9, 9);
                    }
                }
                if (absorbRemaining > 0.0F) {
                    if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                        mc.gui.blit(poseStack, x, y, 63, TOP, 9, 9);
                        absorbRemaining -= 1.0F;
                    } else {
                        mc.gui.blit(poseStack, x, y, 54, TOP, 9, 9);
                        absorbRemaining -= 2.0F;
                    }
                } else {
                    //Starts from the pixel
                    if (i * 2 + 1 < currentHealth) {
                        mc.gui.blit(poseStack, x, y, 36, TOP, 9, 12);
                    }
                }
            }

            RenderSystem.disableBlend();
            poseStack.popPose();
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void G$renderPlayerHealth(PoseStack poseStack, float f, CallbackInfo ci) {
        if (!this.minecraft.options.hideGui && this.minecraft.gameMode.canHurtPlayer() && this.minecraft.getCameraEntity() instanceof Player) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, GALOSPHERE_ICONS);
            RenderSystem.enableBlend();
            this.renderGoldenAirSupply(poseStack);
            RenderSystem.disableBlend();
        }
    }

    private void renderGoldenAirSupply(PoseStack poseStack) {
        Player player = ((GuiAccessor)this).callGetCameraPlayer();
        if (player == null) return;

        Gui $this = (Gui) (Object) this;
        int n = this.screenWidth / 2 + 91;
        int o = this.screenHeight - 39;
        int t = o - 20;

        float y = ((GoldenBreath) player).getMaxGoldenAirSupply();
        float z = Math.min(((GoldenBreath) player).getGoldenAirSupply(), y);
        if (z < y && player.isEyeInFluid(FluidTags.WATER)) {
            int ab = Mth.ceil((double)(z - 2) * 4.0 / (double)y);
            int ac = Mth.ceil((double)z * 4.0 / (double)y) - ab;
            for (int ad = 0; ad < ab + ac; ++ad) {
                if (ad < ab) {
                    $this.blit(poseStack, n - ad * 8 - 9, t, 16, 18, 9, 9);
                    continue;
                }
                $this.blit(poseStack, n - ad * 8 - 9, t, 25, 18, 9, 9);
            }
        }
    }
}
