package com.loader.loadingmod.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import com.loader.loadingmod.core.init.BlockInit;
import com.loader.loadingmod.core.init.RecipeSerializerInit;
import com.loader.loadingmod.core.init.RecipeTypeInit;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SmeltingRecipe extends AbstractCookingRecipe{
	public static int time;
	
	public SmeltingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(RecipeTypeInit.SMELTING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
		SmeltingRecipe.time = cookTimeIn;
	}

	@Override
	public ItemStack getToastSymbol() {

		return new ItemStack(BlockInit.furnace_block.get());

	}

	@Override
	public IRecipeSerializer<?> getSerializer() {

		return RecipeSerializerInit.SMELTING.get();

	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SmeltingRecipe> {

		@Override
		@SuppressWarnings("deprecation")
		public SmeltingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

			String s = JSONUtils.getAsString(json, "group", "");
			JsonElement jsonelement = (JsonElement) (JSONUtils.isArrayNode(json, "ingredient")
					? JSONUtils.getAsJsonArray(json, "ingredient")
					: JSONUtils.getAsJsonObject(json, "ingredient"));
			Ingredient ingredient = Ingredient.fromJson(jsonelement);
			ItemStack itemstack;

			if (!json.has("result")) {

				throw new JsonSyntaxException("Missing result, expected to find a string or object");

			}

			if (json.get("result").isJsonObject()) {
				
				//getAsJsonObject or convertToJsonObject ??
				itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));

			} else {

				String s1 = JSONUtils.getAsString(json, "result");
				ResourceLocation resourcelocation = new ResourceLocation(s1);
				itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {

					return new IllegalStateException("Item: " + s1 + " does not exist");

				}));

			}

			float f = JSONUtils.getAsFloat(json, "experience", 0.0F);
			int i = JSONUtils.getAsInt(json, "cookingtime", SmeltingRecipe.time);
			return new SmeltingRecipe(recipeId, s, ingredient, itemstack, f, i);

		}
		
//		@Override
//		@SuppressWarnings("deprecation")
//		public SmeltingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
//
//			String s = JSONUtils.getAsString(json, "group", "");
//			JsonElement jsonelement = (JsonElement) (JSONUtils.isArrayNode(json, "ingredient")
//					? JSONUtils.getAsJsonArray(json, "ingredient")
//					: JSONUtils.getAsJsonObject(json, "ingredient"));
//			Ingredient ingredient = Ingredient.fromJson(jsonelement);
//			ItemStack itemstack;
//
//			if (!json.has("result")) {
//
//				throw new JsonSyntaxException("Missing result, expected to find a string or object");
//
//			}
//
//			if (json.get("result").isJsonObject()) {
//
//				itemstack = ShapedRecipe.itemFromJson(JSONUtils.convertToJsonObject(json, "result"));
//
//			} else {
//
//				String s1 = JSONUtils.getAsString(json, "result");
//				ResourceLocation resourcelocation = new ResourceLocation(s1);
//				itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
//
//					return new IllegalStateException("Item: " + s1 + " does not exist");
//
//				}));
//
//			}
//
//			float f = JSONUtils.getAsFloat(json, "experience", 0.0F);
//			int i = JSONUtils.getAsInt(json, "cookingtime", SmeltingRecipe.time);
//			return new SmeltingRecipe(recipeId, s, ingredient, itemstack, f, i);
//
//		}
		
		@Override
		public SmeltingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String s = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ItemStack itemstack = buffer.readItem();
			float f = buffer.readFloat();
			int i = buffer.readVarInt();
			return new SmeltingRecipe(recipeId, s, ingredient, itemstack, f, i);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, SmeltingRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.cookingTime);
		}

	}
}
