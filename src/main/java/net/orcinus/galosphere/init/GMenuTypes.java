package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.gui.CombustionTableMenu;

public class GMenuTypes {

    public static MenuType<CombustionTableMenu> COMBUSTION_TABLE;

    public static void init() {
        COMBUSTION_TABLE = ScreenHandlerRegistry.registerSimple(new ResourceLocation(Galosphere.MODID, "combustion_table"), CombustionTableMenu::new);
    }

}