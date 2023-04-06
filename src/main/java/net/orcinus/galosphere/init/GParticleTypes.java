package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Galosphere.MODID);

    public static final RegistryObject<SimpleParticleType> AURA_RINGER_INDICATOR = registerParticle("aura_ringer_indicator", false);
    public static final RegistryObject<SimpleParticleType> AURA_LISTENER = registerParticle("aura_listener", false);
    public static final RegistryObject<SimpleParticleType> SILVER_BOMB = registerParticle("item_silverbomb", false);
    public static final RegistryObject<SimpleParticleType> WARPED = registerParticle("warped", false);
    public static final RegistryObject<SimpleParticleType> ALLURITE_RAIN = registerParticle("allurite_rain", false);
    public static final RegistryObject<SimpleParticleType> LUMIERE_RAIN = registerParticle("lumiere_rain", false);

    public static RegistryObject<SimpleParticleType> registerParticle(String key, boolean alwaysShow) {
        return PARTICLES.register(key, () -> new SimpleParticleType(alwaysShow));
    }

}
