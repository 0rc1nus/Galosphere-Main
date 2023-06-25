package net.orcinus.galosphere.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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

    public static final RegistryObject<SoundEvent> MONSTROMETER_CHARGE = register("block.monstrometer.charge");
    public static final RegistryObject<SoundEvent> MONSTROMETER_ACTIVATE = register("block.monstrometer.activate");
    public static final RegistryObject<SoundEvent> MONSTROMETER_DEACTIVATE = register("block.monstrometer.deactivate");

    public static final RegistryObject<SoundEvent> SPECTRE_AMBIENT = register("entity.spectre.ambient");
    public static final RegistryObject<SoundEvent> SPECTRE_BOTTLE_EMPTY = register("entity.spectre.bottle.empty");
    public static final RegistryObject<SoundEvent> SPECTRE_BOTTLE_FILL = register("entity.spectre.bottle.fill");
    public static final RegistryObject<SoundEvent> SPECTRE_DEATH = register("entity.spectre.death");
    public static final RegistryObject<SoundEvent> SPECTRE_HURT = register("entity.spectre.hurt");
    public static final RegistryObject<SoundEvent> SPECTRE_LOCK_TO_SPYGLASS = register("entity.spectre.lock_to_spyglass");
    public static final RegistryObject<SoundEvent> SPECTRE_RECEIVE_ITEM = register("entity.spectre.receive_item");
    public static final RegistryObject<SoundEvent> SPECTRE_MANIPULATE_BEGIN = register("entity.spectre.manipulate.begin");
    public static final RegistryObject<SoundEvent> SPECTRE_MANIPULATE_END = register("entity.spectre.manipulate.end");

    public static final RegistryObject<SoundEvent> SPECTERPILLAR_DEATH = register("entity.specterpillar.death");
    public static final RegistryObject<SoundEvent> SPECTERPILLAR_HURT = register("entity.specterpillar.hurt");

    public static final SoundType ALLURITE = soundType("allurite");
    public static final SoundType ALLURITE_CLUSTER = soundType("allurite_cluster");
    public static final SoundType MONSTROMETER = soundType("monstrometer");
    public static final SoundType BOWL_LICHEN = soundType("bowl_lichen");
    public static final SoundType COMBUSTION_TABLE = soundType("combustion_table");
    public static final SoundType GLOW_INK_CLUMPS = soundType("glow_ink_clumps");
    public static final SoundType LICHEN_CORDYCEPS = soundType("lichen_cordyceps");
    public static final SoundType LICHEN_CORDYCEPS_BULB = soundType("lichen_cordyceps_bulb");
    public static final SoundType LICHEN_MOSS = soundType("lichen_moss");
    public static final SoundType LICHEN_ROOTS = soundType("lichen_roots");
    public static final SoundType LICHEN_SHELF = soundType("lichen_shelf");
    public static final SoundType LUMIERE = soundType("lumiere");
    public static final SoundType LUMIERE_CLUSTER = soundType("lumiere_cluster");
    public static final SoundType SILVER = soundType("silver");
    public static final SoundType SILVER_LATTICE = soundType("silver_lattice");

    private static RegistryObject<SoundEvent> register(String string) {
        return SOUND_EVENTS.register(string, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Galosphere.MODID, string)));
    }

    private static String block(String name, String append) {
        return "block." + name + "." + append;
    }

    private static SoundType soundType(String name) {
        return register(name, 1, 1);
    }

    private static SoundType register(String name, float volume, float pitch) {
        return new ForgeSoundType(volume, pitch, register(block(name, "break")), register(block(name, "step")), register(block(name, "place")), register(block(name, "hit")), register(block(name, "fall")));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(String name) {
        ResourceLocation id = new ResourceLocation(Galosphere.MODID, name);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

}
