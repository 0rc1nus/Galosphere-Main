package net.orcinus.galosphere.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.orcinus.galosphere.client.ClientEventsHandler;

import java.util.function.Supplier;

public class PlayCooldownSoundPacket {

    public PlayCooldownSoundPacket() {
    }

    public static PlayCooldownSoundPacket read(FriendlyByteBuf buf) {
        return new PlayCooldownSoundPacket();
    }

    public static void write(PlayCooldownSoundPacket packet, FriendlyByteBuf buf) {
    }

    public static void handle(PlayCooldownSoundPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ClientEventsHandler::playCooldownSound);
        ctx.get().setPacketHandled(true);
    }

}