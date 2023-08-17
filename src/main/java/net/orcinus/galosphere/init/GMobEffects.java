package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.effects.GMobEffect;

public class GMobEffects {

    public static final MobEffect ASTRAL = new GMobEffect(MobEffectCategory.BENEFICIAL, 12891319);

    public static void init() {
        Registry.register(BuiltInRegistries.MOB_EFFECT, Galosphere.id("astral"), ASTRAL);
    }

}
