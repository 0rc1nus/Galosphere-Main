package net.orcinus.galosphere.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.orcinus.galosphere.network.ServerPacketTypes;

public class GNetwork {

    public static void init() {
        // Register server-safe packet types
        ServerPacketTypes.register();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Register client-side packet handlers
        net.orcinus.galosphere.network.SendParticlesPacket.register();
        net.orcinus.galosphere.network.SendPerspectivePacket.register();
        net.orcinus.galosphere.network.BarometerPacket.register();
        net.orcinus.galosphere.network.PlayCooldownSoundPacket.register();
    }

}
