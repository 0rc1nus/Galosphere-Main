package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;

import java.util.function.BiConsumer;

public class GEntityLootTableProvider extends SimpleFabricLootTableProvider {

    public GEntityLootTableProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        biConsumer.accept(GEntityTypes.SPARKLE.getDefaultLootTable(), LootTable.lootTable());
        biConsumer.accept(GEntityTypes.SPECTRE.getDefaultLootTable(), LootTable.lootTable());
        biConsumer.accept(GEntityTypes.SPECTERPILLAR.getDefaultLootTable(), LootTable.lootTable());
        biConsumer.accept(GEntityTypes.BERSERKER.getDefaultLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.PRESERVING_TEMPLATE)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.PRESERVED_FLESH))));
    }

}
