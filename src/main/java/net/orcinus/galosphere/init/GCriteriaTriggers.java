package net.orcinus.galosphere.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.criterion.GCriterion;

@Mod.EventBusSubscriber(modid = Galosphere.MODID)
public class GCriteriaTriggers {

    public static final GCriterion LUMIERE_COMPOST = CriteriaTriggers.register(new GCriterion("lumiere_compost"));
    public static final GCriterion WARPED_TELEPORT = CriteriaTriggers.register(new GCriterion("warped_teleport"));

}
