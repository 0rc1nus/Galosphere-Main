package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.entities.SilverBombEntity;
import net.orcinus.cavesandtrenches.items.DefaultBombItem;
import net.orcinus.cavesandtrenches.items.SterlingArmorItem;
import net.orcinus.cavesandtrenches.util.RegistryHandler;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTItems {

    public static final RegistryHandler REGISTRY = CavesAndTrenches.REGISTRY;

    public static final RegistryObject<Item> ALLURITE_SHARD = REGISTRY.registerBaseItem("allurite_shard");
    public static final RegistryObject<Item> LUMIERE_SHARD = REGISTRY.registerBaseItem("lumiere_shard");
    public static final RegistryObject<Item> SILVER_INGOT = REGISTRY.registerBaseItem("silver_ingot");
    public static final RegistryObject<Item> RAW_SILVER = REGISTRY.registerBaseItem("raw_silver");
    public static final RegistryObject<Item> SILVER_NUGGET = REGISTRY.registerBaseItem("silver_nugget");
    public static final RegistryObject<Item> SILVER_BOMB = REGISTRY.registerItem("silver_bomb", () -> new DefaultBombItem(new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(16)));
    public static final RegistryObject<Item> STERLING_HELMET = REGISTRY.registerItem("sterling_helmet", () -> new SterlingArmorItem(EquipmentSlot.HEAD, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_CHESTPLATE = REGISTRY.registerItem("sterling_chestplate", () -> new SterlingArmorItem(EquipmentSlot.CHEST, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_LEGGINGS = REGISTRY.registerItem("sterling_leggings", () -> new SterlingArmorItem(EquipmentSlot.LEGS, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_BOOTS = REGISTRY.registerItem("sterling_boots", () -> new SterlingArmorItem(EquipmentSlot.FEET, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> SPARKLE_SPAWN_EGG = REGISTRY.registerItem("sparkle_spawn_egg", () -> new ForgeSpawnEggItem(CTEntityTypes.SPARKLE, 0xFFFFF, 0xFFFFF, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));

}
