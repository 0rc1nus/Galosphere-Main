package net.orcinus.galosphere.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.init.GItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    @Inject(at = @At("HEAD"), method = "renderArmWithItem", cancellable = true)
    private void GE$renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (itemStack.is(GItems.SPECTRE_BOUNDED_SPYGLASS.get()) && livingEntity.getUseItem() == itemStack && livingEntity.swingTime == 0) {
            ci.cancel();
            ((PlayerItemInHandLayer<?, ?>)(Object)this).renderArmWithSpyglass(livingEntity, itemStack, humanoidArm, poseStack, multiBufferSource, i);
        }
    }

}
