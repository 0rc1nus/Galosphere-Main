package net.orcinus.galosphere.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.criterion.GCriterion;

@Mod.EventBusSubscriber(modid = Galosphere.MODID)
public class GCriteriaTriggers {

    public static final GCriterion LUMIERE_COMPOST = CriteriaTriggers.register(new GCriterion("lumiere_compost"));
    public static final GCriterion WARPED_TELEPORT = CriteriaTriggers.register(new GCriterion("warped_teleport"));
    public static final GCriterion USE_SPECTRE_SPYGLASS = CriteriaTriggers.register(new GCriterion("use_spectre_spyglass"));
    public static final GCriterion LIGHT_SPREAD = CriteriaTriggers.register(new GCriterion("light_spread"));
    public static final GCriterion USE_SPECTRE_FLARE = CriteriaTriggers.register(new GCriterion("use_spectre_flare"));
    public static final GCriterion ACTIVATE_PINK_SALT_CHAMBER = CriteriaTriggers.register(new GCriterion("activate_pink_salt_chamber"));
}
