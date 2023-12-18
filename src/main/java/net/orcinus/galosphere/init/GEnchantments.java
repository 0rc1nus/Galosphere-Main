package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.enchantments.LanguishEnchantment;
import net.orcinus.galosphere.enchantments.SustainEnchantment;

import java.util.Map;

public class GEnchantments {

    //TODO:
    // INFLICT SLOWNESS - LANGUISH
    // EXTENDS LONGER DURATION - SUSTAIN
    // EXPLODES INTO SHARDS WHEN RETRACTS - FRACTURE

    private static final Map<Enchantment, ResourceLocation> ENCHANTMENTS = Maps.newLinkedHashMap();

    public static final Enchantment LANGUISH = register("languish", new LanguishEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment SUSTAIN = register("sustain", new SustainEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment FRACTURE = register("fracture", new SustainEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));

    private static <E extends Enchantment> E register(String languish, E enchantment) {
        ENCHANTMENTS.put(enchantment, Galosphere.id(languish));
        return enchantment;
    }

    public static void init() {
        ENCHANTMENTS.forEach((enchantment, resourceLocation) -> {
            Registry.register(BuiltInRegistries.ENCHANTMENT, resourceLocation, enchantment);
        });
    }

}
