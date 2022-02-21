package net.orcinus.cavesandtrenches.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AuraEmissionParticle extends RisingParticle {
    private final SpriteSet sprites;

    public AuraEmissionParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet set) {
        super(world, x, y, z, velX, velY, velZ);
        this.sprites = set;
        this.scale(1.5F);
        this.setSpriteFromAge(set);
    }

    @Override
    protected int getLightColor(float delta) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet set) {
            this.sprite = set;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
            AuraEmissionParticle auraEmission = new AuraEmissionParticle(world, x, y, z, velX, velY, velZ, this.sprite);
            auraEmission.setAlpha(1.0F);
            return auraEmission;
        }
    }

}
