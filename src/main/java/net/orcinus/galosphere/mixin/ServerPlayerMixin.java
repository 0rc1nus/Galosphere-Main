package net.orcinus.galosphere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getEntityData()Lnet/minecraft/network/syncher/SynchedEntityData;", ordinal = 1, shift = At.Shift.BEFORE), method = "restoreFrom")
    private void G$restoreFrom(ServerPlayer serverPlayer, boolean bl, CallbackInfo ci) {
        ServerPlayer $this = (ServerPlayer) (Object) this;
        for (ItemStack itemStack : serverPlayer.getInventory().items) {
            CompoundTag tag = itemStack.getTag();
            boolean stackFlag = tag != null && tag.contains("Persevered");
            if (!stackFlag) {
                continue;
            }
            if (tag.contains("Persevered")) {
                tag.remove("Persevered");
            }
            $this.getInventory().add(itemStack);
        }
    }

}
