package net.orcinus.galosphere.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.entities.SilverBombEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverBombItem extends Item {

    public SilverBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 20);
        if (!world.isClientSide()) {
            SilverBombEntity silverBomb = new SilverBombEntity(world, player, itemstack);
            silverBomb.setItem(itemstack);
            silverBomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 1.0F);
            world.addFreshEntity(silverBomb);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag tip) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.getInt("Duration") > 0) {
            list.add((new TranslatableComponent("item.cavesandtrenches.silver_bomb.duration")).append(" ").append(String.valueOf(tag.getInt("Duration"))).withStyle(ChatFormatting.GRAY));
        }
        if (tag.getInt("Explosion") > 0) {
            list.add((new TranslatableComponent("item.cavesandtrenches.silver_bomb.explosion")).append(" ").append(String.valueOf(tag.getInt("Explosion"))).withStyle(ChatFormatting.GRAY));
        }
        if (tag.getInt("Bouncy") > 0) {
            list.add((new TranslatableComponent("item.cavesandtrenches.silver_bomb.bouncy")).append(" ").append(String.valueOf(tag.getInt("Bouncy"))).withStyle(ChatFormatting.GRAY));
        }
        if (tag.getBoolean("Shrapnel")) {
            list.add((new TranslatableComponent("item.cavesandtrenches.silver_bomb.shrapnel")).withStyle(ChatFormatting.GRAY));
        }
    }

    public boolean hasDuration(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getInt("Duration") > 0;
    }

    public boolean hasExplosion(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getInt("Duration") > 0;
    }

    public boolean hasBouncy(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getInt("Duration") > 0;
    }

    public boolean hasShrapnel(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getInt("Duration") > 0;
    }

}
