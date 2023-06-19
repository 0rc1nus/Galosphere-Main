package net.orcinus.galosphere.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.entities.SpectatorVision;
import net.orcinus.galosphere.mixin.access.GuiAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow private int screenWidth;
    @Shadow private int screenHeight;
    @Shadow @Final private Minecraft minecraft;
    private static final ResourceLocation GALOSPHERE_ICONS = new ResourceLocation(Galosphere.MODID, "textures/gui/galosphere_icons.png");

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void G$renderSpectatorVision(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        if (this.minecraft.getCameraEntity() instanceof Spectatable spectatable && spectatable.getManipulatorUUID() != null && this.minecraft.level != null) {
            Player player = this.minecraft.level.getPlayerByUUID(spectatable.getManipulatorUUID());
            if (player == this.minecraft.player) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void G$renderPlayerHealth(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        if (!this.minecraft.options.hideGui && this.minecraft.gameMode.canHurtPlayer() && this.minecraft.getCameraEntity() instanceof Player) {
            RenderSystem.enableBlend();
            this.renderGoldenAirSupply(guiGraphics);
            RenderSystem.disableBlend();
        }
    }

    private void renderGoldenAirSupply(GuiGraphics guiGraphics) {
        Player player = ((GuiAccessor)this).callGetCameraPlayer();
        if (player == null) return;

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
                    guiGraphics.blit(GALOSPHERE_ICONS, n - ad * 8 - 9, t, 16, 18, 9, 9);
                    continue;
                }
                guiGraphics.blit(GALOSPHERE_ICONS, n - ad * 8 - 9, t, 25, 18, 9, 9);
            }
        }
    }
}
