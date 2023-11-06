package net.orcinus.galosphere.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.crafting.PreservedTransformRecipe;

@Mod.EventBusSubscriber(modid = Galosphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Galosphere.MODID);

    public static final RegistryObject<RecipeSerializer<PreservedTransformRecipe>> PRESERVED_TRANSFORM = RECIPE_SERIALIZERS.register("preserved_transform_recipe", PreservedTransformRecipe.Serializer::new);

}
