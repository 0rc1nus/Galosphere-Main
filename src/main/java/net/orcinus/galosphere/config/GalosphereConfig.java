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
    public static ForgeConfigSpec.BooleanValue SPECTRE_FLARE_ANCIENT_CITY_LOOT;
    public static ForgeConfigSpec.BooleanValue SILVER_UPGRADE_TEMPLATES_LOOT;

    static {
        BUILDER = new ForgeConfigSpec.Builder();

        SLOWED_BUDDING_AMETHYST_MINING_SPEED = BUILDER.comment("Slows the mining speed of budding amethyst").define("slowedBuddingAmethystMiningSpeed", true);
        PILLAGER_DROP_SILVER_INGOT = BUILDER.comment("Adds silver ingot to pillager drops").define("pillagerDropSilverIngot", true);
        SPECTRE_FLARE_ANCIENT_CITY_LOOT = BUILDER.comment("Adds spectre flares to ancient city loot").define("spectreFlareAncientCityLoot", true);
        SILVER_UPGRADE_TEMPLATES_LOOT = BUILDER.comment("Adds Silver Upgrade Template to Abandoned Mineshafts or Pillager Outposts loot").define("silverUpgradeTemplatesLoot", true);

        COMMON = BUILDER.build();
    }

}
