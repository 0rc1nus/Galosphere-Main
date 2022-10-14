package net.orcinus.galosphere.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.entities.SpectreEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.UUID;

@Mixin(SpyglassItem.class)
public abstract class SpyglassItemMixin extends Item {

    public SpyglassItemMixin(Properties properties) {
        super(properties);
    }

//    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
//    private void GE$use(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
//        ItemStack stack = player.getItemInHand(hand);
//        if (isFayBoundedSpyglass(stack) && stack.getTag() != null) {
//            int id = stack.getTag().getInt("FayBoundId");
//            if (!(world.getEntity(id) instanceof FayEntity)) {
//                cir.setReturnValue(InteractionResultHolder.fail(stack));
//            }
//        }
//    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int p_41431_) {
        if (isFayBoundedSpyglass(stack) && stack.getTag() != null) {
            int id = stack.getTag().getInt("FayBoundId");
            UUID uuid = stack.getTag().getUUID("FayBoundUUID");
            Entity entity = world.getEntity(id);
            if (entity instanceof SpectreEntity fay) {
                fay.setUUID(uuid);
                if (livingEntity instanceof Player player && fay.getManipulatorUUID() != player.getUUID()) {
                    fay.setCamera(player);
                }
            }
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand p_41401_) {
        if (livingEntity instanceof SpectreEntity fayEntity && fayEntity.canBeManipulated()) {
            fayEntity.playSound(SoundEvents.LODESTONE_COMPASS_LOCK, 1.0F, 1.0F);
            boolean flag = !player.getAbilities().instabuild && stack.getCount() == 1;
            if (flag) {
                this.addFayBoundTags(fayEntity, stack.getOrCreateTag());
            } else {
                ItemStack stack1 = new ItemStack(Items.SPYGLASS, 1);
                CompoundTag compoundtag = stack.hasTag() ? stack.getTag().copy() : new CompoundTag();
                stack1.setTag(compoundtag);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                this.addFayBoundTags(fayEntity, compoundtag);
                if (!player.getInventory().add(stack1)) {
                    player.drop(stack1, false);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void addFayBoundTags(SpectreEntity fay, CompoundTag compoundTag) {
        compoundTag.putInt("FayBoundId", fay.getId());
        compoundTag.putUUID("FayBoundUUID", fay.getUUID());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag p_41424_) {
        if (stack.getTag() != null && world != null) {
            Entity entity = world.getEntity(stack.getTag().getInt("FayBoundId"));
            if (entity != null) {
                String fayBoundId = entity.toString();
                list.add(Component.translatable(fayBoundId));
            }
        }
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return isFayBoundedSpyglass(stack) ? "item.galosphere.fay_bounded_spyglass" : super.getDescriptionId(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isFayBoundedSpyglass(stack) || super.isFoil(stack);
    }

    private static boolean isFayBoundedSpyglass(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && (compoundtag.contains("FayBoundId") || compoundtag.contains("FayBoundUUID"));
    }
}
