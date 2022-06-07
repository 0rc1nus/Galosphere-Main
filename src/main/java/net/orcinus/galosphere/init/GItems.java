package net.orcinus.galosphere.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.items.CTHorseArmorItem;
import net.orcinus.galosphere.items.IconItem;
import net.orcinus.galosphere.items.SilverBombItem;
import net.orcinus.galosphere.items.SterlingArmorItem;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Galosphere.MODID);

    public static final RegistryObject<Item> ALLURITE_SHARD = registerBaseItem("allurite_shard");
    public static final RegistryObject<Item> LUMIERE_SHARD = registerBaseItem("lumiere_shard");
    public static final RegistryObject<Item> SILVER_INGOT = registerBaseItem("silver_ingot");
    public static final RegistryObject<Item> RAW_SILVER = registerBaseItem("raw_silver");
    public static final RegistryObject<Item> SILVER_NUGGET = registerBaseItem("silver_nugget");
    public static final RegistryObject<Item> SILVER_BOMB = ITEMS.register("silver_bomb", () -> new SilverBombItem(new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(16)));
    public static final RegistryObject<Item> STERLING_HELMET = ITEMS.register("sterling_helmet", () -> new SterlingArmorItem(EquipmentSlot.HEAD, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_CHESTPLATE = ITEMS.register("sterling_chestplate", () -> new SterlingArmorItem(EquipmentSlot.CHEST, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_LEGGINGS = ITEMS.register("sterling_leggings", () -> new SterlingArmorItem(EquipmentSlot.LEGS, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_BOOTS = ITEMS.register("sterling_boots", () -> new SterlingArmorItem(EquipmentSlot.FEET, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final RegistryObject<Item> STERLING_HORSE_ARMOR = ITEMS.register("sterling_horse_armor", () -> new CTHorseArmorItem(4, "sterling"));
    public static final RegistryObject<Item> SPARKLE_SPAWN_EGG = ITEMS.register("sparkle_spawn_egg", () -> new ForgeSpawnEggItem(GEntityTypes.SPARKLE, 16777215, 2422488, new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final RegistryObject<Item> ICON_ITEM = ITEMS.register("icon_item", () -> new IconItem(new Item.Properties().stacksTo(0)));

    public static RegistryObject<Item> registerBaseItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(Galosphere.GALOSPHERE)));
    }

}
