package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.orcinus.galosphere.blocks.PollinatedClusterBlock;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;

public class GBlockLootTableProvider extends FabricBlockLootTableProvider {

    public GBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        this.add(GBlocks.SILVER_ORE, (block) -> createOreDrop(block, GItems.RAW_SILVER));
        this.add(GBlocks.DEEPSLATE_SILVER_ORE, (block) -> createOreDrop(block, GItems.RAW_SILVER));
        this.dropSelf(GBlocks.STRANDED_MEMBRANE_BLOCK);
        this.dropSelf(GBlocks.CHARGED_LUMIERE_BLOCK);
        this.dropSelf(GBlocks.LUMIERE_BLOCK);
        this.dropSelf(GBlocks.ALLURITE_BLOCK);
        this.dropSelf(GBlocks.RAW_SILVER_BLOCK);
        this.dropSelf(GBlocks.SILVER_BLOCK);
        this.dropSelf(GBlocks.AMETHYST_STAIRS);
        this.dropSlab(GBlocks.AMETHYST_SLAB);
        this.dropSelf(GBlocks.ALLURITE_STAIRS);
        this.dropSlab(GBlocks.ALLURITE_SLAB);
        this.dropSelf(GBlocks.LUMIERE_STAIRS);
        this.dropSlab(GBlocks.LUMIERE_SLAB);
        this.dropSelf(GBlocks.SMOOTH_AMETHYST);
        this.dropSelf(GBlocks.SMOOTH_AMETHYST_STAIRS);
        this.dropSlab(GBlocks.SMOOTH_AMETHYST_SLAB);
        this.dropSelf(GBlocks.AMETHYST_BRICKS);
        this.dropSelf(GBlocks.AMETHYST_BRICK_STAIRS);
        this.dropSlab(GBlocks.AMETHYST_BRICK_SLAB);
        this.dropSelf(GBlocks.CHISELED_AMETHYST);
        this.add(GBlocks.ALLURITE_CLUSTER, (block) -> dropAlternativeWithSilkTouch(block, GBlocks.GLINTED_ALLURITE_CLUSTER, LootItem.lootTableItem(GItems.ALLURITE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.LUMIERE_CLUSTER, (block) -> dropAlternativeWithSilkTouch(block, GBlocks.GLINTED_LUMIERE_CLUSTER, LootItem.lootTableItem(GItems.LUMIERE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.GLINTED_ALLURITE_CLUSTER, (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.ALLURITE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.GLINTED_LUMIERE_CLUSTER, (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(GItems.LUMIERE_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.add(GBlocks.GLINTED_AMETHYST_CLUSTER, (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AMETHYST_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(Items.AMETHYST_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        this.dropSelf(GBlocks.MONSTROMETER);
        this.dropSelf(GBlocks.LUMIERE_LAMP);
        this.dropSelf(GBlocks.ALLURITE_LAMP);
        this.dropSelf(GBlocks.AMETHYST_LAMP);
        this.dropSelf(GBlocks.WARPED_ANCHOR);
        this.dropOther(GBlocks.LUMIERE_COMPOSTER, Blocks.COMPOSTER);
        this.dropOther(GBlocks.SALINE_COMPOSTER, Blocks.COMPOSTER);
        this.dropSelf(GBlocks.COMBUSTION_TABLE);
        this.dropSelf(GBlocks.SMOOTH_ALLURITE);
        this.dropSlab(GBlocks.SMOOTH_ALLURITE_SLAB);
        this.dropSelf(GBlocks.SMOOTH_ALLURITE_STAIRS);
        this.dropSelf(GBlocks.ALLURITE_BRICKS);
        this.dropSelf(GBlocks.CHISELED_ALLURITE);
        this.dropSelf(GBlocks.SMOOTH_LUMIERE);
        this.dropSlab(GBlocks.SMOOTH_LUMIERE_SLAB);
        this.dropSelf(GBlocks.SMOOTH_LUMIERE_STAIRS);
        this.dropSelf(GBlocks.LUMIERE_BRICKS);
        this.dropSelf(GBlocks.CHISELED_LUMIERE);
        this.dropSelf(GBlocks.ALLURITE_BRICK_STAIRS);
        this.dropSlab(GBlocks.ALLURITE_BRICK_SLAB);
        this.dropSelf(GBlocks.LUMIERE_BRICK_STAIRS);
        this.dropSlab(GBlocks.LUMIERE_BRICK_SLAB);
        this.dropSelf(GBlocks.LICHEN_MOSS);
        this.dropSelf(GBlocks.LICHEN_ROOTS);
        this.dropSelf(GBlocks.LICHEN_SHELF);
        this.dropSelf(GBlocks.BOWL_LICHEN);
        this.dropSelf(GBlocks.CHANDELIER);
        this.add(GBlocks.CHANDELIER, (block) -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        this.addVinesDroptable(GBlocks.LICHEN_CORDYCEPS, GBlocks.LICHEN_CORDYCEPS_PLANT);
        this.add(GBlocks.GLOW_INK_CLUMPS, this::createMultifaceBlockDrops);
        this.dropPottedContents(GBlocks.POTTED_BOWL_LICHEN);
        this.dropPottedContents(GBlocks.POTTED_LICHEN_ROOTS);
        this.dropSelf(GBlocks.SILVER_TILES);
        this.dropSelf(GBlocks.SILVER_TILES_STAIRS);
        this.dropSlab(GBlocks.SILVER_TILES_SLAB);
        this.dropSelf(GBlocks.SILVER_PANEL);
        this.dropSelf(GBlocks.SILVER_PANEL_STAIRS);
        this.dropSlab(GBlocks.SILVER_PANEL_SLAB);
        this.dropSelf(GBlocks.SILVER_LATTICE);
        this.dropOther(GBlocks.GLOW_BERRIES_SILVER_LATTICE, GBlocks.SILVER_LATTICE);
        this.dropSelf(GBlocks.PINK_SALT);
        this.dropSelf(GBlocks.ROSE_PINK_SALT);
        this.dropSelf(GBlocks.PASTEL_PINK_SALT);
        this.dropSelf(GBlocks.POLISHED_PINK_SALT);
        this.dropSelf(GBlocks.POLISHED_ROSE_PINK_SALT);
        this.dropSelf(GBlocks.POLISHED_PASTEL_PINK_SALT);
        this.dropSelf(GBlocks.PINK_SALT_BRICKS);
        this.dropSelf(GBlocks.ROSE_PINK_SALT_BRICKS);
        this.dropSelf(GBlocks.PASTEL_PINK_SALT_BRICKS);
        this.dropSlab(GBlocks.PINK_SALT_SLAB);
        this.dropSlab(GBlocks.ROSE_PINK_SALT_SLAB);
        this.dropSlab(GBlocks.PASTEL_PINK_SALT_SLAB);
        this.dropSlab(GBlocks.POLISHED_PINK_SALT_SLAB);
        this.dropSlab(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB);
        this.dropSlab(GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB);
        this.dropSlab(GBlocks.PINK_SALT_BRICK_SLAB);
        this.dropSlab(GBlocks.ROSE_PINK_SALT_BRICK_SLAB);
        this.dropSlab(GBlocks.PASTEL_PINK_SALT_BRICK_SLAB);
        this.dropSelf(GBlocks.PINK_SALT_STAIRS);
        this.dropSelf(GBlocks.ROSE_PINK_SALT_STAIRS);
        this.dropSelf(GBlocks.PASTEL_PINK_SALT_STAIRS);
        this.dropSelf(GBlocks.POLISHED_PINK_SALT_STAIRS);
        this.dropSelf(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS);
        this.dropSelf(GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS);
        this.dropSelf(GBlocks.PINK_SALT_BRICK_STAIRS);
        this.dropSelf(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS);
        this.dropSelf(GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS);
        this.dropSelf(GBlocks.PINK_SALT_WALL);
        this.dropSelf(GBlocks.ROSE_PINK_SALT_WALL);
        this.dropSelf(GBlocks.PASTEL_PINK_SALT_WALL);
        this.dropSelf(GBlocks.POLISHED_PINK_SALT_WALL);
        this.dropSelf(GBlocks.POLISHED_ROSE_PINK_SALT_WALL);
        this.dropSelf(GBlocks.POLISHED_PASTEL_PINK_SALT_WALL);
        this.dropSelf(GBlocks.PINK_SALT_BRICK_WALL);
        this.dropSelf(GBlocks.ROSE_PINK_SALT_BRICK_WALL);
        this.dropSelf(GBlocks.PASTEL_PINK_SALT_BRICK_WALL);
        this.dropSelf(GBlocks.CHISELED_PINK_SALT);
        this.dropSelf(GBlocks.CHISELED_ROSE_PINK_SALT);
        this.dropSelf(GBlocks.CHISELED_PASTEL_PINK_SALT);
        this.dropSelf(GBlocks.SHADOW_FRAME);
        this.dropSelf(GBlocks.PINK_SALT_LAMP);
        this.dropSelf(GBlocks.SUCCULENT);
        this.dropSelf(GBlocks.PINK_SALT_STRAW);
        this.dropSelf(GBlocks.CURED_MEMBRANE_BLOCK);
        this.add(GBlocks.PINK_SALT_CLUSTER, (block) -> {
            return createSilkTouchDispatchTable(block, LootItem.lootTableItem(GItems.PINK_SALT_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(GItems.PINK_SALT_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
        });
        this.dropSelf(GBlocks.GILDED_BEADS);
    }

    protected static LootTable.Builder dropAlternativeWithSilkTouch(Block block, Block alternative, LootPoolEntryContainer.Builder<?> builder) {
        LootPool.Builder lootPool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(
                        LootItem.lootTableItem(alternative)
                                .when(HAS_SILK_TOUCH)
                                .when(LootItemBlockStatePropertyCondition.
                                        hasBlockStateProperties(block).
                                        setProperties(
                                                StatePropertiesPredicate.Builder.
                                                        properties().
                                                        hasProperty(PollinatedClusterBlock.POLLINATED, true)
                                        ))
                                .otherwise(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH))
                                .otherwise(builder)
                );
        return LootTable.lootTable().withPool(lootPool);
    }

    public LootTable.Builder createMultifaceBlockDrops(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(applyExplosionDecay(block, LootItem.lootTableItem(block).apply(Direction.values(), direction -> SetItemCountFunction.setCount(ConstantValue.exactly(1.0f), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MultifaceBlock.getFaceProperty(direction), true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0f), true)))));
    }

    private void addVinesDroptable(Block block, Block plantBlock) {
        LootTable.Builder builder = createSilkTouchOrShearsDispatchTable(block, LootItem.lootTableItem(block).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
        this.add(block, builder);
        this.add(plantBlock, builder);
    }

    private void dropSlab(Block slab) {
        this.add(slab, this::createSlabItemTable);
    }

}
