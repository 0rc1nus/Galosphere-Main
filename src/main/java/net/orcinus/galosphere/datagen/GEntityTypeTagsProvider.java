package net.orcinus.galosphere.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GEntityTypeTags;
import net.orcinus.galosphere.init.GEntityTypes;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public GEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(GEntityTypeTags.STERLING_IMMUNE_ENTITY_TYPES).add(EntityType.VEX).add(EntityType.EVOKER_FANGS).add(EntityType.RAVAGER);
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(GEntityTypes.SILVER_BOMB.get());
        this.tag(EntityTypeTags.FROG_FOOD).add(GEntityTypes.SPECTERPILLAR.get());
    }

}
