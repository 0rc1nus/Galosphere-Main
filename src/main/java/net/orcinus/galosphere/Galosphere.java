package net.orcinus.galosphere;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.netty.util.concurrent.SucceededFuture;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.orcinus.galosphere.api.IBanner;
import net.orcinus.galosphere.blocks.LumiereComposterBlock;
import net.orcinus.galosphere.crafting.AuraRingerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereReformingManager;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.crafting.WarpedAnchorDispenseItemBehavior;
import net.orcinus.galosphere.entities.SparkleEntity;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GConfiguredFeatures;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GPlacedFeatures;
import net.orcinus.galosphere.util.BannerRendererUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Identifier;

public class Galosphere implements ModInitializer {
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "galosphere";
    public static final CreativeModeTab GALOSPHERE = FabricItemGroupBuilder.create(new ResourceLocation(MODID, MODID)).icon(() -> new ItemStack(GItems.ICON_ITEM)).build();

    @Override
    public void onInitialize() {
        GBlocks.init();
        GItems.init();
        GAttributes.init();
        GBiomes.init();
        GBlockEntityTypes.init();
        GFeatures.init();
        GConfiguredFeatures.init();
        GPlacedFeatures.init();
        GMenuTypes.init();
        GMobEffects.init();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new LumiereReformingManager());

        FabricDefaultAttributeRegistry.register(GEntityTypes.SPARKLE, SparkleEntity.createAttributes());

        Util.make(ImmutableList.<Holder<PlacedFeature>>builder(), map -> {
            map.add(GPlacedFeatures.ORE_SILVER_MIDDLE);
            map.add(GPlacedFeatures.ORE_SILVER_SMALL);
        }).build().forEach(featureHolder -> featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, placedFeatureResourceKey)));

        Util.make(ImmutableList.<Holder<PlacedFeature>>builder(), map -> {
            map.add(GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS);
            map.add(GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS);
            map.add(GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS);
            map.add(GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS);
            map.add(GPlacedFeatures.ALLURITE_CEILING_CRYSTALS);
            map.add(GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS);
            map.add(GPlacedFeatures.LUMIERE_CEILING_CRYSTALS);
            map.add(GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS);
        }).build().forEach(featureHolder -> featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatureResourceKey)));

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), MobCategory.MONSTER, EntityType.GLOW_SQUID, 120, 4, 6);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(EntityType.PILLAGER.getDefaultLootTable())) {
                tableBuilder.pool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.SILVER_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).build());
            }
        });

        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new AuraRingerDispenseItemBehavior());

        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new WarpedAnchorDispenseItemBehavior());

        DispenserBlock.registerBehavior(GItems.LUMIERE_SHARD, new LumiereComposterDispenseItemBehavior());

        Registry.ITEM.getTagOrEmpty(ItemTags.CLUSTER_MAX_HARVESTABLES).iterator().forEachRemaining(holder -> {
            DispenserBlock.registerBehavior(holder.value(), new PickaxeDispenseItemBehavior());
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (player.isShiftKeyDown() && !((IBanner)player).getBanner().isEmpty() && player.getItemInHand(hand).isEmpty()) {
                ItemStack copy = ((IBanner) player).getBanner();
                player.setItemInHand(hand, copy);
                player.gameEvent(GameEvent.EQUIP, player);
                ((IBanner) player).setBanner(ItemStack.EMPTY);
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

        UseItemCallback.EVENT.register((player, world, hand) -> {
            BannerRendererUtil util = new BannerRendererUtil();
            ItemStack stack = player.getItemInHand(hand);
            if (((IBanner) player).getBanner().isEmpty() && player.getItemBySlot(EquipmentSlot.HEAD).is(GItems.STERLING_HELMET) && (util.isTapestryStack(stack) || stack.getItem() instanceof BannerItem)) {
                player.gameEvent(GameEvent.EQUIP, player);
                ItemStack copy = stack.copy();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                copy.setCount(1);
                ((IBanner) player).setBanner(copy);
                player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                return InteractionResultHolder.success(stack);
            } else {
                return InteractionResultHolder.pass(ItemStack.EMPTY);
            }
        });

    }
}
