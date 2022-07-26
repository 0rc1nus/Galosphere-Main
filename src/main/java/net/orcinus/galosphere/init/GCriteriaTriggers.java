package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.orcinus.galosphere.criterion.GCriterion;

public class GCriteriaTriggers {
    public static GCriterion LUMIERE_COMPOST;
    public static GCriterion WARPED_TELEPORT;

    public static void init() {
        LUMIERE_COMPOST = CriterionRegistry.register(new GCriterion("lumiere_compost"));
        WARPED_TELEPORT = CriterionRegistry.register(new GCriterion("warped_teleport"));
    }

}
