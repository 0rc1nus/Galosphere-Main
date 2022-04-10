package net.orcinus.galosphere.events;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.IBanner;
import net.orcinus.galosphere.crafting.AuraListenerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.util.BannerRendererUtil;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        if (player.isShiftKeyDown() && !((IBanner) player).getBanner().isEmpty() && stack.isEmpty()) {
            ItemStack copy = ((IBanner) player).getBanner();
            player.setItemInHand(hand, copy);
            ((IBanner) player).setBanner(ItemStack.EMPTY);
        }
    }

    @SubscribeEvent
    public void onRightClickEvent(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        BannerRendererUtil util = new BannerRendererUtil();
        if (((IBanner) player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET.get())) {
            if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
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

    @SubscribeEvent
    public void onTagsUpdated(TagsUpdatedEvent event) {
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.get().asItem(), new AuraListenerDispenseItemBehavior());

        DispenserBlock.registerBehavior(GBlocks.LUMIERE_BLOCK.get(), new LumiereComposterDispenseItemBehavior());

        Registry.ITEM.getTagOrEmpty(ItemTags.CLUSTER_MAX_HARVESTABLES).iterator().forEachRemaining(holder -> {
            DispenserBlock.registerBehavior(holder.value(), new PickaxeDispenseItemBehavior());
        });

    }

}
