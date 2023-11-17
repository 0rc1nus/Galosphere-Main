package net.orcinus.galosphere.datagen;

import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GItems;

import java.util.stream.Stream;

public class GEntityLootTables extends VanillaEntityLoot {

    @Override
    public void generate() {
        this.add(GEntityTypes.SPARKLE.get(), LootTable.lootTable());
        this.add(GEntityTypes.SPECTRE.get(), LootTable.lootTable());
        this.add(GEntityTypes.SPECTERPILLAR.get(), LootTable.lootTable());
        this.add(GEntityTypes.PRESERVED.get(), LootTable.lootTable());
        this.add(GEntityTypes.BERSERKER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.PRESERVED_TEMPLATE.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(GItems.PRESERVED_FLESH.get()))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return GEntityTypes.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
