package net.orcinus.galosphere.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ServerItemCooldowns;
import net.minecraftforge.network.PacketDistributor;
import net.orcinus.galosphere.init.GNetworkHandler;
import net.orcinus.galosphere.network.PlayCooldownSoundPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerItemCooldowns.class)
public class ServerItemCooldownsMixin {

    @Shadow @Final private ServerPlayer player;

    @Inject(at = @At("TAIL"), method = "onCooldownEnded")
    private void G$removeCooldown(Item item, CallbackInfo ci) {
        GNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> this.player), new PlayCooldownSoundPacket());
    }

}
