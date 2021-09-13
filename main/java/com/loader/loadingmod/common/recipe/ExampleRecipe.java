package com.loader.loadingmod.common.recipe;

import com.google.gson.JsonObject;
import com.loader.loadingmod.core.init.RecipeSerializerInit;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ExampleRecipe implements IExampleRecipe{
	private final ResourceLocation id;
	private Ingredient input;
	private final ItemStack output;

	public ExampleRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
		this.id = id;
		this.output = output;
		this.input = input;
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		if (this.input.test(inv.getItem(0))) {
			return true;
		}
		return false;
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return this.output;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output; 
	}
	
	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeSerializerInit.EXAMPLE_SERIALIZER.get();
	}

	@Override
	public Ingredient getInput() {
		return this.input;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(null, this.input);
	}
	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExampleRecipe> {
		
		@Override
		@SuppressWarnings("deprecation")
		public ExampleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
			Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));

			return new ExampleRecipe(recipeId, input, output);
		}

		@Override
		public ExampleRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			ItemStack output = buffer.readItem();
			Ingredient input = Ingredient.fromNetwork(buffer);

			return new ExampleRecipe(recipeId, input, output);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ExampleRecipe recipe) {
			Ingredient input = recipe.getIngredients().get(0);
			input.toNetwork(buffer);

			buffer.writeItemStack(recipe.getResultItem(), false);
		}
	}

}
