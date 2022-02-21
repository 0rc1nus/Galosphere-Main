package net.orcinus.cavesandtrenches.client.particles.providers;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class WarpedProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public WarpedProvider(SpriteSet set) {
        this.sprite = set;
    }

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
        SpellParticle spellparticle = new SpellParticle(world, x, y, z, velX, velY, velZ, this.sprite);
        float f = world.random.nextFloat() * 0.5F + 0.35F;
        spellparticle.setColor(2, 2, 40);
        return spellparticle;
    }
}
