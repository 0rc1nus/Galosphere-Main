package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.orcinus.galosphere.init.GSoundEvents;

public class PlayCooldownSoundPacket implements ClientPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        client.execute(() -> {
            LocalPlayer player = client.player;
            if (player != null) {
                player.playSound(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER, 1.0F, 1.0F);
            }
        });
    }
}
