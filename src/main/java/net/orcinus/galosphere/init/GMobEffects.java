package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.effects.GMobEffect;

import java.util.Map;

public class GMobEffects {
    public static final Map<MobEffect, ResourceLocation> MOB_EFFECTS = Maps.newLinkedHashMap();

    public static final MobEffect ASTRAL = register("astral", new GMobEffect(MobEffectCategory.BENEFICIAL, 12891319));
    public static final MobEffect BLOCK_BANE = register("block_bane", new GMobEffect(MobEffectCategory.HARMFUL, 7612935));

    public static <M extends MobEffect> M register(String name, M effect) {
        MOB_EFFECTS.put(effect, Galosphere.id(name));
        return effect;
    }

    public static void init() {
        MOB_EFFECTS.forEach((mobEffect, resourceLocation) -> Registry.register(BuiltInRegistries.MOB_EFFECT, resourceLocation, mobEffect));
    }

}
