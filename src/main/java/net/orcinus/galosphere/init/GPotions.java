package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.orcinus.galosphere.Galosphere;

public class GPotions {

    public static final Potion ASTRAL = new Potion(new MobEffectInstance(GMobEffects.ASTRAL, 1800));
    public static final Potion LONG_ASTRAL = new Potion("astral", new MobEffectInstance(GMobEffects.ASTRAL, 3600));

    public static void init() {
        Registry.register(BuiltInRegistries.POTION, Galosphere.id("astral"), ASTRAL);
        Registry.register(BuiltInRegistries.POTION, Galosphere.id("long_astral"), LONG_ASTRAL);
    }

}
