package net.orcinus.galosphere.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

import java.util.stream.Collectors;

public class GBlockLootTables extends BlockLoot {
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));

    @Override
    protected void addTables() {
        this.add(GBlocks.SILVER_ORE.get(), (block) -> createOreDrop(block, GItems.RAW_SILVER.get()));
        this.add(GBlocks.DEEPSLATE_SILVER_ORE.get(), (block) -> createOreDrop(block, GItems.RAW_SILVER.get()));
        dropSelf(GBlocks.CHARGED_LUMIERE_BLOCK.get());
        dropSelf(GBlocks.LUMIERE_BLOCK.get());
        dropSelf(GBlocks.ALLURITE_BLOCK.get());
        dropSelf(GBlocks.RAW_SILVER_BLOCK.get());
        dropSelf(GBlocks.SILVER_BLOCK.get());
        dropSelf(GBlocks.AMETHYST_STAIRS.get());
        dropSlab(GBlocks.AMETHYST_SLAB);
        dropSelf(GBlocks.ALLURITE_STAIRS.get());
        dropSlab(GBlocks.ALLURITE_SLAB);
        dropSelf(GBlocks.LUMIERE_STAIRS.get());
        dropSlab(GBlocks.LUMIERE_SLAB);
        dropSelf(GBlocks.SMOOTH_AMETHYST.get());
        dropSelf(GBlocks.SMOOTH_AMETHYST_STAIRS.get());
        dropSlab(GBlocks.SMOOTH_AMETHYST_SLAB);
        dropSelf(GBlocks.AMETHYST_BRICKS.get());
        dropSelf(GBlocks.AMETHYST_BRICK_STAIRS.get());
        dropSlab(GBlocks.AMETHYST_BRICK_SLAB);
        dropSelf(GBlocks.CHISELED_AMETHYST.get());
        this.add(GBlocks.ALLURITE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.LUMIERE_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        dropSelf(GBlocks.AURA_RINGER.get());
        dropSelf(GBlocks.LUMIERE_LAMP.get());
        dropSelf(GBlocks.ALLURITE_LAMP.get());
        dropSelf(GBlocks.AMETHYST_LAMP.get());
        dropSelf(GBlocks.WARPED_ANCHOR.get());
        this.add(GBlocks.LUMIERE_COMPOSTER.get(), LootTable.lootTable().withPool(applyExplosionCondition(Blocks.COMPOSTER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Blocks.COMPOSTER)))));
        dropSelf(GBlocks.COMBUSTION_TABLE.get());
        dropSelf(GBlocks.SMOOTH_ALLURITE.get());
        dropSlab(GBlocks.SMOOTH_ALLURITE_SLAB);
        dropSelf(GBlocks.SMOOTH_ALLURITE_STAIRS.get());
        dropSelf(GBlocks.ALLURITE_BRICKS.get());
        dropSelf(GBlocks.CHISELED_ALLURITE.get());
        dropSelf(GBlocks.SMOOTH_LUMIERE.get());
        dropSlab(GBlocks.SMOOTH_LUMIERE_SLAB);
        dropSelf(GBlocks.SMOOTH_LUMIERE_STAIRS.get());
        dropSelf(GBlocks.LUMIERE_BRICKS.get());
        dropSelf(GBlocks.CHISELED_LUMIERE.get());
        dropSelf(GBlocks.ALLURITE_BRICK_STAIRS.get());
        dropSlab(GBlocks.ALLURITE_BRICK_SLAB);
        dropSelf(GBlocks.LUMIERE_BRICK_STAIRS.get());
        dropSlab(GBlocks.LUMIERE_BRICK_SLAB);
        dropSelf(GBlocks.LICHEN_MOSS.get());
        dropSelf(GBlocks.LICHEN_ROOTS.get());
        dropSelf(GBlocks.LICHEN_SHELF.get());
        dropSelf(GBlocks.BOWL_LICHEN.get());
        dropSelf(GBlocks.CHANDELIER.get());
        this.addVinesDroptable(GBlocks.LICHEN_CORDYCEPS.get(), GBlocks.LICHEN_CORDYCEPS_PLANT.get());
        this.add(GBlocks.GLOW_INK_CLUMPS.get(), GBlockLootTables::createMultifaceBlockDrops);
        this.dropPottedContents(GBlocks.POTTED_BOWL_LICHEN.get());
        this.dropPottedContents(GBlocks.POTTED_LICHEN_ROOTS.get());
    }

    public static LootTable.Builder createMultifaceBlockDrops(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(applyExplosionDecay(block, LootItem.lootTableItem(block).when(HAS_SHEARS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.EAST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.WEST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.NORTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.SOUTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.UP, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.DOWN, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))));
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
