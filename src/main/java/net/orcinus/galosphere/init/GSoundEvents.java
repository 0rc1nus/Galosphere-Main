package net.orcinus.galosphere.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Galosphere.MODID);

    public static final RegistryObject<SoundEvent> MUSIC_CRYSTAL_CANYONS = register("music.biome.crystal_canyons");
    public static final RegistryObject<SoundEvent> MUSIC_LICHEN_CAVES = register("music.biome.lichen_caves");

    public static final RegistryObject<SoundEvent> LUMIERE_COMPOST = register("block.lumiere.compost");

    public static final RegistryObject<SoundEvent> GLOW_FLARE_SPREAD = register("entity.glow_flare.spread");

    public static final RegistryObject<SoundEvent> SPECTRE_AMBIENT = register("entity.spectre.ambient");
    public static final RegistryObject<SoundEvent> SPECTRE_BOTTLE_EMPTY = register("entity.spectre.bottle.empty");
    public static final RegistryObject<SoundEvent> SPECTRE_BOTTLE_FILL = register("entity.spectre.bottle.fill");
    public static final RegistryObject<SoundEvent> SPECTRE_DEATH = register("entity.spectre.death");
    public static final RegistryObject<SoundEvent> SPECTRE_HURT = register("entity.spectre.hurt");
    public static final RegistryObject<SoundEvent> SPECTRE_LOCK_TO_SPYGLASS = register("entity.spectre.lock_to_spyglass");
    public static final RegistryObject<SoundEvent> SPECTRE_RECEIVE_ITEM = register("entity.spectre.receive_item");
    public static final RegistryObject<SoundEvent> SPECTRE_MANIPULATE_BEGIN = register("entity.spectre.manipulate.begin");
    public static final RegistryObject<SoundEvent> SPECTRE_MANIPULATE_END = register("entity.spectre.manipulate.end");

    public static final SoundType ALLURITE = register("allurite", 1, 1);
    public static final SoundType ALLURITE_CLUSTER = register("allurite_cluster", 1, 1);
    public static final SoundType LUMIERE = register("lumiere", 1, 1);
    public static final SoundType LUMIERE_CLUSTER = register("lumiere_cluster", 1, 1);
    public static final SoundType SILVER = register("silver", 1, 1);

    private static RegistryObject<SoundEvent> register(String string) {
        return SOUND_EVENTS.register(string, () -> new SoundEvent(new ResourceLocation(Galosphere.MODID, string)));
    }

    private static String block(String name, String append) {
        return "block." + name + "." + append;
    }

    private static SoundType register(String name, float volume, float pitch) {
        return new ForgeSoundType(volume, pitch, register(block(name, "break")), register(block(name, "step")), register(block(name, "place")), register(block(name, "hit")), register(block(name, "fall")));
    }

}
