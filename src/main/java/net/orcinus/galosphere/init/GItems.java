package net.orcinus.galosphere.init;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.items.FayBottleItem;
import net.orcinus.galosphere.items.FayBoundedSpyglassItem;
import net.orcinus.galosphere.items.GHorseArmorItem;
import net.orcinus.galosphere.items.GlowFlareItem;
import net.orcinus.galosphere.items.GoldenLichenCordycepsItem;
import net.orcinus.galosphere.items.IconItem;
import net.orcinus.galosphere.items.LichenCordycepsItem;
import net.orcinus.galosphere.items.SilverBombItem;
import net.orcinus.galosphere.items.SterlingArmorItem;

public class GItems {

    public static final Map<ResourceLocation, Item> ITEMS = Maps.newLinkedHashMap();

    public static final Item ALLURITE_SHARD = registerBaseItem("allurite_shard");
    public static final Item LUMIERE_SHARD = registerBaseItem("lumiere_shard");
    public static final Item SILVER_INGOT = registerBaseItem("silver_ingot");
    public static final Item RAW_SILVER = registerBaseItem("raw_silver");
    public static final Item SILVER_NUGGET = registerBaseItem("silver_nugget");
    public static final Item SILVER_BOMB = register("silver_bomb", new SilverBombItem(new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(16)));
    public static final Item STERLING_HELMET = register("sterling_helmet", new SterlingArmorItem(EquipmentSlot.HEAD, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_CHESTPLATE = register("sterling_chestplate", new SterlingArmorItem(EquipmentSlot.CHEST, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_LEGGINGS = register("sterling_leggings", new SterlingArmorItem(EquipmentSlot.LEGS, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_BOOTS = register("sterling_boots", new SterlingArmorItem(EquipmentSlot.FEET, new Item.Properties().tab(Galosphere.GALOSPHERE).stacksTo(1)));
    public static final Item STERLING_HORSE_ARMOR = register("sterling_horse_armor", new GHorseArmorItem(4, "sterling"));
    public static final Item SPARKLE_SPAWN_EGG = register("sparkle_spawn_egg", new SpawnEggItem(GEntityTypes.SPARKLE, 16777215, 2422488, new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item FAY_SPAWN_EGG = register("fay_spawn_egg", new SpawnEggItem(GEntityTypes.FAY, 16777215, 10803902, new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item ICON_ITEM = register("icon_item", new IconItem(new Item.Properties().stacksTo(0)));
    public static final Item BOTTLE_OF_FAY = register("bottle_of_fay", new FayBottleItem(new Item.Properties().stacksTo(16).tab(Galosphere.GALOSPHERE)));
    public static final Item LICHEN_CORDYCEPS = register("lichen_cordyceps", new LichenCordycepsItem(GBlocks.LICHEN_CORDYCEPS, new Item.Properties().food(GFoods.LICHEN_CORDYCEPS).tab(Galosphere.GALOSPHERE)));
    public static final Item GOLDEN_LICHEN_CORDYCEPS = register("golden_lichen_cordyceps", new GoldenLichenCordycepsItem(new Item.Properties().food(GFoods.GOLDEN_LICHEN_CORDYCEPS).tab(Galosphere.GALOSPHERE)));
    public static final Item GLOW_FLARE = register("glow_flare", new GlowFlareItem(new Item.Properties().tab(Galosphere.GALOSPHERE)));
    public static final Item FAY_BOUNDED_SPYGLASS = register("fay_bounded_spyglass", new FayBoundedSpyglassItem(new Item.Properties().stacksTo(1)));

    public static <I extends Item> I register(String name, I item) {
        ITEMS.put(Galosphere.id(name), item);
        return item;
    }

    public static Item registerBaseItem(String name) {
        return register(name, new Item(new Item.Properties().tab(Galosphere.GALOSPHERE)));
    }

    public static void init() {
        for (ResourceLocation id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }

}
