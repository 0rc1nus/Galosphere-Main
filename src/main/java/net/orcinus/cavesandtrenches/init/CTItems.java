package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.items.DefaultBombItem;
import net.orcinus.cavesandtrenches.items.SterlingArmorItem;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CavesAndTrenches.MODID);

    public static final RegistryObject<Item> ALLURITE_SHARD = registerBaseItem("allurite_shard");
    public static final RegistryObject<Item> LUMIERE_SHARD = registerBaseItem("lumiere_shard");
    public static final RegistryObject<Item> SILVER_INGOT = registerBaseItem("silver_ingot");
    public static final RegistryObject<Item> RAW_SILVER = registerBaseItem("raw_silver");
    public static final RegistryObject<Item> SILVER_NUGGET = registerBaseItem("silver_nugget");
    public static final RegistryObject<Item> SILVER_BOMB = ITEMS.register("silver_bomb", () -> new DefaultBombItem(new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(16)));
    public static final RegistryObject<Item> STERLING_HELMET = ITEMS.register("sterling_helmet", () -> new SterlingArmorItem(EquipmentSlot.HEAD, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_CHESTPLATE = ITEMS.register("sterling_chestplate", () -> new SterlingArmorItem(EquipmentSlot.CHEST, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_LEGGINGS = ITEMS.register("sterling_leggings", () -> new SterlingArmorItem(EquipmentSlot.LEGS, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_BOOTS = ITEMS.register("sterling_boots", () -> new SterlingArmorItem(EquipmentSlot.FEET, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES).stacksTo(1)));
    public static final RegistryObject<Item> SPARKLE_SPAWN_EGG = ITEMS.register("sparkle_spawn_egg", () -> new ForgeSpawnEggItem(CTEntityTypes.SPARKLE, 0xFFFFF, 0xFFFFF, new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));
//    public static final RegistryObject<Item> FLOW_LICHEN = ITEMS.register("flow_lichen", () -> new WaterLilyBlockItem(CTBlocks.FLOW_LICHEN.get(), new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));
//    public static final RegistryObject<Item> ROOT_LICHEN = ITEMS.register("root_lichen", () -> new WaterLilyBlockItem(CTBlocks.LICHEN_ROOT.get(), new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));

    public static RegistryObject<Item> registerBaseItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(CavesAndTrenches.CAVESANDTRENCHES)));
    }

}