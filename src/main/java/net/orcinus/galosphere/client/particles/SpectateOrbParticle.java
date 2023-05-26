package net.orcinus.galosphere.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.orcinus.galosphere.api.Spectatable;

@Environment(EnvType.CLIENT)
public class SpectateOrbParticle extends SmokeParticle {

    protected SpectateOrbParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
        super(clientLevel, d, e, f, g, h, i, 2.5F, spriteSet);
        if (this.isPlayerSpectating()) {
            this.setAlpha(0.0F);
        }
    }

    @Override
    protected int getLightColor(float f) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isPlayerSpectating()) {
            this.setAlpha(0.0F);
        }
    }

    public boolean isPlayerSpectating() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.getCameraEntity() instanceof Spectatable && minecraft.options.getCameraType().isFirstPerson();
    }

    @Environment(EnvType.CLIENT)
    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            SpectateOrbParticle particle = new SpectateOrbParticle(clientLevel, d, e, f, g, h, i, this.sprite);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }

    }

}
