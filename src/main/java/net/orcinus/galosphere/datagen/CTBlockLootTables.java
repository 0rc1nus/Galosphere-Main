package net.orcinus.galosphere.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

import java.util.stream.Collectors;

public class CTBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.add(GBlocks.SILVER_ORE.get(), (block) -> createOreDrop(block, GItems.RAW_SILVER.get()));
        this.add(GBlocks.DEEPSLATE_SILVER_ORE.get(), (block) -> createOreDrop(block, GItems.RAW_SILVER.get()));
        dropSelf(GBlocks.LUMIERE_BLOCK.get());
        dropSelf(GBlocks.ALLURITE_BLOCK.get());
        dropSelf(GBlocks.RAW_SILVER_BLOCK.get());
        dropSelf(GBlocks.SILVER_BLOCK.get());
        dropSelf(GBlocks.AMETHYST_STAIRS.get());
        dropSelf(GBlocks.AMETHYST_SLAB.get());
        dropSelf(GBlocks.ALLURITE_STAIRS.get());
        dropSelf(GBlocks.ALLURITE_SLAB.get());
        dropSelf(GBlocks.LUMIERE_STAIRS.get());
        dropSelf(GBlocks.LUMIERE_SLAB.get());
        dropSelf(GBlocks.SMOOTH_AMETHYST.get());
        dropSelf(GBlocks.SMOOTH_AMETHYST_STAIRS.get());
        dropSelf(GBlocks.SMOOTH_AMETHYST_SLAB.get());
        dropSelf(GBlocks.AMETHYST_BRICKS.get());
        dropSelf(GBlocks.CHISELED_AMETHYST.get());
        this.add(GBlocks.ALLURITE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.LUMIERE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        dropSelf(GBlocks.AURA_TRANSMITTER.get());
        dropSelf(GBlocks.LUMIERE_LAMP.get());
        dropSelf(GBlocks.ALLURITE_LAMP.get());
        dropSelf(GBlocks.WARPED_ANCHOR.get());
        dropSelf(GBlocks.LUMIERE_COMPOSTER.get());
        dropSelf(GBlocks.MYSTERIA_CINDERS.get());
        dropSelf(GBlocks.MYSTERIA_LOG.get());
        dropSelf(GBlocks.COMBUSTION_TABLE.get());
        this.add(GBlocks.MYSTERIA_VINES.get(), CTBlockLootTables::createMysteriaVinesDrop);
        this.add(GBlocks.MYSTERIA_VINES_PLANTS.get(), CTBlockLootTables::createMysteriaVinesDrop);
    }

    protected static LootTable.Builder createMysteriaVinesDrop(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(GBlocks.MYSTERIA_VINES.get())));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return GBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
