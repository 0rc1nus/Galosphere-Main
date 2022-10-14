package net.orcinus.galosphere.network;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class SendPerspectivePacket {
    private final UUID uuid;
    private final int id;

    public SendPerspectivePacket(UUID uuid, int id) {
        this.uuid = uuid;
        this.id = id;
    }

    public static void write(SendPerspectivePacket packet, FriendlyByteBuf buf) {
        buf.writeUUID(packet.uuid);
        buf.writeInt(packet.id);
    }

    public static SendPerspectivePacket read(FriendlyByteBuf buf) {
        return new SendPerspectivePacket(buf.readUUID(), buf.readInt());
    }

    public static void handle(SendPerspectivePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft client = Minecraft.getInstance();
            Optional.ofNullable(client.level).flatMap(world -> Optional.ofNullable(world.getPlayerByUUID(packet.uuid)).filter(player -> player.equals(client.player)).flatMap(player -> Optional.ofNullable(client.level.getEntity(packet.id)))).ifPresent(entity -> {
                client.setCameraEntity(entity);
                client.player.resetPos();
                if (!client.options.getCameraType().isFirstPerson()) {
                    client.options.setCameraType(CameraType.FIRST_PERSON);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
