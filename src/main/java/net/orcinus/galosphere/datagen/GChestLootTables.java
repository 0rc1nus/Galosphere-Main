package net.orcinus.galosphere.datagen;

import net.minecraft.data.loot.packs.VanillaChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GBuiltinLootTables;
import net.orcinus.galosphere.init.GEnchantments;
import net.orcinus.galosphere.init.GItems;

import java.util.function.BiConsumer;

public class GChestLootTables extends VanillaChestLoot {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(GBuiltinLootTables.PINK_SALT_SHRINE_CHEST, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 7.0F))
                        .add(LootItem.lootTableItem(GBlocks.PINK_SALT_CHAMBER.get().asItem())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .setWeight(10)
                        ).add(LootItem.lootTableItem(Items.PHANTOM_MEMBRANE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F)))
                                .setWeight(30))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F)))
                                .setWeight(25))
                        .add(LootItem.lootTableItem(GItems.SUCCULENT_PETALS.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                                .setWeight(15))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .setWeight(1))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .setWeight(15)
                                .apply(new EnchantRandomlyFunction.Builder().withEnchantment(GEnchantments.SUSTAIN.get()).withEnchantment(GEnchantments.ENFEEBLE.get()).withEnchantment(GEnchantments.RUPTURE.get()).withEnchantment(Enchantments.UNBREAKING)))
                )
        );
        consumer.accept(GBuiltinLootTables.PINK_SALT_SHRINE_LIBRARY_CHEST, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .setWeight(15))
                        .add(LootItem.lootTableItem(Items.PAPER)
                                .setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                        ).add(LootItem.lootTableItem(Items.BOOK)
                                .setWeight(25)
                                .apply(new EnchantRandomlyFunction.Builder().withEnchantment(GEnchantments.SUSTAIN.get()).withEnchantment(GEnchantments.ENFEEBLE.get()).withEnchantment(GEnchantments.RUPTURE.get()).withEnchantment(Enchantments.UNBREAKING)))
                ));
    }

}
