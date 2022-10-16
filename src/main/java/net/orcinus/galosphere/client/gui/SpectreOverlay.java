package net.orcinus.galosphere.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class SpectreOverlay {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreRender(RenderGuiOverlayEvent.Pre event) {
        if (event.isCanceled()) return;
        Optional.of(event.getOverlay()).filter(elementType -> elementType.equals(VanillaGuiOverlay.EXPERIENCE_BAR.type())).flatMap(elementType -> Optional.ofNullable(Minecraft.getInstance().player).filter(SpectreBoundSpyglass.class::isInstance).map(SpectreBoundSpyglass.class::cast).filter(SpectreBoundSpyglass::isUsingSpectreBoundedSpyglass)).ifPresent(spectreBoundedSpyglass -> event.setCanceled(true));
    }

}
