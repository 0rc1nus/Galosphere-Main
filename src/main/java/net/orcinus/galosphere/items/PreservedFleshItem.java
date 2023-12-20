package net.orcinus.galosphere.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class PreservedFleshItem extends Item {

    public PreservedFleshItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        itemStack.hurtAndBreak(1, livingEntity, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return this.eat(livingEntity, level, itemStack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(Items.ROTTEN_FLESH);
    }

    private ItemStack eat(LivingEntity livingEntity, Level level, ItemStack itemStack) {
        if (!(livingEntity instanceof Player player)) {
            return ItemStack.EMPTY;
        }
        player.getFoodData().eat(itemStack.getItem(), itemStack);
        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
        }
        if (itemStack.isEdible()) {
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getEatingSound(itemStack), SoundSource.NEUTRAL, 1.0f, 1.0f + (level.random.nextFloat() - level.random.nextFloat()) * 0.4f);
            livingEntity.gameEvent(GameEvent.EAT);
        }
        return itemStack;
    }

}
