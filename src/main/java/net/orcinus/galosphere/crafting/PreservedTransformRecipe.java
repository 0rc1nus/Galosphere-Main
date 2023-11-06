package net.orcinus.galosphere.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GRecipeSerializers;

public class PreservedTransformRecipe implements SmithingRecipe {
    private final ResourceLocation ID = new ResourceLocation(Galosphere.MODID, "persevered_transform_recipe");

    public PreservedTransformRecipe() {
    }

    @Override
    public boolean isTemplateIngredient(ItemStack itemStack) {
        return itemStack.is(GItems.PRESERVING_TEMPLATE.get());
    }

    @Override
    public boolean isBaseIngredient(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("Persevered")) {
            return false;
        }
        return itemStack.getCount() == 1 && !(tag != null && tag.contains("Persevered"));
    }

    @Override
    public boolean isAdditionIngredient(ItemStack itemStack) {
        return itemStack.is(GItems.PINK_SALT_SHARD.get());
    }

    @Override
    public boolean matches(Container container, Level level) {
        boolean templateIngredient = this.isTemplateIngredient(container.getItem(0));
        boolean baseIngredient = this.isBaseIngredient(container.getItem(1));
        boolean additionIngredient = this.isAdditionIngredient(container.getItem(2));
        return templateIngredient && baseIngredient && additionIngredient;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack itemStack = container.getItem(1).copy();
        itemStack.getOrCreateTag().putBoolean("Persevered", true);
        return itemStack;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(Items.IRON_INGOT);
    }

    @Override
    public ResourceLocation getId() {
        return this.ID;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GRecipeSerializers.PRESERVED_TRANSFORM.get();
    }

    @Override
    public boolean isIncomplete() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<PreservedTransformRecipe> {
        @Override
        public PreservedTransformRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return new PreservedTransformRecipe();
        }

        @Override
        public PreservedTransformRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return new PreservedTransformRecipe();
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, PreservedTransformRecipe preservedTransformRecipe) {
        }
    }

}