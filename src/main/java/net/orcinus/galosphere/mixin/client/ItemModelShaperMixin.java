package net.orcinus.galosphere.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.init.GItems;

@Mixin(ItemModelShaper.class)
public class ItemModelShaperMixin {

    @Inject(at = @At("HEAD"), method = "getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", cancellable = true)
    private void GE$getItemModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.is(GItems.SPECTRE_BOUND_SPYGLASS)) {
            cir.setReturnValue(((ItemModelShaper)(Object)this).getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory")));
        }
    }
}
