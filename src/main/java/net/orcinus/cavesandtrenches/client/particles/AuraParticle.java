package net.orcinus.cavesandtrenches.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AuraParticle extends RisingParticle {
    private final SpriteSet sprites;

    AuraParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet set) {
        super(world, x, y, z, velX, velY, velZ);
        this.sprites = set;
        this.friction = 0.96F;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.lifetime = 30;
        this.scale(2.9F);
        this.setSpriteFromAge(set);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return 240;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        Vec3 vec3 = camera.getPosition();
        float originX = (float) (Mth.lerp(partialTicks, xo, x) - vec3.x());
        float originY = (float) (Mth.lerp(partialTicks, yo, y) - vec3.y());
        float originZ = (float) (Mth.lerp(partialTicks, zo, z) - vec3.z());

        Vector3f[] avector3f = new Vector3f[] {new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float size = getQuadSize(partialTicks);

        Quaternion quaternion = Vector3f.XP.rotationDegrees(90);

        for(int i = 0; i < 4; ++i) {
            Vector3f vertex = avector3f[i];
            vertex.transform(quaternion);
            vertex.mul(size);
            vertex.add(originX, originY, originZ);
        }

        float u1 = getU1();
        float u0 = getU0();
        float v0 = getV0();
        float v1 = getV1();
        int brightness = getLightColor(partialTicks);
        consumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(u0, v1).color(rCol, gCol, bCol, alpha).uv2(brightness).endVertex();
        consumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(u0, v0).color(rCol, gCol, bCol, alpha).uv2(brightness).endVertex();
        consumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(u1, v0).color(rCol, gCol, bCol, alpha).uv2(brightness).endVertex();
        consumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(u1, v1).color(rCol, gCol, bCol, alpha).uv2(brightness).endVertex();
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

        public Particle createParticle(SimpleParticleType p_107750_, ClientLevel world, double x, double y, double z, double p_107755_, double p_107756_, double p_107757_) {
            AuraParticle aura = new AuraParticle(world, x, y, z, p_107755_, p_107756_, p_107757_, this.sprite);
            aura.setAlpha(1.0F);
            return aura;
        }
    }
}
