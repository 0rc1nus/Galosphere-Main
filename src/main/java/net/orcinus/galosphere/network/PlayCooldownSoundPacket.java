package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GSoundEvents;

public record PlayCooldownSoundPacket() implements CustomPacketPayload {
    public static final Type<PlayCooldownSoundPacket> TYPE = new Type<>(Galosphere.id("play_cooldown_sound"));
    public static final StreamCodec<FriendlyByteBuf, PlayCooldownSoundPacket> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, PlayCooldownSoundPacket>() {
        @Override
        public PlayCooldownSoundPacket decode(FriendlyByteBuf buf) {
            return new PlayCooldownSoundPacket();
        }

        @Override
        public void encode(FriendlyByteBuf buf, PlayCooldownSoundPacket packet) {
            // No data to encode for this packet
        }
    };

    public void write(FriendlyByteBuf friendlyByteBuf) {
    }

    public void receive(ClientPlayNetworking.Context context) {
        Minecraft client = context.client();
        client.execute(() -> {
            if (client.player != null) client.getSoundManager().play(SimpleSoundInstance.forUI(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER, 1));
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register() {
        // Register receiver for the server packet type (packet type already registered by ServerPacketTypes)
        ClientPlayNetworking.registerGlobalReceiver(net.orcinus.galosphere.network.ServerPacketTypes.PLAY_COOLDOWN_SOUND_TYPE, (packet, context) -> {
            // Convert server packet to client packet and handle
            PlayCooldownSoundPacket clientPacket = new PlayCooldownSoundPacket();
            clientPacket.receive(context);
        });
    }
}
