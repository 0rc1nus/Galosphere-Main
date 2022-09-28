package net.orcinus.galosphere.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FayDustParticle extends TextureSheetParticle {
    static final RandomSource RANDOM = RandomSource.create();
    private final SpriteSet sprites;

    public FayDustParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet sprites) {
        super(world, x, y, z, velX, velY, velZ);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.quadSize *= 0.75F;
        this.hasPhysics = false;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float p_172146_) {
        float f = ((float)this.age + p_172146_) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(p_172146_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }


    @OnlyIn(Dist.CLIENT)
    public static class FayDustProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public FayDustProvider(SpriteSet p_172172_) {
            this.sprite = p_172172_;
        }

        public Particle createParticle(SimpleParticleType p_172183_, ClientLevel world, double x, double y, double z, double p_172188_, double vy, double vz) {
            FayDustParticle fayDust = new FayDustParticle(world, x, y, z, 0.5D - FayDustParticle.RANDOM.nextDouble(), vy, 0.5D - FayDustParticle.RANDOM.nextDouble(), this.sprite);
            fayDust.yd *= 0.2F;
            if (p_172188_ == 0.0D && vz == 0.0D) {
                fayDust.xd *= 0.1F;
                fayDust.zd *= 0.1F;
            }
            fayDust.setLifetime((int)(8.0D / (world.random.nextDouble() * 0.8D + 0.2D)));
            return fayDust;
        }
    }
}
