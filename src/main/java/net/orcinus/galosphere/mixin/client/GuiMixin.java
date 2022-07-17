package net.orcinus.galosphere.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.mixin.access.GuiAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Gui.class)
public class GuiMixin {
    private static final ResourceLocation GALOSPHERE_ICONS = new ResourceLocation(Galosphere.MODID, "textures/gui/galosphere_icons.png");

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

}
