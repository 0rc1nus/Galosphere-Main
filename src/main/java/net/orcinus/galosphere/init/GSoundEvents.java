package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.orcinus.galosphere.Galosphere;

public class GSoundEvents {

    public static final SoundEvent MUSIC_CRYSTAL_CANYONS = register("music.biome.crystal_canyons");
    public static final SoundEvent MUSIC_LICHEN_CAVES = register("music.biome.lichen_caves");

    public static final SoundEvent LUMIERE_COMPOST = register("block.lumiere.compost");

    public static final SoundEvent GLOW_FLARE_SPREAD = register("entity.glow_flare.spread");

    public static final SoundEvent SPECTRE_AMBIENT = register("entity.spectre.ambient");
    public static final SoundEvent SPECTRE_BOTTLE_EMPTY = register("entity.spectre.bottle.empty");
    public static final SoundEvent SPECTRE_BOTTLE_FILL = register("entity.spectre.bottle.fill");
    public static final SoundEvent SPECTRE_DEATH = register("entity.spectre.death");
    public static final SoundEvent SPECTRE_HURT = register("entity.spectre.hurt");
    public static final SoundEvent SPECTRE_LOCK_TO_SPYGLASS = register("entity.spectre.lock_to_spyglass");
    public static final SoundEvent SPECTRE_RECEIVE_ITEM = register("entity.spectre.receive_item");
    public static final SoundEvent SPECTRE_MANIPULATE_BEGIN = register("entity.spectre.manipulate.begin");
    public static final SoundEvent SPECTRE_MANIPULATE_END = register("entity.spectre.manipulate.end");

    public static final SoundType ALLURITE = register("allurite", 1, 1);
    public static final SoundType ALLURITE_CLUSTER = register("allurite_cluster", 1, 1);
    public static final SoundType LUMIERE = register("lumiere", 1, 1);
    public static final SoundType LUMIERE_CLUSTER = register("lumiere_cluster", 1, 1);
    public static final SoundType SILVER = register("silver", 1, 1);
    public static final SoundType GLOW_INK_CLUMPS = register("glow_ink_clumps", 1, 1);

    private static SoundEvent register(String string) {
        ResourceLocation id = new ResourceLocation(Galosphere.MODID, string);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    private static String block(String name, String append) {
        return "block." + name + "." + append;
    }

    private static SoundType register(String name, float volume, float pitch) {
        return new SoundType(volume, pitch, register(block(name, "break")), register(block(name, "step")), register(block(name, "place")), register(block(name, "hit")), register(block(name, "fall")));
    }

    public static void init() {
    }
}