package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GEntityTypeTags;
import net.orcinus.galosphere.init.GEntityTypes;
import org.jetbrains.annotations.Nullable;

public class GEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public GEntityTypeTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(GEntityTypeTags.STERLING_IMMUNE_ENTITY_TYPES).add(EntityType.VEX).add(EntityType.EVOKER_FANGS).add(EntityType.RAVAGER);
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(GEntityTypes.SIVLER_BOMB.get());
    }

}
