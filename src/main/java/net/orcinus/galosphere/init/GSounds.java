package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.orcinus.galosphere.Galosphere;

public class GSounds {

    public static final SoundEvent MUSIC_CRYSTAL_CANYONS = register("music.biome.crystal_canyons");

    private static SoundEvent register(String string) {
        ResourceLocation id = Galosphere.id(string);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void init() {
    }
}