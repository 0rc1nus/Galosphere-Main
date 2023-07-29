package net.orcinus.galosphere.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class ResetPerspectivePacket {
    private final UUID uuid;

    public ResetPerspectivePacket(UUID uuid) {
        this.uuid = uuid;
    }

    public static ResetPerspectivePacket read(FriendlyByteBuf buf) {
        UUID uuid = buf.readUUID();
        return new ResetPerspectivePacket(uuid);
    }

    public static void write(ResetPerspectivePacket packet, FriendlyByteBuf buf) {
        buf.writeUUID(packet.uuid);
    }

    public static void handle(ResetPerspectivePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft client = Minecraft.getInstance();
            Optional.ofNullable(client.level).flatMap(world -> Optional.ofNullable(world.getPlayerByUUID(packet.uuid))).filter(player -> player.equals(client.player) && client.getCameraEntity() != player).ifPresent(client::setCameraEntity);
        });
        ctx.get().setPacketHandled(true);
    }

}
