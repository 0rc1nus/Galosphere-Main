package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.world.gen.features.CrystalSpikeFeature;
import net.orcinus.galosphere.world.gen.features.config.CrystalSpikeConfig;

import java.util.Map;

public class GFeatures {

    public static final Map<ResourceLocation, Feature<?>> FEATURES = Maps.newLinkedHashMap();

    public static final Feature<CrystalSpikeConfig> CRYSTAL_SPIKE = registerFeature("crystal_spike", new CrystalSpikeFeature(CrystalSpikeConfig.CODEC));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> F registerFeature(String name, F feature) {
        FEATURES.put(new ResourceLocation(Galosphere.MODID, name), feature);
        return feature;
    }

    public static void init() {
        for (ResourceLocation id : FEATURES.keySet()) {
            Registry.register(Registry.FEATURE, id, FEATURES.get(id));
        }
    }

}
