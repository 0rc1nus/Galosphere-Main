package net.orcinus.galosphere.init;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.orcinus.galosphere.crafting.MonstrometerDispenseItemBehavior;
import net.orcinus.galosphere.crafting.GlowFlareDispenseItemBehavior;
import net.orcinus.galosphere.crafting.LumiereComposterDispenseItemBehavior;
import net.orcinus.galosphere.crafting.PickaxeDispenseItemBehavior;
import net.orcinus.galosphere.crafting.WarpedAnchorDispenseItemBehavior;

public class GVanillaIntegration {

    public static void init() {
        GVanillaIntegration.registerCompostables();
        GVanillaIntegration.registerDispenserBehaviors();
    }

    public static void registerCompostables() {
        CompostingChanceRegistry instance = CompostingChanceRegistry.INSTANCE;
        Util.make(ImmutableMap.<Block, Float>builder(), map -> {
            map.put(GBlocks.LICHEN_MOSS, 0.85F);
            map.put(GBlocks.BOWL_LICHEN, 0.65F);
            map.put(GBlocks.LICHEN_ROOTS, 0.3F);
            map.put(GBlocks.LICHEN_SHELF, 0.45F);
            map.put(GBlocks.LICHEN_CORDYCEPS, 0.4F);
        }).build().forEach(instance::add);
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new MonstrometerDispenseItemBehavior());
        DispenserBlock.registerBehavior(GBlocks.ALLURITE_BLOCK.asItem(), new WarpedAnchorDispenseItemBehavior());
        DispenserBlock.registerBehavior(GItems.LUMIERE_SHARD, new LumiereComposterDispenseItemBehavior());

        DispenserBlock.registerBehavior(GItems.GLOW_FLARE, new GlowFlareDispenseItemBehavior());

        Registry.ITEM.getTagOrEmpty(ItemTags.CLUSTER_MAX_HARVESTABLES).iterator().forEachRemaining(holder -> DispenserBlock.registerBehavior(holder.value(), new PickaxeDispenseItemBehavior()));
    }

}
