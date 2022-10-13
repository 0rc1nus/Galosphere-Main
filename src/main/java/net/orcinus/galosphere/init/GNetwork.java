package net.orcinus.galosphere.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.network.SendParticlesPacket;
import net.orcinus.galosphere.network.SendPerspectivePacket;

public class GNetwork {

    public static final ResourceLocation SEND_PARTICLES = Galosphere.id("send_particles");
    public static final ResourceLocation SEND_PERSPECTIVE = Galosphere.id("send_perspective");

    @Environment(EnvType.CLIENT)
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(SEND_PARTICLES, new SendParticlesPacket());
        ClientPlayNetworking.registerGlobalReceiver(SEND_PERSPECTIVE, new SendPerspectivePacket());
    }

}
