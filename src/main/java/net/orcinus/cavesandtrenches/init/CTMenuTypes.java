package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.Galosphere;
import net.orcinus.cavesandtrenches.client.gui.CombustionTableMenu;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Galosphere.MODID);

    public static final RegistryObject<MenuType<CombustionTableMenu>> COMBUSTION_TABLE = MENU_TYPES.register("combustion_table", () -> new MenuType<>(CombustionTableMenu::new));

}