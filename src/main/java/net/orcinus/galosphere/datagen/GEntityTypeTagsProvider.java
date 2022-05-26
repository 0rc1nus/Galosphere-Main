package net.orcinus.galosphere.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GEntityTypes;
import org.jetbrains.annotations.Nullable;

public class GEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public GEntityTypeTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Galosphere.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(GEntityTypes.SIVLER_BOMB.get());
    }

}
