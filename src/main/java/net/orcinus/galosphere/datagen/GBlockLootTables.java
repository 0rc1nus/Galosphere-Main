package net.orcinus.galosphere.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

import java.util.stream.Collectors;

public class GBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.add(GBlocks.SILVER_ORE.get(), (block) -> createOreDrop(block, GItems.RAW_SILVER.get()));
        this.add(GBlocks.DEEPSLATE_SILVER_ORE.get(), (block) -> createOreDrop(block, GItems.RAW_SILVER.get()));
        this.dropSelf(GBlocks.CHARGED_LUMIERE_BLOCK.get());
        this.dropSelf(GBlocks.LUMIERE_BLOCK.get());
        this.dropSelf(GBlocks.ALLURITE_BLOCK.get());
        this.dropSelf(GBlocks.RAW_SILVER_BLOCK.get());
        this.dropSelf(GBlocks.SILVER_BLOCK.get());
        this.dropSelf(GBlocks.AMETHYST_STAIRS.get());
        this.dropSlab(GBlocks.AMETHYST_SLAB);
        this.dropSelf(GBlocks.ALLURITE_STAIRS.get());
        this.dropSlab(GBlocks.ALLURITE_SLAB);
        this.dropSelf(GBlocks.LUMIERE_STAIRS.get());
        this.dropSlab(GBlocks.LUMIERE_SLAB);
        this.dropSelf(GBlocks.SMOOTH_AMETHYST.get());
        this.dropSelf(GBlocks.SMOOTH_AMETHYST_STAIRS.get());
        this.dropSlab(GBlocks.SMOOTH_AMETHYST_SLAB);
        this.dropSelf(GBlocks.AMETHYST_BRICKS.get());
        this.dropSelf(GBlocks.AMETHYST_BRICK_STAIRS.get());
        this.dropSlab(GBlocks.AMETHYST_BRICK_SLAB);
        this.dropSelf(GBlocks.CHISELED_AMETHYST.get());
        this.add(GBlocks.ALLURITE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.LUMIERE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.dropSelf(GBlocks.AURA_RINGER.get());
        this.dropSelf(GBlocks.LUMIERE_LAMP.get());
        this.dropSelf(GBlocks.ALLURITE_LAMP.get());
        this.dropSelf(GBlocks.AMETHYST_LAMP.get());
        this.dropSelf(GBlocks.WARPED_ANCHOR.get());
        this.add(GBlocks.LUMIERE_COMPOSTER.get(), LootTable.lootTable().withPool(applyExplosionCondition(Blocks.COMPOSTER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Blocks.COMPOSTER)))));
        this.dropSelf(GBlocks.COMBUSTION_TABLE.get());
        this.dropSelf(GBlocks.SMOOTH_ALLURITE.get());
        this.dropSlab(GBlocks.SMOOTH_ALLURITE_SLAB);
        this.dropSelf(GBlocks.SMOOTH_ALLURITE_STAIRS.get());
        this.dropSelf(GBlocks.ALLURITE_BRICKS.get());
        this.dropSelf(GBlocks.CHISELED_ALLURITE.get());
        this.dropSelf(GBlocks.SMOOTH_LUMIERE.get());
        this.dropSlab(GBlocks.SMOOTH_LUMIERE_SLAB);
        this.dropSelf(GBlocks.SMOOTH_LUMIERE_STAIRS.get());
        this.dropSelf(GBlocks.LUMIERE_BRICKS.get());
        this.dropSelf(GBlocks.CHISELED_LUMIERE.get());
        this.dropSelf(GBlocks.ALLURITE_BRICK_STAIRS.get());
        this.dropSlab(GBlocks.ALLURITE_BRICK_SLAB);
        this.dropSelf(GBlocks.LUMIERE_BRICK_STAIRS.get());
        this.dropSlab(GBlocks.LUMIERE_BRICK_SLAB);
        this.dropSelf(GBlocks.LICHEN_MOSS.get());
        this.dropSelf(GBlocks.LICHEN_ROOTS.get());
        this.dropSelf(GBlocks.LICHEN_SHELF.get());
        this.dropSelf(GBlocks.BOWL_LICHEN.get());
        this.dropSelf(GBlocks.CHANDELIER.get());
        this.addVinesDroptable(GBlocks.LICHEN_CORDYCEPS.get(), GBlocks.LICHEN_CORDYCEPS_PLANT.get());
    }

    private void addVinesDroptable(Block block, Block plantBlock) {
        LootTable.Builder loottable$builder = createSilkTouchOrShearsDispatchTable(block, LootItem.lootTableItem(block).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
        this.add(block, loottable$builder);
        this.add(plantBlock, loottable$builder);
    }

    private void dropSlab(RegistryObject<Block> slab) {
        this.add(slab.get(), BlockLoot::createSlabItemTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return GBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
