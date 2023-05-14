package net.orcinus.galosphere.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.api.Spectatable;

@OnlyIn(Dist.CLIENT)
public class SpectatorVisionOverlay {

    @SubscribeEvent
    public void onHandRender(RenderHandEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getCameraEntity() instanceof Spectatable spectatable && spectatable.getManipulatorUUID() != null && minecraft.level != null) {
            Player player = minecraft.level.getPlayerByUUID(spectatable.getManipulatorUUID());
            if (player == minecraft.player) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreRender(RenderGuiOverlayEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getCameraEntity() instanceof Spectatable spectatable && spectatable.getManipulatorUUID() != null && minecraft.level != null) {
            Player player = minecraft.level.getPlayerByUUID(spectatable.getManipulatorUUID());
            if (player == minecraft.player && event.getOverlay().equals(VanillaGuiOverlay.EXPERIENCE_BAR.type())) {
                event.setCanceled(true);
            }
        }
    }

}
