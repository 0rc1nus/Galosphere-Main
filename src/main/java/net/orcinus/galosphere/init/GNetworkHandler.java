package net.orcinus.galosphere.init;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.network.BarometerPacket;
import net.orcinus.galosphere.network.SendParticlesPacket;
import net.orcinus.galosphere.network.SendPerspectivePacket;

import java.util.Optional;

public class GNetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(Galosphere.MODID, "network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    protected static int packetID = 0;

    public GNetworkHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(getPacketID(), SendParticlesPacket.class, SendParticlesPacket::write, SendParticlesPacket::read, SendParticlesPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(getPacketID(), SendPerspectivePacket.class, SendPerspectivePacket::write, SendPerspectivePacket::read, SendPerspectivePacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(getPacketID(), BarometerPacket.class, BarometerPacket::write, BarometerPacket::read, BarometerPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToAllInRangeClients(BlockPos pos, ServerLevel level, double distance, SendParticlesPacket message) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            server.getPlayerList().broadcast(null, pos.getX(), pos.getY(), pos.getZ(), distance, level.dimension(), INSTANCE.toVanillaPacket(message, NetworkDirection.PLAY_TO_CLIENT));
        }
    }

    public static int getPacketID() {
        return packetID++;
    }
}
