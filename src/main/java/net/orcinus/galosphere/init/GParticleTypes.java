package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.galosphere.Galosphere;

public class GParticleTypes {

    public static final SimpleParticleType AURA_LISTENER = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Galosphere.MODID, "aura_listener"), FabricParticleTypes.simple());
    public static final SimpleParticleType SILVER_BOMB = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Galosphere.MODID, "item_silverbomb"), FabricParticleTypes.simple());
    public static final SimpleParticleType WARPED = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Galosphere.MODID, "warped"), FabricParticleTypes.simple());

}
