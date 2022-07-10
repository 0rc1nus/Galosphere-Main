package net.orcinus.galosphere.client.particles.providers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.mixin.access.BreakingItemParticleAccessor;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SilverBombProvider implements ParticleProvider<SimpleParticleType> {

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType particleType, ClientLevel world, double x, double y, double z, double p_107426_, double p_107427_, double p_107428_) {
        return BreakingItemParticleAccessor.createBreakingItemParticle(world, x, y, z, new ItemStack(GItems.SILVER_BOMB));
    }
}
