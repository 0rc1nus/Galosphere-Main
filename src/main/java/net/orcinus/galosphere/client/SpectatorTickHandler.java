package net.orcinus.galosphere.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;

@OnlyIn(Dist.CLIENT)
public class SpectatorTickHandler {

    public static void tick() {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player != null && (player instanceof SpectreBoundSpyglass spectreBoundSpyglass && spectreBoundSpyglass.isUsingSpectreBoundedSpyglass() && client.getCameraEntity() instanceof Spectatable)) {
            player.setDeltaMovement(player.getDeltaMovement().multiply(0, 1, 0));
            player.xxa = 0.0F;
            player.zza = 0.0F;
            player.setJumping(false);
            player.setSprinting(false);
        }
    }

}
