package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.util.BannerRendererUtil;

public class GEvents {

    public static void init() {
        registerLootableEvents();
        registerBlockUseEvents();
        registerItemUseEvents();
    }

    private static void registerItemUseEvents() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            BannerRendererUtil util = new BannerRendererUtil();
            ItemStack stack = player.getItemInHand(hand);
            if (((BannerAttachable) player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET) && (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem)) {
                player.gameEvent(GameEvent.EQUIP, player);
                ItemStack copy = stack.copy();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                copy.setCount(1);
                ((BannerAttachable) player).setBanner(copy);
                player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                return InteractionResultHolder.success(stack);
            } else {
                return InteractionResultHolder.pass(ItemStack.EMPTY);
            }
        });
    }

    private static void registerBlockUseEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (player.isShiftKeyDown() && !((BannerAttachable)player).getBanner().isEmpty() && player.getItemInHand(hand).isEmpty()) {
                ItemStack copy = ((BannerAttachable) player).getBanner();
                player.setItemInHand(hand, copy);
                player.gameEvent(GameEvent.EQUIP, player);
                ((BannerAttachable) player).setBanner(ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
            else if (state.is(Blocks.COMPOSTER) && player.getItemInHand(hand).getItem() == GItems.LUMIERE_SHARD && state.getValue(ComposterBlock.LEVEL) > 0 && state.getValue(ComposterBlock.LEVEL) < 8) {
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                world.setBlock(pos, GBlocks.LUMIERE_COMPOSTER.defaultBlockState().setValue(LumiereComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
                world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return InteractionResult.SUCCESS;
            }
            else {
                return InteractionResult.PASS;
            }
        });
    }

    private static void registerLootableEvents() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(EntityType.PILLAGER.getDefaultLootTable())) {
                tableBuilder.pool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.SILVER_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).build());
            }
        });
    }

}
