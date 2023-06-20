package net.orcinus.galosphere.client.particles.providers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.orcinus.galosphere.mixin.access.FallingDustParticleAccessor;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PinkSaltFallingDustProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public PinkSaltFallingDustProvider(SpriteSet spriteSet) {
        this.sprite = spriteSet;
    }

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
        int j = 15568753;
        float k = (float)(j >> 16 & 0xFF) / 255.0f;
        float l = (float)(j >> 8 & 0xFF) / 255.0f;
        float m = (float)(j & 0xFF) / 255.0f;
        return FallingDustParticleAccessor.createFallingDustParticle(clientLevel, d, e, f, k, l, m, this.sprite);
    }
}
