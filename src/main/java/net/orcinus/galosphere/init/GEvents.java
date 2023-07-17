package net.orcinus.galosphere.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
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
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.BannerAttachable;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.config.GalosphereConfig;
import net.orcinus.galosphere.util.BannerRendererUtil;

public class GEvents {

    public static void init() {
        GEvents.registerServerTickEvents();
        GEvents.registerLootTableEvents();
        GEvents.registerBlockUseEvents();
        GEvents.registerItemUseEvents();
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            LocalPlayer player = client.player;
            if (player != null && (player instanceof SpectreBoundSpyglass spectreBoundSpyglass && spectreBoundSpyglass.isUsingSpectreBoundedSpyglass()) && client.getCameraEntity() instanceof Spectatable) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(0, 1, 0));
                player.xxa = 0.0F;
                player.zza = 0.0F;
                player.setJumping(false);
                player.setSprinting(false);
            }
        });
    }

    private static void registerServerTickEvents() {
        ServerTickEvents.START_WORLD_TICK.register(Galosphere.id("send_barometer_info"), (level) -> {
            level.getPlayers((player) -> player.level() != null).forEach((player) -> {
                FriendlyByteBuf buf = PacketByteBufs.create();
                ServerLevelData levelData = (ServerLevelData) level.getLevelData();
                int rainTime = levelData.getClearWeatherTime() > 0 ? levelData.getClearWeatherTime() : levelData.getRainTime();
                buf.writeInt(rainTime);
                ServerPlayNetworking.send(player, GNetwork.BAROMETER_INFO, buf);
            });
        });
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
                world.playSound(null, pos, GSoundEvents.LUMIERE_COMPOST, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return InteractionResult.SUCCESS;
            }
            else {
                return InteractionResult.PASS;
            }
        });
    }

    private static void registerLootTableEvents() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(EntityType.PILLAGER.getDefaultLootTable()) && GalosphereConfig.pillagerDropSilverIngot) {
                tableBuilder.pool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.SILVER_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).build());
            }
            if (id.equals(BuiltInLootTables.ANCIENT_CITY) && GalosphereConfig.spectreFlareAncientCityLoot) {
                tableBuilder.pool(LootPool.lootPool().add(LootItem.lootTableItem(GItems.SPECTRE_FLARE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))).build());
            }
            if (id.equals(BuiltInLootTables.ABANDONED_MINESHAFT) || id.equals(BuiltInLootTables.PILLAGER_OUTPOST)) {
                tableBuilder.pool(LootPool.lootPool().add(LootItem.lootTableItem(GItems.SILVER_UPGRADE_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))).build());
            }
        });
    }

}
