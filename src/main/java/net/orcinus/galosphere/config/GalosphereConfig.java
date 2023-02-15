package net.orcinus.galosphere.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID)
public class GalosphereConfig {

    public static final ForgeConfigSpec.Builder BUILDER;
    public static ForgeConfigSpec COMMON;
    public static ForgeConfigSpec.BooleanValue SLOWED_BUDDING_AMETHYST_MINING_SPEED;
    public static ForgeConfigSpec.BooleanValue PILLAGER_DROP_SILVER_INGOT;

    static {
        BUILDER = new ForgeConfigSpec.Builder();

        SLOWED_BUDDING_AMETHYST_MINING_SPEED = BUILDER.comment("Slows the mining speed of budding amethyst").define("slowedBuddingAmethystMiningSpeed", true);
        PILLAGER_DROP_SILVER_INGOT = BUILDER.comment("Adds silver ingot to pillager drops").define("pillagerDropSilverIngot", true);

        COMMON = BUILDER.build();
    }

}
