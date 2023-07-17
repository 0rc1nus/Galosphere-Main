package net.orcinus.galosphere.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public record NoisePatchConfig(CaveSurface caveSurface) implements FeatureConfiguration {
    public static final Codec<NoisePatchConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(CaveSurface.CODEC.fieldOf("surface").forGetter(config -> config.caveSurface)).apply(instance, NoisePatchConfig::new));
}
