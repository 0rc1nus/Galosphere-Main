package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.orcinus.galosphere.Galosphere;

public class GParticleTypes {

    public static void init() { }

    public static final SimpleParticleType AURA_RINGER_INDICATOR = Registry.register(Registry.PARTICLE_TYPE, Galosphere.id("aura_ringer_indicator"), FabricParticleTypes.simple());
    public static final SimpleParticleType SILVER_BOMB = Registry.register(Registry.PARTICLE_TYPE, Galosphere.id("item_silverbomb"), FabricParticleTypes.simple());
    public static final SimpleParticleType WARPED = Registry.register(Registry.PARTICLE_TYPE, Galosphere.id("warped"), FabricParticleTypes.simple());
    public static final SimpleParticleType ALLURITE_RAIN = Registry.register(Registry.PARTICLE_TYPE, Galosphere.id("allurite_rain"), FabricParticleTypes.simple());
    public static final SimpleParticleType LUMIERE_RAIN = Registry.register(Registry.PARTICLE_TYPE, Galosphere.id("lumiere_rain"), FabricParticleTypes.simple());
    public static final SimpleParticleType SPECTATE_ORB = Registry.register(Registry.PARTICLE_TYPE, Galosphere.id("spectate_orb"), FabricParticleTypes.simple());

}
