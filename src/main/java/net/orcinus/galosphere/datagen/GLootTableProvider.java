package net.orcinus.galosphere.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GLootTableProvider extends LootTableProvider {

    public GLootTableProvider(PackOutput packoutput) {
        super(packoutput, Set.of(), ImmutableList.of(new SubProviderEntry(GBlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(new SubProviderEntry(GBlockLootTables::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
        map.forEach((resourceLocation, lootTable) -> {
            lootTable.validate(validationcontext);
        });
    }
}
