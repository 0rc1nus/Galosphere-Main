package net.orcinus.galosphere.mixin.client;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.init.GItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelShaper.class)
public class ItemModelShaperMixin {

    @Inject(at = @At("HEAD"), method = "getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", cancellable = true)
    private void GE$getItemModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.is(GItems.SPECTRE_BOUND_SPYGLASS)) {
            cir.setReturnValue(((ItemModelShaper)(Object)this).getModelManager().getModel(ModelResourceLocation.vanilla("spyglass_in_hand", "inventory")));
        }
    }
}
