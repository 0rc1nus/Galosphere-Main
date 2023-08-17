package net.orcinus.galosphere.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Galosphere.MODID);

    public static final RegistryObject<Potion> ASTRAL = POTIONS.register("astral", () -> new Potion(new MobEffectInstance(GMobEffects.ASTRAL.get(), 1800)));
    public static final RegistryObject<Potion> LONG_ASTRAL = POTIONS.register("long_astral", () -> new Potion("astral", new MobEffectInstance(GMobEffects.ASTRAL.get(), 3600)));

    public static void init() {
        PotionBrewing.addMix(Potions.AWKWARD, GItems.CURED_MEMBRANE.get(), ASTRAL.get());
        PotionBrewing.addMix(ASTRAL.get(), Items.REDSTONE, LONG_ASTRAL.get());
    }

}
