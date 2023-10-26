package net.orcinus.galosphere.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.blocks.MonstrometerBlock;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ImpactParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public ImpactParticle(ClientLevel clientLevel, double d, double e, double f, SpriteSet sprites) {
        super(clientLevel, d, e, f);
        this.alpha = 0.8F;
        this.quadSize = 1.3F;
        this.lifetime = 48;
        this.sprites = sprites;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        quadSize = Mth.lerp(0.25F, quadSize, 6);
        if (age++ >= lifetime) {
            remove();
        } else {
            alpha = Mth.lerp(0.08F, alpha, 0);
        }
        if (alpha <= 0.01F) remove();
        setSpriteFromAge(sprites);
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float delta) {
        renderParticle(consumer, camera, delta, Axis.XP.rotation(Mth.PI / 2));
        renderParticle(consumer, camera, delta, Axis.XN.rotation(Mth.PI / 2));
    }

    private void renderParticle(VertexConsumer consumer, Camera camera, float delta, Quaternionf quaternion) {
        Vec3 vec3 = camera.getPosition();
        if (vec3.distanceTo(new Vec3(x, y, z)) >= MonstrometerBlock.getParticleViewRange()) return;

        Vector3f[] veca = new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0)};

        for(int i = 0; i < 4; ++i) {
            Vector3f vec = veca[i];
            quaternion.transform(vec);
            vec.mul(getQuadSize(delta));
            float f = (float) (Mth.lerp(delta, xo, x) - vec3.x());
            float f1 = (float) (Mth.lerp(delta, yo, y) - vec3.y());
            float f2 = (float) (Mth.lerp(delta, zo, z) - vec3.z());
            vec.add(f, f1, f2);
        }

        float u0 = getU0();
        float u1 = getU1();
        float v0 = getV0();
        float v1 = getV1();

        int j = getLightColor(delta);

        consumer.vertex(veca[0].x(), veca[0].y(), veca[0].z()).uv(u1, v1).color(rCol, gCol, bCol, this.alpha).uv2(j).endVertex();
        consumer.vertex(veca[1].x(), veca[1].y(), veca[1].z()).uv(u1, v0).color(rCol, gCol, bCol, this.alpha).uv2(j).endVertex();
        consumer.vertex(veca[2].x(), veca[2].y(), veca[2].z()).uv(u0, v0).color(rCol, gCol, bCol, this.alpha).uv2(j).endVertex();
        consumer.vertex(veca[3].x(), veca[3].y(), veca[3].z()).uv(u0, v1).color(rCol, gCol, bCol, this.alpha).uv2(j).endVertex();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float tint) {
        return Math.max(50, super.getLightColor(tint));
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new ImpactParticle(clientLevel, d, e, f, this.sprites);
        }
    }

}
