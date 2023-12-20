package net.orcinus.galosphere.init;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.enchantments.LeveledSaltboundEnchantment;
import net.orcinus.galosphere.enchantments.SaltboundEnchantment;
import net.orcinus.galosphere.items.SaltboundTabletItem;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GEnchantments {

    public static final EnchantmentCategory SALTBOUND_TABLET = EnchantmentCategory.create("saltbound_tablet", SaltboundTabletItem.class::isInstance);

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Galosphere.MODID);

    public static final RegistryObject<Enchantment> ENFEEBLE = ENCHANTMENTS.register("enfeeble", SaltboundEnchantment::new);
    public static final RegistryObject<Enchantment> SUSTAIN = ENCHANTMENTS.register("sustain", LeveledSaltboundEnchantment::new);
    public static final RegistryObject<Enchantment> RUPTURE = ENCHANTMENTS.register("rupture", LeveledSaltboundEnchantment::new);

}
