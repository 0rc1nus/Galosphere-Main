package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.CrystalSpikeFeature;
import net.orcinus.galosphere.world.gen.features.LichenCordycepsColumnFeature;
import net.orcinus.galosphere.world.gen.features.LichenMushroomFeature;
import net.orcinus.galosphere.world.gen.features.LichenPatchFeature;
import net.orcinus.galosphere.world.gen.features.NoisePatchFeature;
import net.orcinus.galosphere.world.gen.features.OasisFeature;
import net.orcinus.galosphere.world.gen.features.PinkSaltStrawPatchFeature;
import net.orcinus.galosphere.world.gen.features.SurfaceOasisFeature;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;
import net.orcinus.galosphere.world.gen.features.config.NoisePatchConfig;
import net.orcinus.galosphere.world.gen.features.config.PinkSaltStrawPatchConfig;

import java.util.Map;

public class GFeatures {

    public static final Map<ResourceLocation, Feature<?>> FEATURES = Maps.newLinkedHashMap();

    public static final Feature<CrystalSpikeConfig> CRYSTAL_SPIKE = registerFeature("crystal_spike", new CrystalSpikeFeature(CrystalSpikeConfig.CODEC));
    public static final Feature<VegetationPatchConfiguration> LICHEN_PATCH = registerFeature("lichen_patch", new LichenPatchFeature(VegetationPatchConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> BOWL_LICHEN = registerFeature("bowl_lichen", new LichenMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> LICHEN_CORDYCEPS_COLUMN = registerFeature("lichen_cordyceps_column", new LichenCordycepsColumnFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoisePatchConfig> NOISE_PATCH = registerFeature("noise_patch", new NoisePatchFeature(NoisePatchConfig.CODEC));
    public static final Feature<PinkSaltStrawPatchConfig> PINK_SALT_STRAW_PATCH = registerFeature("pink_salt_straw_patch", new PinkSaltStrawPatchFeature(PinkSaltStrawPatchConfig.CODEC));
    public static final Feature<NoneFeatureConfiguration> OASIS = registerFeature("oasis", new OasisFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> SURFACE_OASIS = registerFeature("surface_oasis", new SurfaceOasisFeature(NoneFeatureConfiguration.CODEC));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> F registerFeature(String name, F feature) {
        FEATURES.put(Galosphere.id(name), feature);
        return feature;
    }

    public static void init() {
        for (ResourceLocation id : FEATURES.keySet()) {
            Registry.register(BuiltInRegistries.FEATURE, id, FEATURES.get(id));
        }
    }

}
