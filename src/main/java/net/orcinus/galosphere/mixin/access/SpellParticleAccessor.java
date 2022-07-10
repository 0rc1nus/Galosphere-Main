package net.orcinus.galosphere.mixin.access;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpellParticle.class)
public interface SpellParticleAccessor {
    @Invoker("<init>")
    static SpellParticle createSpellParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
        throw new UnsupportedOperationException();
    }
}
