package net.orcinus.galosphere.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;

@Environment(EnvType.CLIENT)
public class IllusiveOverlay {
    private static final ResourceLocation GALOSPHERE_ICONS = Galosphere.id("textures/gui/galosphere_icons.png");

//    @SubscribeEvent(priority = EventPriority.LOW)
//    public void onPostRender(RenderGameOverlayEvent.Pre event) {
//        if (event.isCanceled()) return;
//
//        Minecraft mc = Minecraft.getInstance();
//        Player player = mc.player;
//        PoseStack matrixStack = event.getPoseStack();
//        if (player == null) return;
//
//        int left = mc.getWindow().getGuiScaledWidth() / 2 - 91;
//        int top = mc.getWindow().getGuiScaledHeight() - ((ForgeIngameGui)mc.gui).left_height;
//
//        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && player.hasEffect(GMobEffects.ILLUSIVE.get())) {
//            event.setCanceled(true);
//            matrixStack.pushPose();
//            matrixStack.translate(0, 0, 0.01);
//
//            float currentHealth = player.getMaxHealth();
//            int ticks = mc.gui.getGuiTicks();
//            Random rand = new Random();
//            rand.setSeed(ticks * 312871L);
//
//            float absorb = Mth.ceil(player.getAbsorptionAmount());
//            boolean highlight = mc.gui.healthBlinkTime > (long) ticks && (mc.gui.healthBlinkTime - (long) ticks) / 3L % 2L == 1L;
//
//            int healthRows = (int) player.getMaxHealth();
//            int rowHeight = Math.max(10 - (healthRows - 2), 3);
//
//            final int TOP = 17 * (mc.level.getLevelData().isHardcore() ? 1 : 0);
//            float absorbRemaining = absorb;
//            RenderSystem.setShaderTexture(0, GALOSPHERE_ICONS);
//            RenderSystem.enableBlend();
//
//            for (int i = 20; i >= 0; --i) {
//                int row = Mth.ceil((float) (i + 1) / 10.0F);
//                int x = left + i % 10 * 8;
//                int y = top - row * rowHeight - 1;
//
//                if (currentHealth <= 4) y += rand.nextInt(2);
//                if (highlight) {
//                    if (i * 2 + 1 < mc.gui.displayHealth) {
//                        mc.gui.blit(matrixStack, x, y, 54, TOP, 9, 9);
//                    } else if (i * 2 + 1 == mc.gui.displayHealth) {
//                        mc.gui.blit(matrixStack, x, y, 63, TOP, 9, 9);
//                    }
//                }
//                if (absorbRemaining > 0.0F) {
//                    if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
//                        mc.gui.blit(matrixStack, x, y, 63, TOP, 9, 9);
//                        absorbRemaining -= 1.0F;
//                    } else {
//                        mc.gui.blit(matrixStack, x, y, 54, TOP, 9, 9);
//                        absorbRemaining -= 2.0F;
//                    }
//                } else {
//                    //Starts from the pixel
//                    if (i * 2 + 1 < currentHealth) {
//                        mc.gui.blit(matrixStack, x, y, 36, TOP, 9, 12);
//                    }
//                }
//            }
//
//            RenderSystem.disableBlend();
//            matrixStack.popPose();
//            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
//        }
//    }

}
