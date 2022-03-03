package net.orcinus.cavesandtrenches.init;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> FLUTTERED_LEAF = registerConfiguredFeature("fluttered_leaf", CTFeatures.FLUTTERED_LEAF.get().configured(FeatureConfiguration.NONE));

}
