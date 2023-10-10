package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.DispenserBlock;
import net.orcinus.galosphere.crafting.GlowFlareDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.MonstrometerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.crafting.WarpedAnchorDispenseItemBehavior;

public class GVanillaIntegration {

    public static void init() {
        GVanillaIntegration.registerCompostables();
        GVanillaIntegration.registerDispenserBehaviors();
        GVanillaIntegration.registerBrewables();
    }

    public static void registerBrewables() {
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(GItems.CURED_MEMBRANE), GPotions.ASTRAL);
        FabricBrewingRecipeRegistry.registerPotionRecipe(GPotions.ASTRAL, Ingredient.of(Items.REDSTONE), GPotions.LONG_ASTRAL);
    }

    public static void registerCompostables() {
        CompostingChanceRegistry instance = CompostingChanceRegistry.INSTANCE;
        Util.make(ImmutableMap.<Item, Float>builder(), map -> {
            map.put(GBlocks.LICHEN_MOSS.asItem(), 0.85F);
            map.put(GBlocks.BOWL_LICHEN.asItem(), 0.65F);
            map.put(GBlocks.LICHEN_ROOTS.asItem(), 0.3F);
            map.put(GBlocks.LICHEN_SHELF.asItem(), 0.45F);
            map.put(GBlocks.LICHEN_CORDYCEPS.asItem(), 0.4F);
            map.put(GBlocks.SUCCULENT.asItem(), 0.65F);
            map.put(GItems.SUCCULENT_PETALS.asItem(), 0.23F);
            map.put(GItems.CURED_MEMBRANE, 0.8F);
            map.put(GItems.SALTED_JERKY, 0.8F);
        }).build().forEach(instance::add);
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new MonstrometerDispenseItemBehavior());
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new WarpedAnchorDispenseItemBehavior());

        DispenserBlock.registerBehavior(GItems.LUMIERE_SHARD, new LumiereComposterDispenseItemBehavior());
        DispenserBlock.registerBehavior(GItems.GLOW_FLARE, new GlowFlareDispenseItemBehavior());

        BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.CLUSTER_MAX_HARVESTABLES).iterator().forEachRemaining(holder -> DispenserBlock.registerBehavior(holder.value(), new PickaxeDispenseItemBehavior()));
    }

}
