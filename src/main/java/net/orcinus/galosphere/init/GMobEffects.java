package net.orcinus.galosphere.init;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.effects.GMobEffect;

public class GMobEffects {

    public static final Map<ResourceLocation, MobEffect> EFFECTS = Maps.newLinkedHashMap();

    public static final MobEffect ILLUSIVE = registerMobEffect("illusive", new GMobEffect(MobEffectCategory.HARMFUL, 623007));

    public static <M extends MobEffect> M registerMobEffect(String name, M mobEffect) {
        EFFECTS.put(Galosphere.id(name), mobEffect);
        return mobEffect;
    }

    public static void init() {
        for (ResourceLocation id : EFFECTS.keySet()) {
            Registry.register(Registry.MOB_EFFECT, id, EFFECTS.get(id));
        }
    }

}
