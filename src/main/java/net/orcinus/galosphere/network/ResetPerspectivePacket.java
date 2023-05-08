package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public class ResetPerspectivePacket implements ClientPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        UUID uuid = buf.readUUID();
        client.execute(() -> {
            Level world = client.level;
            if (world != null) {
                Optional.ofNullable(world.getPlayerByUUID(uuid)).filter(player -> player.equals(client.player)).ifPresent(player -> {
                    if (client.getCameraEntity() != player) {
                        client.setCameraEntity(player);
                    }
                });
            }
        });
    }
}
