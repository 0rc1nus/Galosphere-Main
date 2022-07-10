package net.orcinus.galosphere.mixin.access;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BreakingItemParticle.class)
public interface BreakingItemParticleAccessor {
    @Invoker("<init>")
    static BreakingItemParticle createBreakingItemParticle(ClientLevel clientLevel, double d, double e, double f, ItemStack itemStack) {
        throw new UnsupportedOperationException();
    }
}
