package net.orcinus.galosphere.items;

import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.orcinus.galosphere.api.GoldenBreath;

public class GoldenLichenCordycepsItem extends Item {

    public GoldenLichenCordycepsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (this.isEdible()) {
            boolean flag = livingEntity.getAirSupply() < livingEntity.getMaxAirSupply();
            int airSupply = livingEntity.getAirSupply();
            int i = (livingEntity.getMaxAirSupply() * 3) / 4;
            boolean flag1 = airSupply > i;
            if (livingEntity instanceof Player player && (flag || flag1 || player.canEat(this.getFoodProperties().canAlwaysEat()))) {
                if (flag1 && livingEntity instanceof GoldenBreath goldenBreath) {
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getEatingSound(itemStack), SoundSource.NEUTRAL, 1.0f, 1.0f + (level.random.nextFloat() - level.random.nextFloat()) * 0.4f);
                    livingEntity.gameEvent(GameEvent.EAT);
                    goldenBreath.setGoldenAirSupply(goldenBreath.getMaxGoldenAirSupply());
                }
                if (flag) {
                    livingEntity.setAirSupply(Math.min(livingEntity.getAirSupply() + 120, livingEntity.getMaxAirSupply()));
                }
                livingEntity.eat(level, itemStack);
            }
        }
        return itemStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (player.getAirSupply() < ((GoldenBreath)player).getMaxGoldenAirSupply() || player.getAirSupply() < player.getMaxAirSupply() || player.canEat(itemstack.getFoodProperties(player).canAlwaysEat())) {
            return ItemUtils.startUsingInstantly(level, player, interactionHand);
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
        }
    }
}
