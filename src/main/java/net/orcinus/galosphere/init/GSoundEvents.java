package net.orcinus.galosphere.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.orcinus.galosphere.Galosphere;

public class GSoundEvents {

    public static final Holder.Reference<SoundEvent> MUSIC_CRYSTAL_CANYONS = registerForHolder("music.overworld.crystal_canyons");
    public static final Holder.Reference<SoundEvent> MUSIC_LICHEN_CAVES = registerForHolder("music.overworld.lichen_caves");

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
        ResourceLocation id = Galosphere.id(string);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static String block(String name, String append) {
        return "block." + name + "." + append;
    }

    private static SoundType register(String name, float volume, float pitch) {
        return new SoundType(volume, pitch, register(block(name, "break")), register(block(name, "step")), register(block(name, "place")), register(block(name, "hit")), register(block(name, "fall")));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(String string) {
        return registerForHolder(new ResourceLocation(Galosphere.MODID, string));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(ResourceLocation resourceLocation) {
        return registerForHolder(resourceLocation, resourceLocation);
    }

    private static Holder.Reference<SoundEvent> registerForHolder(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation2));
    }

    public static void init() {
    }
}