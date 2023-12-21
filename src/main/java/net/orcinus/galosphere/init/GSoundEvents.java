package net.orcinus.galosphere.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.orcinus.galosphere.Galosphere;

public class GSoundEvents {

    public static final Holder.Reference<SoundEvent> MUSIC_CRYSTAL_CANYONS = registerForHolder("music.biome.crystal_canyons");
    public static final Holder.Reference<SoundEvent> MUSIC_LICHEN_CAVES = registerForHolder("music.biome.lichen_caves");
    public static final Holder.Reference<SoundEvent> MUSIC_PINK_SALT_CAVES = registerForHolder("music.biome.pink_salt_caves");

    public static final SoundEvent LUMIERE_COMPOST = register("block.lumiere.compost");

    public static final SoundEvent GLOW_FLARE_SPREAD = register("entity.glow_flare.spread");

    public static final SoundEvent MONSTROMETER_CHARGE = register("block.monstrometer.charge");
    public static final SoundEvent MONSTROMETER_ACTIVATE = register("block.monstrometer.activate");
    public static final SoundEvent MONSTROMETER_DEACTIVATE = register("block.monstrometer.deactivate");

    public static final SoundEvent SPECTRE_AMBIENT = register("entity.spectre.ambient");
    public static final SoundEvent SPECTRE_BOTTLE_EMPTY = register("entity.spectre.bottle.empty");
    public static final SoundEvent SPECTRE_BOTTLE_FILL = register("entity.spectre.bottle.fill");
    public static final SoundEvent SPECTRE_DEATH = register("entity.spectre.death");
    public static final SoundEvent SPECTRE_HURT = register("entity.spectre.hurt");
    public static final SoundEvent SPECTRE_LOCK_TO_SPYGLASS = register("entity.spectre.lock_to_spyglass");
    public static final SoundEvent SPECTRE_RECEIVE_ITEM = register("entity.spectre.receive_item");
    public static final SoundEvent SPECTRE_MANIPULATE_BEGIN = register("entity.spectre.manipulate.begin");
    public static final SoundEvent SPECTRE_MANIPULATE_END = register("entity.spectre.manipulate.end");

    public static final SoundEvent BERSERKER_DEATH = register("entity.berserker.death");
    public static final SoundEvent BERSERKER_HURT = register("entity.berserker.hurt");
    public static final SoundEvent BERSERKER_IDLE = register("entity.berserker.idle");
    public static final SoundEvent BERSERKER_DUO_SMASH = register("entity.berserker.duo_smash");
    public static final SoundEvent BERSERKER_SMASH = register("entity.berserker.smash");
    public static final SoundEvent BERSERKER_SUMMONING = register("entity.berserker.summoning");
    public static final SoundEvent BERSERKER_PUNCH = register("entity.berserker.punch");
    public static final SoundEvent BERSERKER_ROAR = register("entity.berserker.roar");
    public static final SoundEvent BERSERKER_SHAKE = register("entity.berserker.shake");
    public static final SoundEvent BERSERKER_STEP = register("entity.berserker.step");

    public static final SoundEvent PRESERVED_DEATH = register("entity.preserved.death");
    public static final SoundEvent PRESERVED_HURT = register("entity.preserved.hurt");
    public static final SoundEvent PRESERVED_IDLE = register("entity.preserved.idle");
    public static final SoundEvent PRESERVED_EMERGE = register("entity.preserved.emerge");
    public static final SoundEvent PRESERVED_STEP = register("entity.preserved.step");

    public static final SoundEvent PINK_SALT_PILLAR_EMERGE = register("entity.pink_salt_pillar.emerge");

    public static final SoundEvent PINK_SALT_SHARD_LAND = register("entity.pink_salt_shard.land");

    public static final SoundEvent SPECTERPILLAR_DEATH = register("entity.specterpillar.death");
    public static final SoundEvent SPECTERPILLAR_HURT = register("entity.specterpillar.hurt");

    public static final SoundEvent SALTBOUND_TABLET_PREPARE_ATTACK = register("item.saltbound_tablet.prepare_attack");
    public static final SoundEvent SALTBOUND_TABLET_CAST_ATTACK = register("item.saltbound_tablet.cast_attack");
    public static final SoundEvent SALTBOUND_TABLET_COOLDOWN_OVER = register("item.saltbound_tablet.cooldown_over");

    public static final SoundEvent PINK_SALT_CHAMBER_SUMMON = register("block.pink_salt_chamber.summon");
    public static final SoundEvent PINK_SALT_CHAMBER_DEACTIVATE = register("block.pink_salt_chamber.deactivate");

    public static final SoundType ALLURITE = soundType("allurite");
    public static final SoundType ALLURITE_CLUSTER = soundType("allurite_cluster");
    public static final SoundType MONSTROMETER = soundType("monstrometer");
    public static final SoundType BOWL_LICHEN = soundType("bowl_lichen");
    public static final SoundType COMBUSTION_TABLE = soundType("combustion_table");
    public static final SoundType GLOW_INK_CLUMPS = soundType("glow_ink_clumps");
    public static final SoundType LICHEN_CORDYCEPS = soundType("lichen_cordyceps");
    public static final SoundType LICHEN_MOSS = soundType("lichen_moss");
    public static final SoundType LICHEN_ROOTS = soundType("lichen_roots");
    public static final SoundType LICHEN_SHELF = soundType("lichen_shelf");
    public static final SoundType LUMIERE = soundType("lumiere");
    public static final SoundType LUMIERE_CLUSTER = soundType("lumiere_cluster");
    public static final SoundType SILVER = soundType("silver");
    public static final SoundType SILVER_LATTICE = soundType("silver_lattice");
    public static final SoundType PINK_SALT = soundType("pink_salt");
    public static final SoundType PINK_SALT_CLUSTER = soundType("pink_salt_cluster");
    public static final SoundType PINK_SALT_LAMP = soundType("pink_salt_lamp");
    public static final SoundType GILDED_BEADS = soundType("gilded_beads");
    public static final SoundType CURED_MEMBRANE = soundType("cured_membrane");
    public static final SoundType STRANDED_MEMBRANE = soundType("stranded_membrane");

    private static SoundEvent register(String string) {
        ResourceLocation id = Galosphere.id(string);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(String string) {
        ResourceLocation id = Galosphere.id(string);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static String block(String name, String append) {
        return "block." + name + "." + append;
    }

    private static SoundType soundType(String name) {
        return new SoundType(1, 1, register(block(name, "break")), register(block(name, "step")), register(block(name, "place")), register(block(name, "hit")), register(block(name, "fall")));
    }

    public static void init() {
    }
}