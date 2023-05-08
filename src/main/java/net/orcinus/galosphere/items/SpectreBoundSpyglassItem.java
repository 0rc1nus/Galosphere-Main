package net.orcinus.galosphere.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.init.GCriteriaTriggers;

public class SpectreBoundSpyglassItem extends Item {

    public SpectreBoundSpyglassItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 1200;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!SpectreBoundSpyglass.canUseSpectreBoundSpyglass(player.getItemInHand(interactionHand))) {
            return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
        } else {
            if (player instanceof ServerPlayer serverPlayer) {
                if (!level.isClientSide) {
                    GCriteriaTriggers.USE_SPECTRE_SPYGLASS.trigger(serverPlayer);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            player.playSound(SoundEvents.SPYGLASS_USE, 1.0f, 1.0f);
            return ItemUtils.startUsingInstantly(level, player, interactionHand);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        return itemStack;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        this.stopUsing(livingEntity);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    private void stopUsing(LivingEntity livingEntity) {
        livingEntity.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, 1.0f);
        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(this, 20);
        }
    }

}
