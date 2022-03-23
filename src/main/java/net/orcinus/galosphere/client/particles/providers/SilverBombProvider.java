package net.orcinus.galosphere.client.particles.providers;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.galosphere.init.CTItems;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class SilverBombProvider implements ParticleProvider<SimpleParticleType> {

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType particleType, ClientLevel world, double x, double y, double z, double p_107426_, double p_107427_, double p_107428_) {
        return new BreakingItemParticle(world, x, y, z, new ItemStack(CTItems.SILVER_BOMB.get()));
    }
}
