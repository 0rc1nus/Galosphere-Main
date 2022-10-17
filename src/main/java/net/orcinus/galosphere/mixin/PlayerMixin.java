package net.orcinus.galosphere.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.util.BannerRendererUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(at = @At("TAIL"), method = "isScoping", cancellable = true)
    private void GE$isScoping(CallbackInfoReturnable<Boolean> cir) {
        Player $this = (Player) (Object) this;
        if ($this.isUsingItem() && $this.getUseItem().is(GItems.SPECTRE_BOUND_SPYGLASS)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getDestroySpeed", cancellable = true)
    private void G$getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (blockState.getBlock() == Blocks.BUDDING_AMETHYST) {
            cir.setReturnValue(2.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "getProjectile", cancellable = true)
    private void GE$getProjectile(ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity $this = (LivingEntity) (Object) this;
        if (!ProjectileWeaponItem.getHeldProjectile($this, stack -> stack.getItem() == GItems.GLOW_FLARE).isEmpty() && $this instanceof Player) {
            cir.setReturnValue(new ItemStack(GItems.GLOW_FLARE));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), method = "interactOn", cancellable = true)
    private void G$interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getItemInHand(interactionHand);
        BannerRendererUtil util = new BannerRendererUtil();
        if (entity instanceof Horse horse) {
            if (horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                if (((BannerAttachable) horse).getBanner().isEmpty()) {
                    if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
                        if (!horse.level.isClientSide()) {
                            ItemStack copy = stack.copy();
                            if (!player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            copy.setCount(1);
                            horse.level.playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                            horse.gameEvent(GameEvent.MOB_INTERACT, player);
                            ((BannerAttachable) horse).setBanner(copy);
                            player.swing(interactionHand);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                        }
                    }
                } else {
                    boolean b = player.isShiftKeyDown() && stack.isEmpty();
                    if (b) {
                        ItemStack copy = ((BannerAttachable) horse).getBanner();
                        player.setItemInHand(interactionHand, copy);
                        horse.level.playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                        horse.gameEvent(GameEvent.MOB_INTERACT, player);
                        ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }

}
