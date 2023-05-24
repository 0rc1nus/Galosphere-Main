package net.orcinus.galosphere.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public record CrystalSpikeConfig(BlockState crystal_state, BlockState cluster_state, BlockState glinted_cluster,
                                 IntProvider xzRadius, CaveSurface crystal_direction) implements FeatureConfiguration {
    public static final Codec<CrystalSpikeConfig> CODEC = RecordCodecBuilder.create(codec -> {
        return codec.group(BlockState.CODEC.fieldOf("crystal_state").forGetter(config -> {
            return config.crystal_state;
        }), BlockState.CODEC.fieldOf("cluster_state").forGetter(config -> {
            return config.cluster_state;
        }), BlockState.CODEC.fieldOf("glinted_cluster").forGetter(config -> {
            return config.glinted_cluster;
        }), IntProvider.CODEC.fieldOf("xz_radius").forGetter(config -> {
            return config.xzRadius;
        }), CaveSurface.CODEC.fieldOf("crystal_direction").forGetter(config -> {
            return config.crystal_direction;
        })).apply(codec, CrystalSpikeConfig::new);
    });

}
