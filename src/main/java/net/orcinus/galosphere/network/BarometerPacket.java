package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.GalosphereClient;

public record BarometerPacket(int time) implements CustomPacketPayload {
    public static final Type<BarometerPacket> TYPE = new Type<>(Galosphere.id("barometer_info"));
    public static final StreamCodec<FriendlyByteBuf, BarometerPacket> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, BarometerPacket>() {
        @Override
        public BarometerPacket decode(FriendlyByteBuf buf) {
            return new BarometerPacket(buf.readInt());
        }

        @Override
        public void encode(FriendlyByteBuf buf, BarometerPacket packet) {
            buf.writeInt(packet.time());
        }
    };

    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(this.time);
    }

    public void receive(ClientPlayNetworking.Context context) {
        Minecraft client = context.client();
        client.execute(() -> GalosphereClient.clearWeatherTime = this.time);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register() {
        // Register receiver for the server packet type
        ClientPlayNetworking.registerGlobalReceiver(net.orcinus.galosphere.network.ServerPacketTypes.BAROMETER_TYPE, (packet, context) -> {
            // Convert server packet to client packet and handle
            BarometerPacket clientPacket = new BarometerPacket(packet.time());
            clientPacket.receive(context);
        });
    }
}
