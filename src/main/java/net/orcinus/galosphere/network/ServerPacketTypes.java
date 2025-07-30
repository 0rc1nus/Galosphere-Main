package net.orcinus.galosphere.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.orcinus.galosphere.Galosphere;

/**
 * Server-safe packet type definitions without client-side imports
 */
public class ServerPacketTypes {
    
    // Packet types
    public static final CustomPacketPayload.Type<ServerBarometerPacket> BAROMETER_TYPE = 
        new CustomPacketPayload.Type<>(Galosphere.id("barometer_info"));
    public static final CustomPacketPayload.Type<ServerPlayCooldownSoundPacket> PLAY_COOLDOWN_SOUND_TYPE = 
        new CustomPacketPayload.Type<>(Galosphere.id("play_cooldown_sound"));
    public static final CustomPacketPayload.Type<ServerSendParticlesPacket> SEND_PARTICLES_TYPE = 
        new CustomPacketPayload.Type<>(Galosphere.id("send_particles"));
    public static final CustomPacketPayload.Type<ServerSendPerspectivePacket> SEND_PERSPECTIVE_TYPE = 
        new CustomPacketPayload.Type<>(Galosphere.id("send_perspective"));

    // Server-safe packet implementations
    public record ServerBarometerPacket(int time) implements CustomPacketPayload {
        public static final StreamCodec<FriendlyByteBuf, ServerBarometerPacket> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ServerBarometerPacket>() {
            @Override
            public ServerBarometerPacket decode(FriendlyByteBuf buf) {
                return new ServerBarometerPacket(buf.readInt());
            }

            @Override
            public void encode(FriendlyByteBuf buf, ServerBarometerPacket packet) {
                buf.writeInt(packet.time());
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return BAROMETER_TYPE;
        }
    }

    public record ServerPlayCooldownSoundPacket() implements CustomPacketPayload {
        public static final StreamCodec<FriendlyByteBuf, ServerPlayCooldownSoundPacket> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ServerPlayCooldownSoundPacket>() {
            @Override
            public ServerPlayCooldownSoundPacket decode(FriendlyByteBuf buf) {
                return new ServerPlayCooldownSoundPacket();
            }

            @Override
            public void encode(FriendlyByteBuf buf, ServerPlayCooldownSoundPacket packet) {
                // No data to encode
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return PLAY_COOLDOWN_SOUND_TYPE;
        }
    }

    public record ServerSendParticlesPacket(net.minecraft.core.BlockPos blockPos) implements CustomPacketPayload {
        public static final StreamCodec<FriendlyByteBuf, ServerSendParticlesPacket> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ServerSendParticlesPacket>() {
            @Override
            public ServerSendParticlesPacket decode(FriendlyByteBuf buf) {
                return new ServerSendParticlesPacket(buf.readBlockPos());
            }

            @Override
            public void encode(FriendlyByteBuf buf, ServerSendParticlesPacket packet) {
                buf.writeBlockPos(packet.blockPos());
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return SEND_PARTICLES_TYPE;
        }
    }

    public record ServerSendPerspectivePacket(java.util.UUID uuid, int id) implements CustomPacketPayload {
        public static final StreamCodec<FriendlyByteBuf, ServerSendPerspectivePacket> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ServerSendPerspectivePacket>() {
            @Override
            public ServerSendPerspectivePacket decode(FriendlyByteBuf buf) {
                return new ServerSendPerspectivePacket(buf.readUUID(), buf.readInt());
            }

            @Override
            public void encode(FriendlyByteBuf buf, ServerSendPerspectivePacket packet) {
                buf.writeUUID(packet.uuid());
                buf.writeInt(packet.id());
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return SEND_PERSPECTIVE_TYPE;
        }
    }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(BAROMETER_TYPE, ServerBarometerPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(PLAY_COOLDOWN_SOUND_TYPE, ServerPlayCooldownSoundPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SEND_PARTICLES_TYPE, ServerSendParticlesPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SEND_PERSPECTIVE_TYPE, ServerSendPerspectivePacket.STREAM_CODEC);
    }
}