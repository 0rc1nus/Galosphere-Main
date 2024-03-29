package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.orcinus.galosphere.Galosphere;

public class GParticleTypes {

    public static void init() { }

    public static final SimpleParticleType AURA_RINGER_INDICATOR = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("aura_ringer_indicator"), FabricParticleTypes.simple());
    public static final SimpleParticleType SILVER_BOMB = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("item_silverbomb"), FabricParticleTypes.simple());
    public static final SimpleParticleType WARPED = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("warped"), FabricParticleTypes.simple());
    public static final SimpleParticleType ALLURITE_RAIN = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("allurite_rain"), FabricParticleTypes.simple());
    public static final SimpleParticleType LUMIERE_RAIN = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("lumiere_rain"), FabricParticleTypes.simple());
    public static final SimpleParticleType AMETHYST_RAIN = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("amethyst_rain"), FabricParticleTypes.simple());
    public static final SimpleParticleType SPECTATE_ORB = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("spectate_orb"), FabricParticleTypes.simple());
    public static final SimpleParticleType PINK_SALT_FALLING_DUST = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("pink_salt_falling_dust"), FabricParticleTypes.simple());
    public static final SimpleParticleType IMPACT = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Galosphere.id("impact"), FabricParticleTypes.simple());

}
