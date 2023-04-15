package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.orcinus.galosphere.GalosphereClient;

public class BarometerPacket implements ClientPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int clearWeatherTime = buf.readInt();
        client.execute(() -> GalosphereClient.clearWeatherTime = clearWeatherTime);
    }

}