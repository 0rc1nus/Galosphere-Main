package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.items.ChandelierItem;
import net.orcinus.galosphere.items.GlowFlareItem;
import net.orcinus.galosphere.items.GoldenLichenCordycepsItem;
import net.orcinus.galosphere.items.IconItem;
import net.orcinus.galosphere.items.LichenCordycepsItem;
import net.orcinus.galosphere.items.SilverBombItem;
import net.orcinus.galosphere.items.SpectreBottleItem;
import net.orcinus.galosphere.items.SpectreBoundSpyglassItem;
import net.orcinus.galosphere.items.SpectreFlareItem;
import net.orcinus.galosphere.items.SterlingArmorItem;

import java.util.Map;

public class GItems {

    public static final Map<ResourceLocation, Item> ITEMS = Maps.newLinkedHashMap();

    public static final Item ICON_ITEM = register("icon_item", new IconItem(new Item.Properties().stacksTo(0)));

    public static final Item SPARKLE_SPAWN_EGG = register("sparkle_spawn_egg", new SpawnEggItem(GEntityTypes.SPARKLE, 0xF0F5F4, 0x24F6D8, new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item SPECTRE_SPAWN_EGG = register("spectre_spawn_egg", new SpawnEggItem(GEntityTypes.SPECTRE, 0xFFF3DD, 0x9CCDB6, new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item SPECTERPILLAR_SPAWN_EGG = register("specterpillar_spawn_egg", new SpawnEggItem(GEntityTypes.SPECTERPILLAR, 0xFFF3DD, 0xF7CF7B, new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item BOTTLE_OF_SPECTRE = register("bottle_of_spectre", new SpectreBottleItem(new Item.Properties().stacksTo(1).tab(Galosphere.GALOSPHERE)));
    public static final Item ALLURITE_SHARD = registerBaseItem("allurite_shard");
    public static final Item LUMIERE_SHARD = registerBaseItem("lumiere_shard");
    public static final Item RAW_SILVER = registerBaseItem("raw_silver");
    public static final Item SILVER_INGOT = registerBaseItem("silver_ingot");
    public static final Item SILVER_NUGGET = registerBaseItem("silver_nugget");
    public static final Item SILVER_BOMB = register("silver_bomb", new SilverBombItem(new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(16)));
    public static final Item BAROMETER = registerBaseItem("barometer");
    public static final Item STERLING_HELMET = register("sterling_helmet", new SterlingArmorItem(EquipmentSlot.HEAD, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_CHESTPLATE = register("sterling_chestplate", new SterlingArmorItem(EquipmentSlot.CHEST, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_LEGGINGS = register("sterling_leggings", new SterlingArmorItem(EquipmentSlot.LEGS, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_BOOTS = register("sterling_boots", new SterlingArmorItem(EquipmentSlot.FEET, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_HORSE_ARMOR = register("sterling_horse_armor", new HorseArmorItem(4, "sterling", new Item.Properties().stacksTo(1).tab(Galosphere.GALOSPHERE)));
    public static final Item LICHEN_CORDYCEPS = register("lichen_cordyceps", new LichenCordycepsItem(GBlocks.LICHEN_CORDYCEPS, new Item.Properties().food(GFoods.LICHEN_CORDYCEPS).tab(Galosphere.GALOSPHERE)));
    public static final Item GOLDEN_LICHEN_CORDYCEPS = register("golden_lichen_cordyceps", new GoldenLichenCordycepsItem(new Item.Properties().food(GFoods.GOLDEN_LICHEN_CORDYCEPS).tab(Galosphere.GALOSPHERE)));
    public static final Item GLOW_FLARE = register("glow_flare", new GlowFlareItem(new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item SPECTRE_FLARE = register("spectre_flare", new SpectreFlareItem(new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item SPECTRE_BOUND_SPYGLASS = register("spectre_bound_spyglass", new SpectreBoundSpyglassItem(new Item.Properties().stacksTo(1)));
    public static final Item CHANDELIER = register("chandelier", new ChandelierItem(GBlocks.CHANDELIER, new Item.Properties().tab(Galosphere.GALOSPHERE)));

    public static Item registerBaseItem(String name) {
        return register(name, new Item(new Item.Properties().tab(Galosphere.GALOSPHERE)));
    }

    public static <I extends Item> I register(String name, I item) {
        GItems.ITEMS.put(Galosphere.id(name), item);
        return item;
    }

    public static void init() {
        GItems.ITEMS.forEach((resourceLocation, item) -> Registry.register(Registry.ITEM, resourceLocation, item));
        GBlocks.BLOCK_ITEMS.forEach((resourceLocation, item) -> Registry.register(Registry.ITEM, resourceLocation, item));
    }

}