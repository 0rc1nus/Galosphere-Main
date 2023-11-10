package net.orcinus.galosphere.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.Galosphere;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), method = "dropAll")
    private boolean G$isEmpty(ItemStack instance) {
        return instance.getTag() != null && instance.getTag().contains("Preserved");
    }

}
