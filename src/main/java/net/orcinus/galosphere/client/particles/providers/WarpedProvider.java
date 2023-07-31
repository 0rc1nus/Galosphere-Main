package net.orcinus.galosphere.client.particles.providers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.orcinus.galosphere.mixin.access.SpellParticleAccessor;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class WarpedProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public WarpedProvider(SpriteSet set) {
        this.sprite = set;
    }

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
        SpellParticle spellparticle = SpellParticleAccessor.createSpellParticle(world, x, y, z, velX, velY, velZ, this.sprite);
        spellparticle.setColor(world.getRandom().nextFloat(),0.98F,1);
        return spellparticle;
    }
}
