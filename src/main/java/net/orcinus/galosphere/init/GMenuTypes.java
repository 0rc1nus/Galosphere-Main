package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.gui.CombustionTableMenu;

public class GMenuTypes {

    public static MenuType<CombustionTableMenu> COMBUSTION_TABLE;

    public static void init() {
        COMBUSTION_TABLE = Registry.register(BuiltInRegistries.MENU, Galosphere.id("combustion_table"), new MenuType<>(CombustionTableMenu::new, FeatureFlags.VANILLA_SET));
    }
}