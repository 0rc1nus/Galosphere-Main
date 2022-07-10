package net.orcinus.galosphere;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.orcinus.galosphere.crafting.AuraRingerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.crafting.WarpedAnchorDispenseItemBehavior;
import net.orcinus.galosphere.init.GAttributes;
import net.orcinus.galosphere.init.GBiomes;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GConfiguredFeatures;
import net.orcinus.galosphere.init.GFeatures;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.init.GMobEffects;
import net.orcinus.galosphere.init.GPlacedFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        Util.make(ImmutableMap.<Holder<PlacedFeature>, GenerationStep.Decoration>builder(), map -> {
            map.put(GPlacedFeatures.ORE_SILVER_MIDDLE, GenerationStep.Decoration.UNDERGROUND_ORES);
            map.put(GPlacedFeatures.ORE_SILVER_SMALL, GenerationStep.Decoration.UNDERGROUND_ORES);
        }).build().forEach((featureHolder, decoration) -> {
            featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), decoration, placedFeatureResourceKey));
        });

        Util.make(ImmutableMap.<Holder<PlacedFeature>, GenerationStep.Decoration>builder(), map -> {
            map.put(GPlacedFeatures.LARGE_CEILING_ALLURITE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LARGE_FLOOR_ALLURITE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LARGE_CEILING_LUMIERE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LARGE_FLOOR_LUMIERE_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.ALLURITE_CEILING_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.ALLURITE_FLOOR_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LUMIERE_CEILING_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
            map.put(GPlacedFeatures.LUMIERE_FLOOR_CRYSTALS, GenerationStep.Decoration.VEGETAL_DECORATION);
        }).build().forEach((featureHolder, decoration) -> {
            featureHolder.unwrapKey().ifPresent(placedFeatureResourceKey -> BiomeModifications.addFeature(BiomeSelectors.includeByKey(GBiomes.CRYSTAL_CANYONS_KEY), decoration, placedFeatureResourceKey));
        });

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
    }
}
