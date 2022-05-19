package net.orcinus.galosphere.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.mixin.GuiAccessor;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class IllusiveOverlay {
    private static final ResourceLocation GALOSPHERE_ICONS = new ResourceLocation(Galosphere.MODID, "textures/gui/galosphere_icons.png");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPostRender(RenderGameOverlayEvent.Pre event) {
        if (event.isCanceled()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        PoseStack matrixStack = event.getMatrixStack();
        if (player == null) return;

        int left = mc.getWindow().getGuiScaledWidth() / 2 - 91;
        int top = mc.getWindow().getGuiScaledHeight() - ((ForgeIngameGui)mc.gui).left_height;

        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && player.hasEffect(GMobEffects.ILLUSIVE.get())) {
            event.setCanceled(true);
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 0.01);

            float currentHealth = player.getMaxHealth();
            int ticks = mc.gui.getGuiTicks();
            Random rand = new Random();
            rand.setSeed(ticks * 312871L);

            float absorb = Mth.ceil(player.getAbsorptionAmount());
            boolean highlight = ((GuiAccessor)mc.gui).getHealthBlinkTime() > (long) ticks && (((GuiAccessor)mc.gui).getHealthBlinkTime() - (long) ticks) / 3L % 2L == 1L;

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
                    if (i * 2 + 1 < ((GuiAccessor)mc.gui).getDisplayHealth()) {
                        mc.gui.blit(matrixStack, x, y, 54, TOP, 9, 9);
                    } else if (i * 2 + 1 == ((GuiAccessor)mc.gui).getDisplayHealth()) {
                        mc.gui.blit(matrixStack, x, y, 63, TOP, 9, 9);
                    }
                }
                if (absorbRemaining > 0.0F) {
                    if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                        mc.gui.blit(matrixStack, x, y, 63, TOP, 9, 9);
                        absorbRemaining -= 1.0F;
                    } else {
                        mc.gui.blit(matrixStack, x, y, 54, TOP, 9, 9);
                        absorbRemaining -= 2.0F;
                    }
                } else {
                    //Starts from the pixel
                    if (i * 2 + 1 < currentHealth) {
                        mc.gui.blit(matrixStack, x, y, 36, TOP, 9, 12);
                    }
                }
            }

            RenderSystem.disableBlend();
            matrixStack.popPose();
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        }
    }

}
