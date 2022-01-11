package net.orcinus.cavesandtrenches.events;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.api.IBanner;
import net.orcinus.cavesandtrenches.init.CTItems;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        if (player.isShiftKeyDown() && !((IBanner)player).getBanner().isEmpty() && stack.isEmpty()) {
            ItemStack copy = ((IBanner) player).getBanner();
            player.setItemInHand(InteractionHand.MAIN_HAND, copy);
            ((IBanner) player).setBanner(ItemStack.EMPTY);
        }
    }

    @SubscribeEvent
    public void onRightClickEvent(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        if (((IBanner)player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(CTItems.STERLING_HELMET.get())) {
            if (stack.getItem() instanceof BannerItem) {
                ItemStack copy = stack.copy();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                copy.setCount(1);
                ((IBanner) player).setBanner(copy);
                player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                player.swing(hand);
            }
        }
    }

}
