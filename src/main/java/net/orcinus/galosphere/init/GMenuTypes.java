package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.world.inventory.MenuType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.gui.CombustionTableMenu;

public class GMenuTypes {

    public static MenuType<CombustionTableMenu> COMBUSTION_TABLE;

    public static void init() {
        COMBUSTION_TABLE = ScreenHandlerRegistry.registerSimple(Galosphere.id("combustion_table"), CombustionTableMenu::new);
    }

}