package net.orcinus.galosphere.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
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
        if ($this.isUsingItem() && $this.getUseItem().is(GItems.SPECTRE_BOUND_SPYGLASS.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getProjectile", cancellable = true)
    private void GE$getProjectile(ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity $this = (LivingEntity) (Object) this;
        if (!ProjectileWeaponItem.getHeldProjectile($this, stack -> stack.getItem() == GItems.GLOW_FLARE.get()).isEmpty() && $this instanceof Player) {
            cir.setReturnValue(new ItemStack(GItems.GLOW_FLARE.get()));
        }
    }

}
