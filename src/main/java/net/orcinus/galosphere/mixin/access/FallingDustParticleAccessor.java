package net.orcinus.galosphere.mixin.access;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingDustParticle;
import net.minecraft.client.particle.SpriteSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FallingDustParticle.class)
public interface FallingDustParticleAccessor {
    @Invoker("<init>")
    static FallingDustParticle createFallingDustParticle(ClientLevel clientLevel, double d, double e, double f, float g, float h, float i, SpriteSet spriteSet) {
        throw new UnsupportedOperationException();
    }
}
