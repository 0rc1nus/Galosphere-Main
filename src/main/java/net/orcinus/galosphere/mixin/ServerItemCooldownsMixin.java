package net.orcinus.galosphere.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ServerItemCooldowns;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GNetwork;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerItemCooldowns.class)
public class ServerItemCooldownsMixin {

    @Shadow
    @Final
    private ServerPlayer player;

    @Inject(at = @At("TAIL"), method = "onCooldownEnded")
    private void G$removeCooldown(Item item, CallbackInfo ci) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        if (item.equals(GItems.SALTBOUND_TABLET)) {
            ServerPlayNetworking.send(this.player, GNetwork.PLAY_COOLDOWN_SOUND, buf);
        }
    }

}
