package net.orcinus.galosphere.init;

import net.minecraft.world.food.FoodProperties;

public class GFoods {

    public static final FoodProperties LICHEN_CORDYCEPS = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build();
    public static final FoodProperties GOLDEN_LICHEN_CORDYCEPS = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build();
    public static final FoodProperties SALTED_JERKY = new FoodProperties.Builder().nutrition(6).saturationMod(0.2F).meat().build();
    public static final FoodProperties CURED_SUCCULENT = new FoodProperties.Builder().nutrition(3).saturationMod(0.8F).build();
}
