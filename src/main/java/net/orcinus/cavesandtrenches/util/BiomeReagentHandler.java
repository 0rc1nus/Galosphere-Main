package net.orcinus.cavesandtrenches.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.orcinus.cavesandtrenches.CavesAndTrenches;

import java.util.function.Consumer;

public class BiomeReagentHandler {
    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    public static final Climate.Parameter CAVE_BIOME_RANGE = Climate.Parameter.span(0.2F, 0.9F);

    public static ResourceKey<Biome> CRYSTAL_CANYONS;
//    public static final Climate.ParameterPoint CRYSTAL_CANYONS_PARAMETER = Climate.parameters(FULL_RANGE, Climate.Parameter.span(0.4F, 1.0F), Climate.Parameter.span(0.4F, 0.45F), FULL_RANGE, CAVE_BIOME_RANGE, FULL_RANGE, 0.0F);
    public static final Climate.ParameterPoint CRYSTAL_CANYONS_PARAMETER = Climate.parameters(Climate.Parameter.span(-1.0F, -0.4F), FULL_RANGE, Climate.Parameter.span(-1.0F, -0.8F), FULL_RANGE, CAVE_BIOME_RANGE, FULL_RANGE, 0.0F);

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {

        if (CRYSTAL_CANYONS == null)
            CRYSTAL_CANYONS = registerResourceKey("crystal_canyons");

        consumer.accept(Pair.of(CRYSTAL_CANYONS_PARAMETER, CRYSTAL_CANYONS));
    }

    private static ResourceKey<Biome> registerResourceKey(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CavesAndTrenches.MODID, name));
    }

}
