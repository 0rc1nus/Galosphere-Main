package net.orcinus.galosphere.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.orcinus.galosphere.init.GEntityTypeTags;
import net.orcinus.galosphere.init.GEntityTypes;

import java.util.concurrent.CompletableFuture;

public class GEntityTypeTagsProvider extends FabricTagProvider.EntityTypeTagProvider {

    public GEntityTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.getOrCreateTagBuilder(GEntityTypeTags.STERLING_IMMUNE_ENTITY_TYPES).add(EntityType.EVOKER_FANGS, EntityType.RAVAGER, EntityType.VEX);
        this.getOrCreateTagBuilder(EntityTypeTags.FROG_FOOD).add(GEntityTypes.SPECTERPILLAR);
        this.getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(GEntityTypes.SIVLER_BOMB);
    }
}
