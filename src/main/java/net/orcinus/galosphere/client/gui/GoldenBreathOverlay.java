package net.orcinus.galosphere.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.GoldenBreath;

@OnlyIn(Dist.CLIENT)
public class GoldenBreathOverlay {
    private static final ResourceLocation GALOSPHERE_ICONS = Galosphere.id("textures/gui/galosphere_icons.png");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPostRender(RenderGuiOverlayEvent.Post event) {
        if (event.isCanceled()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (player == null) return;

        if (event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_HEALTH.id())) {
            if (!Minecraft.getInstance().options.hideGui && Minecraft.getInstance().gameMode.canHurtPlayer() && Minecraft.getInstance().getCameraEntity() instanceof Player) {
                RenderSystem.enableBlend();
                this.renderGoldenAirSupply(guiGraphics, mc.player, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
                RenderSystem.disableBlend();
            }
        }
    }

    private void renderGoldenAirSupply(GuiGraphics guiGraphics, Player player, int width, int height) {
        if (player == null) return;

        int n = width / 2 + 91;
        int o = height - 39;
        int t = o - 20;

        float y = ((GoldenBreath) player).getMaxGoldenAirSupply();
        float z = Math.min(((GoldenBreath) player).getGoldenAirSupply(), y);
        if (z < y && player.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
            int ab = Mth.ceil((double)(z - 2) * 4.0 / (double)y);
            int ac = Mth.ceil((double)z * 4.0 / (double)y) - ab;
            for (int ad = 0; ad < ab + ac; ++ad) {
                if (ad < ab) {
                    guiGraphics.blit(GALOSPHERE_ICONS, n - ad * 8 - 9, t, 16, 18, 9, 9);
                    continue;
                }
                guiGraphics.blit(GALOSPHERE_ICONS, n - ad * 8 - 9, t, 25, 18, 9, 9);
            }
        }
    }
}
