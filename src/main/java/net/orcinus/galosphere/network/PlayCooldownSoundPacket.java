package net.orcinus.galosphere.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.orcinus.galosphere.events.ClientEvents;
import net.orcinus.galosphere.init.GSoundEvents;

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
        ctx.get().enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.playSound(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER.get(), 1.0F, 1.0F);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}