package net.orcinus.galosphere.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
    
    @Inject(at = @At("HEAD"), method = "appendHoverText")
    private void G$appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("Preserved")) {
            list.add(Component.translatable("item.galosphere.preserved").withStyle(ChatFormatting.GRAY));
        }
    }
    
}
