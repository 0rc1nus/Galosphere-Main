package net.orcinus.galosphere.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Galosphere.MODID);

    public static final RegistryObject<SoundEvent> SPARKLE_BITE = registerSoundEvent("entity.sparkle.bite");

    private static RegistryObject<SoundEvent> registerSoundEvent(String path) {
        return SOUND_EVENTS.register(path, () -> new SoundEvent(new ResourceLocation(Galosphere.MODID, path)));
    }

}
