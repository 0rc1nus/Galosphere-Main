package net.orcinus.galosphere.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class SpectreOverlay {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreRender(RenderGameOverlayEvent.Pre event) {
        if (event.isCanceled()) return;
        Optional.ofNullable(event.getType()).filter(elementType -> elementType.equals(RenderGameOverlayEvent.ElementType.LAYER)).flatMap(elementType -> Optional.ofNullable(Minecraft.getInstance().player).filter(SpectreBoundSpyglass.class::isInstance).map(SpectreBoundSpyglass.class::cast).filter(SpectreBoundSpyglass::isUsingSpectreBoundedSpyglass)).ifPresent(spectreBoundedSpyglass -> event.setCanceled(true));
    }

}
