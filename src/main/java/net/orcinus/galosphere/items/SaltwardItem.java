package net.orcinus.galosphere.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.api.SaltBound;
import net.orcinus.galosphere.util.SaltLayer;

public class SaltwardItem extends Item {

    public SaltwardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        SaltBound saltBound = (SaltBound) player;
        if (saltBound.getSaltLayers() == 0) {
            saltBound.setSaltLayers(SaltLayer.STRONG);
            saltBound.setSaltDegradation(SaltLayer.STRONG.getDurability());
            player.getCooldowns().addCooldown(this, 1000);
            player.playSound(SoundEvents.SKELETON_CONVERTED_TO_STRAY);
            return InteractionResultHolder.success(player.getItemInHand(interactionHand));
        }
        return super.use(level, player, interactionHand);
    }

}
