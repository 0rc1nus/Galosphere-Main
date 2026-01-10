package net.orcinus.galosphere.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class GalosphereConfig extends MidnightConfig {

    @MidnightConfig.Entry
    public static boolean slowBuddingAmethystDestroySpeed = true;
    @MidnightConfig.Entry
    public static boolean pillagerDropSilverIngot = true;
    @MidnightConfig.Entry
    public static boolean spectreFlareAncientCityLoot = true;
    @MidnightConfig.Entry
    public static boolean silverUpgradeTemplatesLoot = true;

}
