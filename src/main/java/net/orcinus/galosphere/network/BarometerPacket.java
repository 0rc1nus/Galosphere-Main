package net.orcinus.galosphere.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.orcinus.galosphere.events.ClientEvents;

import java.util.function.Supplier;

public class BarometerPacket {
    private final int weatherTicks;

    public BarometerPacket(int weatherTicks) {
        this.weatherTicks = weatherTicks;
    }

    public static BarometerPacket read(FriendlyByteBuf buf) {
        int weatherTicks = buf.readInt();
        return new BarometerPacket(weatherTicks);
    }

    public static void write(BarometerPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.weatherTicks);
    }

    public static void handle(BarometerPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientEvents.clearWeatherTime = packet.weatherTicks;
        });
        ctx.get().setPacketHandled(true);
    }

}