package net.orcinus.galosphere.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.IBanner;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.crafting.AuraRingerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.crafting.WarpedAnchorDispenseItemBehavior;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.util.BannerRendererUtil;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onLoottableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        LootTable table = event.getTable();
        if (name.equals(new ResourceLocation("entities/pillager"))) {
            table.addPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).build());
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        Level world = event.getWorld();
        BlockState state = world.getBlockState(pos);
        if (player.isShiftKeyDown() && !((IBanner) player).getBanner().isEmpty() && stack.isEmpty()) {
            ItemStack copy = ((IBanner) player).getBanner();
            player.setItemInHand(hand, copy);
            player.gameEvent(GameEvent.EQUIP, player);
            ((IBanner) player).setBanner(ItemStack.EMPTY);
        }
        if (state.getBlock() == Blocks.COMPOSTER) {
            if (stack.getItem() == GItems.LUMIERE_SHARD.get()) {
                if (state.getValue(ComposterBlock.LEVEL) > 0 && state.getValue(ComposterBlock.LEVEL) < 8) {
                    event.setCanceled(true);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    world.setBlock(pos, GBlocks.LUMIERE_COMPOSTER.get().defaultBlockState().setValue(LumiereComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
                    world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    player.swing(hand);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        BannerRendererUtil util = new BannerRendererUtil();
        if (((IBanner) player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET.get())) {
            if (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem) {
                player.gameEvent(GameEvent.EQUIP, player);
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
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.get().asItem(), new AuraRingerDispenseItemBehavior());

        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.get().asItem(), new WarpedAnchorDispenseItemBehavior());

        DispenserBlock.registerBehavior(GItems.LUMIERE_SHARD.get(), new LumiereComposterDispenseItemBehavior());

        Registry.ITEM.getTagOrEmpty(ItemTags.CLUSTER_MAX_HARVESTABLES).iterator().forEachRemaining(holder -> {
            DispenserBlock.registerBehavior(holder.value(), new PickaxeDispenseItemBehavior());
        });

    }

}
