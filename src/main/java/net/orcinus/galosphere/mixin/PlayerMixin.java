package net.orcinus.galosphere.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.config.GalosphereConfig;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.util.BannerRendererUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private final Player $this = (Player) (Object) this;

    @Inject(at = @At("TAIL"), method = "isScoping", cancellable = true)
    private void GE$isScoping(CallbackInfoReturnable<Boolean> cir) {
        if ($this.isUsingItem() && $this.getUseItem().is(GItems.SPECTRE_BOUND_SPYGLASS)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getDestroySpeed", cancellable = true)
    private void G$getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (GalosphereConfig.slowBuddingAmethystDestroySpeed && blockState.getBlock() == Blocks.BUDDING_AMETHYST) {
            cir.setReturnValue(2.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "getProjectile", cancellable = true)
    private void GE$getProjectile(ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        if ($this != null) {
            if (!ProjectileWeaponItem.getHeldProjectile($this, stack -> stack.is(GItems.GLOW_FLARE)).isEmpty()) {
                cir.setReturnValue(new ItemStack(GItems.GLOW_FLARE));
            }
            if (!ProjectileWeaponItem.getHeldProjectile($this, stack -> stack.is(GItems.SPECTRE_FLARE)).isEmpty()) {
                cir.setReturnValue(new ItemStack(GItems.SPECTRE_FLARE));
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), method = "interactOn", cancellable = true)
    private void G$interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = $this.getItemInHand(interactionHand);
        BannerRendererUtil util = new BannerRendererUtil();
        if (entity instanceof Horse horse) {
            if (horse.getArmor().is(GItems.STERLING_HORSE_ARMOR)) {
                if (((BannerAttachable) horse).getBanner().isEmpty()) {
                    if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
                        if (!horse.level().isClientSide()) {
                            ItemStack copy = stack.copy();
                            if (!$this.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            copy.setCount(1);
                            horse.level().playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                            horse.gameEvent(GameEvent.ENTITY_INTERACT, $this);
                            ((BannerAttachable) horse).setBanner(copy);
                            $this.swing(interactionHand);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                        }
                    }
                } else {
                    if ($this.isShiftKeyDown() && stack.isEmpty()) {
                        ItemStack copy = ((BannerAttachable) horse).getBanner();
                        $this.setItemInHand(interactionHand, copy);
                        horse.level().playSound(null, horse, SoundEvents.HORSE_ARMOR, SoundSource.PLAYERS, 1.0F, 1.0F);
                        horse.gameEvent(GameEvent.ENTITY_INTERACT, $this);
                        ((BannerAttachable) horse).setBanner(ItemStack.EMPTY);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }

}
