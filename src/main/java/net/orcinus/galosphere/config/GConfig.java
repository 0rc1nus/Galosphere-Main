package net.orcinus.galosphere.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID)
public class GConfig {
    public static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON;
    public static final ForgeConfigSpec.BooleanValue speedReductionOnBuddingAmethyst;

    static {
        speedReductionOnBuddingAmethyst = builder.define("Reduces the speed when a player mines a budding amethyst", false);
        COMMON = builder.build();
    }

}
