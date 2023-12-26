package net.orcinus.galosphere.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.orcinus.galosphere.init.GSoundEvents;

public class ClientEventsHandler {

    public static void playCooldownSound() {
        Minecraft instance = Minecraft.getInstance();
        LocalPlayer player = instance.player;
        if (player != null) {
            instance.getSoundManager().play(SimpleSoundInstance.forUI(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER.get(), 1.0F));
        }
    }

}
