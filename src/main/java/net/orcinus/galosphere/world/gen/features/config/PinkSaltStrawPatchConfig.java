package net.orcinus.galosphere.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record PinkSaltStrawPatchConfig(IntProvider radius, IntProvider height, IntProvider additionHeight, Direction direction) implements FeatureConfiguration {
    public static final Codec<PinkSaltStrawPatchConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(IntProvider.CODEC.fieldOf("radius").forGetter(PinkSaltStrawPatchConfig::radius), IntProvider.codec(1, 128).fieldOf("height").forGetter(PinkSaltStrawPatchConfig::height), IntProvider.codec(1, 128).fieldOf("addition_height").forGetter(PinkSaltStrawPatchConfig::additionHeight), Direction.CODEC.fieldOf("direction").forGetter(PinkSaltStrawPatchConfig::direction)).apply(instance, PinkSaltStrawPatchConfig::new));
}
