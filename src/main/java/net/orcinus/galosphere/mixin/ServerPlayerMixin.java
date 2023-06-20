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
        for (ItemStack stack : serverPlayer.getArmorSlots()) {
            CompoundTag tag = stack.getTag();
            boolean flag = !stack.isEmpty() && tag != null && tag.contains("Persevered");
            if (!flag) {
                continue;
            }
            for (ItemStack itemStack : serverPlayer.getInventory().items) {
                boolean stackFlag = itemStack.getTag() != null && itemStack.getTag().contains("Persevered");
                if (!stackFlag) {
                    continue;
                }
                $this.getInventory().add(itemStack);
            }
            if (tag.contains("Persevered")) {
                tag.remove("Persevered");
                break;
            }
        }
    }

}
