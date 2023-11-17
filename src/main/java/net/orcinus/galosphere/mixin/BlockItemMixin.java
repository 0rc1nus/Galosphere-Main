package net.orcinus.galosphere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.util.PreservedShulkerBox;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;setPlacedBy(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER), method = "place")
    private void G$placeBlock(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = blockPlaceContext.getPlayer();
        if (player != null) {
            BlockEntity blockEntity = player.level().getBlockEntity(blockPlaceContext.getClickedPos());
            if (player.hasEffect(GMobEffects.BLOCK_BANE) && !player.getAbilities().instabuild) {
                player.hurt(blockPlaceContext.getLevel().damageSources().magic(), 3.0F);
                player.getCooldowns().addCooldown(blockPlaceContext.getItemInHand().getItem(), 100);
            }
            CompoundTag tag = blockPlaceContext.getItemInHand().getTag();
            if (blockEntity instanceof ShulkerBoxBlockEntity && tag != null && tag.contains("Preserved")) {
                ((PreservedShulkerBox)blockEntity).setPreserved(true);
            }
        }
    }

}
