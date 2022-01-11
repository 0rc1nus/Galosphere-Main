package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

public class CTBiomeKeys {

    public static final ResourceKey<Biome> CRYSTAL_CANYONS_KEY = registerKeyBiome("crystal_canyons");

    public static ResourceKey<Biome> registerKeyBiome(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CavesAndTrenches.MODID, name));
    }

}
