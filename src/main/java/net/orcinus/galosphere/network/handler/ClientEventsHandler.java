package net.orcinus.galosphere.network.handler;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.orcinus.galosphere.events.ClientEvents;
import net.orcinus.galosphere.init.GSoundEvents;
import net.orcinus.galosphere.network.BarometerPacket;
import net.orcinus.galosphere.network.PlayCooldownSoundPacket;
import net.orcinus.galosphere.network.SendParticlesPacket;
import net.orcinus.galosphere.network.SendPerspectivePacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT) 
public class ClientEventsHandler {

    public static void handleSendParticles(SendParticlesPacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            Optional.ofNullable(minecraft.level).ifPresent(world -> {
                RandomSource randomsource = world.getRandom();
                world.playLocalSound(packet.blockPos(), GSoundEvents.GLOW_FLARE_SPREAD.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                boolean flag = world.getBlockState(packet.blockPos()).isCollisionShapeFullBlock(world, packet.blockPos());
                int l2 = flag ? 40 : 20;
                float f9 = flag ? 0.45F : 0.25F;
                for(int k3 = 0; k3 < l2; ++k3) {
                    float f12 = 2.0F * randomsource.nextFloat() - 1.0F;
                    float f14 = 2.0F * randomsource.nextFloat() - 1.0F;
                    float f15 = 2.0F * randomsource.nextFloat() - 1.0F;
                    world.addParticle(ParticleTypes.GLOW, (double)packet.blockPos().getX() + 0.5D + (double)(f12 * f9), (double)packet.blockPos().getY() + 0.5D + (double)(f14 * f9), (double)packet.blockPos().getZ() + 0.5D + (double)(f15 * f9), f12 * 0.07F, f14 * 0.07F, f15 * 0.07F);
                }
                world.playLocalSound(packet.blockPos(), GSoundEvents.GLOW_FLARE_SPREAD.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            });
        });
    }

    public static void sendBarometerInfo(BarometerPacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ClientEvents.clearWeatherTime = packet.weatherTicks();
        });
    }

    public static void sendPerspective(SendPerspectivePacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft client = Minecraft.getInstance();
            Optional.ofNullable(client.level).flatMap(world -> Optional.ofNullable(world.getPlayerByUUID(packet.uuid())).filter(player -> player.equals(client.player)).flatMap(player -> Optional.ofNullable(client.level.getEntity(packet.id())))).ifPresent(entity -> {
                client.setCameraEntity(entity);
                if (!client.options.getCameraType().isFirstPerson()) {
                    client.options.setCameraType(CameraType.FIRST_PERSON);
                }
            });
        });
    }

    public static void playCooldownSound(PlayCooldownSoundPacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            LocalPlayer player = instance.player;
            if (player != null) {
                instance.getSoundManager().play(SimpleSoundInstance.forUI(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER.get(), 1.0F));
            }
        });
    }
}