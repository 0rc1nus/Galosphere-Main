package net.orcinus.galosphere.client.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.client.model.ImpactModel;
import net.orcinus.galosphere.init.GModelLayers;
import org.jetbrains.annotations.Nullable;

public class ImpactParticle extends Particle {
    private final Model model;
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/particle/impact.png");
    private final RenderType renderType = RenderType.entityTranslucent(TEXTURE);

    public ImpactParticle(ClientLevel clientLevel, double d, double e, double f) {
        super(clientLevel, d, e, f);
        this.model = new ImpactModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(GModelLayers.IMPACT));
        this.gravity = 0.0f;
        this.lifetime = 14;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @Override
    protected int getLightColor(float f) {
        return 240;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float delta) {
        PoseStack poseStack = new PoseStack();
        Vec3 vec3 = camera.getPosition();
        float g = (float)(Mth.lerp(delta, this.xo, this.x) - vec3.x());
        float h = (float)(Mth.lerp(delta, this.yo, this.y) - vec3.y());
        float i = (float)(Mth.lerp(delta, this.zo, this.z) - vec3.z());
        float size = (this.age / 7.0F) * 1.75F;
        poseStack.translate(g, h - 0.5F, i);
        poseStack.scale(size, 1.0F, size);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer2 = bufferSource.getBuffer(this.renderType);
        this.model.renderToBuffer(poseStack, vertexConsumer2, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0F);
        bufferSource.endBatch();
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new ImpactParticle(clientLevel, d, e, f);
        }
    }

}
