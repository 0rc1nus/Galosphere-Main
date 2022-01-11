package net.orcinus.cavesandtrenches.init;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.cavesandtrenches.CavesAndTrenches;
import net.orcinus.cavesandtrenches.util.RegistryHandler;

@Mod.EventBusSubscriber(modid = CavesAndTrenches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTParticleTypes {

    public static final RegistryHandler REGISTRY = CavesAndTrenches.REGISTRY;

    public static final RegistryObject<SimpleParticleType> AURA_LISTENER = REGISTRY.registerParticle("aura_listener", false);
    public static final RegistryObject<SimpleParticleType> SILVER_BOMB = REGISTRY.registerParticle("item_silverbomb", false);

}
