package net.orcinus.galosphere.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.orcinus.galosphere.criterion.GCriterion;

public class GCriteriaTriggers {
    public static GCriterion LUMIERE_COMPOST;
    public static GCriterion WARPED_TELEPORT;
    public static GCriterion USE_SPECTRE_SPYGLASS;
    public static GCriterion LIGHT_SPREAD;
    public static GCriterion USE_SPECTRE_FLARE;
    public static GCriterion ACTIVATE_PINK_SALT_CHAMBER;

    public static void init() {
        LUMIERE_COMPOST = CriteriaTriggers.register(new GCriterion("lumiere_compost"));
        WARPED_TELEPORT = CriteriaTriggers.register(new GCriterion("warped_teleport"));
        USE_SPECTRE_SPYGLASS = CriteriaTriggers.register(new GCriterion("use_spectre_spyglass"));
        LIGHT_SPREAD = CriteriaTriggers.register(new GCriterion("light_spread"));
        USE_SPECTRE_FLARE = CriteriaTriggers.register(new GCriterion("use_spectre_flare"));
        ACTIVATE_PINK_SALT_CHAMBER = CriteriaTriggers.register(new GCriterion("activate_pink_salt_chamber"));
    }

}
