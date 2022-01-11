package net.orcinus.cavesandtrenches.datagen;

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
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTItems;

import java.util.stream.Collectors;

public class CTBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.add(CTBlocks.SILVER_ORE.get(), (block) -> createOreDrop(block, CTItems.RAW_SILVER.get()));
        this.add(CTBlocks.DEEPSLATE_SILVER_ORE.get(), (block) -> createOreDrop(block, CTItems.RAW_SILVER.get()));
        dropSelf(CTBlocks.LUMEN_BLOSSOM.get());
        dropSelf(CTBlocks.GLOW_LICHEN_BLOCK.get());
        dropSelf(CTBlocks.LUMIERE_BLOCK.get());
        dropSelf(CTBlocks.ALLURITE_BLOCK.get());
        dropSelf(CTBlocks.RAW_SILVER_BLOCK.get());
        dropSelf(CTBlocks.SILVER_BLOCK.get());
        dropSelf(CTBlocks.POLISHED_AMETHYST.get());
        dropSelf(CTBlocks.POLISHED_AMETHYST_STAIRS.get());
        dropSelf(CTBlocks.POLISHED_AMETHYST_SLAB.get());
        dropSelf(CTBlocks.AMETHYST_BRICKS.get());
        dropSelf(CTBlocks.AMETHYST_BRICKS_STAIRS.get());
        dropSelf(CTBlocks.AMETHYST_BRICKS_SLAB.get());
        dropSelf(CTBlocks.CHISELED_AMETHYST.get());
        this.add(CTBlocks.ALLURITE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(CTItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(CTItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(CTBlocks.LUMIERE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(CTItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(CTItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        dropSelf(CTBlocks.AURA_LISTENER.get());
        dropSelf(CTBlocks.LUMIERE_LAMP.get());
        dropSelf(CTBlocks.WARPED_ANCHOR.get());
        dropSelf(CTBlocks.LUMIERE_COMPOSTER.get());
        dropSelf(CTBlocks.COMBUSTION_TABLE.get());
        dropSelf(CTBlocks.MYSTERIA_CINDERS.get());
        dropSelf(CTBlocks.MYSTERIA_LOG.get());
        this.add(CTBlocks.MYSTERIA_VINES.get(), CTBlockLootTables::createMysteriaVinesDrop);
        this.add(CTBlocks.MYSTERIA_VINES_PLANTS.get(), CTBlockLootTables::createMysteriaVinesDrop);
    }

    protected static LootTable.Builder createMysteriaVinesDrop(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(CTBlocks.MYSTERIA_VINES.get())));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CTBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
