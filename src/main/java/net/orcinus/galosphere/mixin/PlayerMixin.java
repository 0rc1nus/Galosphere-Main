package net.orcinus.galosphere.mixin;

import net.minecraft.world.entity.player.Player;
import net.orcinus.galosphere.init.GItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(at = @At("TAIL"), method = "isScoping", cancellable = true)
    private void GE$isScoping(CallbackInfoReturnable<Boolean> cir) {
        Player $this = (Player) (Object) this;
        if ($this.isUsingItem() && $this.getUseItem().is(GItems.SPECTRE_BOUNDED_SPYGLASS.get())) {
            cir.setReturnValue(true);
        }
    }

}
