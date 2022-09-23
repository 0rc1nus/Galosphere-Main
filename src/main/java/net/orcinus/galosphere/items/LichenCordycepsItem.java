package net.orcinus.galosphere.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class LichenCordycepsItem extends ItemNameBlockItem {

    public LichenCordycepsItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (this.isEdible()) {
            boolean flag = livingEntity.getAirSupply() < livingEntity.getMaxAirSupply();
            if (livingEntity instanceof Player player && (flag || player.canEat(this.getFoodProperties().canAlwaysEat()))) {
                if (flag) {
                    livingEntity.setAirSupply(Math.min(livingEntity.getAirSupply() + 60, livingEntity.getMaxAirSupply()));
                }
                livingEntity.eat(level, itemStack);
            }
        }
        return itemStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (player.getAirSupply() < player.getMaxAirSupply() || player.canEat(itemstack.getFoodProperties(player).canAlwaysEat())) {
            return ItemUtils.startUsingInstantly(level, player, interactionHand);
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
        }
    }

}
