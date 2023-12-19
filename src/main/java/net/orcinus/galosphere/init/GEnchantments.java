package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.enchantments.LeveledSaltboundEnchantment;
import net.orcinus.galosphere.enchantments.SaltboundEnchantment;

import java.util.Map;

public class GEnchantments {

    public static final Map<ResourceLocation, Enchantment> ENCHANTMENTS = Maps.newLinkedHashMap();

    public static final Enchantment ENFEEBLE = register("enfeeble", new SaltboundEnchantment());
    public static final Enchantment SUSTAIN = register("sustain", new LeveledSaltboundEnchantment());
    public static final Enchantment RUPTURE = register("rupture", new LeveledSaltboundEnchantment());

    private static <E extends Enchantment> E register(String languish, E enchantment) {
        ENCHANTMENTS.put(Galosphere.id(languish), enchantment);
        return enchantment;
    }

    public static void init() {
        ENCHANTMENTS.forEach((resourceLocation, enchantment) -> {
            Registry.register(BuiltInRegistries.ENCHANTMENT, resourceLocation, enchantment);
        });
    }

}
