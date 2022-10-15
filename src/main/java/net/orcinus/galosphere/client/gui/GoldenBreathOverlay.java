package net.orcinus.galosphere.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.GoldenBreath;

@OnlyIn(Dist.CLIENT)
public class GoldenBreathOverlay {
    private static final ResourceLocation GALOSPHERE_ICONS = new ResourceLocation(Galosphere.MODID, "textures/gui/galosphere_icons.png");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPostRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || event.isCanceled()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        PoseStack matrixStack = event.getMatrixStack();
        if (player == null) return;


        if (!Minecraft.getInstance().options.hideGui && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer() && Minecraft.getInstance().getCameraEntity() instanceof Player) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, GALOSPHERE_ICONS);
            RenderSystem.enableBlend();
            this.renderGoldenAirSupply(matrixStack, mc.gui, mc.player, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
            RenderSystem.disableBlend();
        }
    }

    private void renderGoldenAirSupply(PoseStack poseStack, Gui gui, Player player, int width, int height) {
        if (player == null) return;

        int n = width / 2 + 91;
        int o = height - 39;
        int t = o - 20;

        float y = ((GoldenBreath) player).getMaxGoldenAirSupply();
        float z = Math.min(((GoldenBreath) player).getGoldenAirSupply(), y);
        if (z < y && player.isEyeInFluid(FluidTags.WATER)) {
            int ab = Mth.ceil((double)(z - 2) * 4.0 / (double)y);
            int ac = Mth.ceil((double)z * 4.0 / (double)y) - ab;
            for (int ad = 0; ad < ab + ac; ++ad) {
                if (ad < ab) {
                    gui.blit(poseStack, n - ad * 8 - 9, t, 16, 18, 9, 9);
                    continue;
                }
                gui.blit(poseStack, n - ad * 8 - 9, t, 25, 18, 9, 9);
            }
        }
    }
}