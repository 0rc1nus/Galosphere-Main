package net.orcinus.galosphere.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class CrystalRainParticle extends SimpleAnimatedParticle {

    public CrystalRainParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet set) {
        super(world, x, y, z, set, 0.0125F);
        this.xd = velocityX;
        this.yd = velocityY;
        this.zd = velocityZ;
        this.quadSize *= 2.0F;
        this.lifetime = 60 + this.random.nextInt(12);
        this.setSpriteFromAge(set);
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType p_106566_, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new CrystalRainParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
        }
    }

}
