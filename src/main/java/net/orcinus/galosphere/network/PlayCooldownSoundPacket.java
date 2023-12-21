package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.orcinus.galosphere.init.GSoundEvents;

public class PlayCooldownSoundPacket implements ClientPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        client.execute(() -> {
            if (client.player != null) client.getSoundManager().play(SimpleSoundInstance.forUI(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER, 1));
        });
    }
}
