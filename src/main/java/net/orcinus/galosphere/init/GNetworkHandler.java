package net.orcinus.galosphere.init;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.packet.SendParticlesPacket;

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
    }

    public static void sendToAllInRangeClients(BlockPos pos, ServerLevel level, double distance, SendParticlesPacket message) {
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        if (currentServer != null) {
            PlayerList players = currentServer.getPlayerList();
            ResourceKey<Level> dimension = level.dimension();
            players.broadcast(null, pos.getX(), pos.getY(), pos.getZ(), distance, dimension, INSTANCE.toVanillaPacket(message, NetworkDirection.PLAY_TO_CLIENT));
        }
    }

    public static int getPacketID() {
        return packetID++;
    }
}
