package net.orcinus.galosphere.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.crafting.PreservedTransformRecipe;

public class GRecipeSerializers {

    public static final RecipeSerializer<PreservedTransformRecipe> PERSEVERED_TRANSFORM = new PreservedTransformRecipe.Serializer();

    public static void init() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Galosphere.id("persevered_transform_recipe"), PERSEVERED_TRANSFORM);
    }

}
