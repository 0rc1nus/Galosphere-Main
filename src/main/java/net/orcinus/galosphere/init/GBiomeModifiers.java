package net.orcinus.galosphere.init;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.GalosphereBiomeProvider;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Galosphere.MODID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> GALOSPHERE_BIOME_MODIFIER = BIOME_MODIFIERS.register("galosphere_biome_modifier", () -> Codec.unit(GalosphereBiomeProvider::new));

}
